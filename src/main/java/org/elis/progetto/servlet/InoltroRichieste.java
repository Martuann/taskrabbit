package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.elis.dao.definition.*;
import org.elis.progetto.model.*;

@WebServlet("/InoltroRichieste")
public class InoltroRichieste extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RichiestaDao richiestaDao;
    private UtenteDao utenteDao;
    private ProfessioneDao professioneDao;
    private VeicoloDao veicoloDao;
    private UtenteVeicoloDao utenteVeicoloDao;
    private UtenteProfessioneDao utenteProfessioneDao;
    private DisponibilitaDao dispDao;
    private OrarioBaseDao orarioDao;

    @Override
    public void init() throws ServletException {
        DaoFactory factory = DaoFactory.getInstance();
        richiestaDao = factory.getRichiestaDao();
        utenteDao = factory.getUtenteDao();
        professioneDao = factory.getProfessioneDao();
        veicoloDao = factory.getVeicoloDao();
        utenteVeicoloDao = factory.getUtenteVeicoloDao();
        utenteProfessioneDao = factory.getUtenteProfessioneDao();
        dispDao = factory.getDisponibilitaDao();
        orarioDao = factory.getOrarioBaseDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long idProfessionista;
        try {
            idProfessionista = Long.parseLong(request.getParameter("id_professionista"));
        } catch (NumberFormatException | NullPointerException e) {
            response.sendRedirect(request.getContextPath() + "/HomepageServlet");
            return;
        }

        try {
            Utente professionista = utenteDao.ricercaPerId(idProfessionista);
            if (professionista == null || !professionista.getRuolo().equals(Ruolo.PROFESSIONISTA)) {
                response.sendError(404, "Professionista non trovato.");
                return;
            }

            List<Veicolo> veicoli = veicoloDao.getVeicolibyUtente(idProfessionista);
            List<Disponibilita> eccezioni = dispDao.getDisponibilitaPerUtente(idProfessionista);
            List<OrarioBase> orariBase = orarioDao.getOrariByUtente(idProfessionista);
            List<UtenteVeicolo> utenteVeicoli = utenteVeicoloDao.getDettagliVeicoliUtente(idProfessionista);
            List<Professione> professioni = professioneDao.selectbyUtente(idProfessionista);
            List<UtenteProfessione> utenteProf = utenteProfessioneDao.selectByUtenteandtariffa(idProfessionista);
            List<Richiesta> occupate = richiestaDao.selectByIdUtenteRichiesto(idProfessionista);

            List<Disponibilita> dispProssimeDueSettimane = new ArrayList<>();
            LocalDate dataCorrente = LocalDate.now();
            LocalDate finePeriodo = dataCorrente.plusWeeks(2);
            LocalTime adessoPiuDue = LocalTime.now().plusHours(2);
            LocalDate oggiReal = LocalDate.now();

            while (!dataCorrente.isAfter(finePeriodo)) {
                LocalTime inizio = null;
                LocalTime fine = null;

                for (Disponibilita d : eccezioni) {
                    if (d.getData() != null && d.getData().equals(dataCorrente)) {
                        inizio = d.getInizio();
                        fine = d.getFine();
                        break;
                    }
                }

                if (inizio == null) {
                    DayOfWeek giorno = dataCorrente.getDayOfWeek();
                    for (OrarioBase ob : orariBase) {
                        if (ob.getGiornoSettimana().equals(giorno)) {
                            inizio = ob.getOraInizio();
                            fine = ob.getOraFine();
                            break;
                        }
                    }
                }

                if (inizio != null && fine != null && inizio.isBefore(fine)) {
                    LocalTime ora = inizio;

                    while (ora.isBefore(fine)) {
                        LocalTime oraInizioGiro = ora; 

                        if (dataCorrente.equals(oggiReal) && ora.isBefore(adessoPiuDue)) {
                            int minuti = adessoPiuDue.getMinute();
                            
                            if (minuti == 0) {
                                ora = adessoPiuDue;
                            } else if (minuti <= 30) {
                                ora = adessoPiuDue.withMinute(30).withSecond(0).withNano(0);
                            } else {
                                ora = adessoPiuDue.plusHours(1).withMinute(0).withSecond(0).withNano(0);
                            }

                            if (!ora.isBefore(fine)) break;
                        }

                        Richiesta occupata = null;
                        for (Richiesta r : occupate) {
                            if (r.getData().equals(dataCorrente) &&
                               (r.getStato() == StatoRichiesta.in_corso || r.getStato() == StatoRichiesta.completato) &&
                               !ora.isBefore(r.getOrarioInizio()) && ora.isBefore(r.getOrarioFine())) {
                                occupata = r;
                                break;
                            }
                        }

                        if (occupata != null) {
                            ora = occupata.getOrarioFine();
                        } else {
                            LocalTime fineSlot = fine;
                            for (Richiesta r : occupate) {
                                if (r.getData().equals(dataCorrente) &&
                                    r.getOrarioInizio().isAfter(ora) &&
                                    r.getOrarioInizio().isBefore(fineSlot)) {
                                    fineSlot = r.getOrarioInizio();
                                }
                            }

                            Disponibilita slot = new Disponibilita();
                            slot.setUtente(professionista);
                            slot.setData(dataCorrente);
                            slot.setInizio(ora);
                            slot.setFine(fineSlot);
                            dispProssimeDueSettimane.add(slot);

                            ora = fineSlot;
                        }

                        if (!ora.isAfter(oraInizioGiro)) {
                            ora = ora.plusMinutes(15);
                        }
                    }
                }
                dataCorrente = dataCorrente.plusDays(1);
            }

            request.setAttribute("professionista", professionista);
            request.setAttribute("utenteProf", utenteProf);
            request.setAttribute("ListaProf", professioni);
            request.setAttribute("veicoli", veicoli);
            request.setAttribute("utenteVeicolo", utenteVeicoli);
            request.setAttribute("dispProssimeDueSettimane", dispProssimeDueSettimane);

            request.getRequestDispatcher("/WEB-INF/jsp/utente/RichiestaUtente.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Errore nel caricamento dati.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");
        if (utenteLoggato == null) {
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }

        try {
            Long idProfessionista = Long.valueOf(request.getParameter("id_professionista"));
            Long idProfessione = Long.valueOf(request.getParameter("id_professione"));
            LocalDate dataScelta = LocalDate.parse(request.getParameter("data_scelta"));
            LocalTime oraInizio = LocalTime.parse(request.getParameter("ora_inizio"));
            int durataOre = Integer.parseInt(request.getParameter("durata_ore"));
            LocalTime oraFine = oraInizio.plusHours(durataOre);
            String indirizzo = request.getParameter("indirizzo");

            if (dataScelta.isBefore(LocalDate.now())) {
                response.sendRedirect(request.getContextPath() + "/InoltroRichieste?id_professionista=" + idProfessionista + "&error=data_passata");
                return;
            }

            List<Richiesta> occupate = richiestaDao.selectByIdUtenteRichiesto(idProfessionista);
            for (Richiesta r : occupate) {
                if (r.getData().equals(dataScelta) &&
                    (r.getStato() == StatoRichiesta.in_corso || r.getStato() == StatoRichiesta.completato) &&
                    oraInizio.isBefore(r.getOrarioFine()) && oraFine.isAfter(r.getOrarioInizio())) {
                    response.sendRedirect(request.getContextPath() + "/InoltroRichieste?id_professionista=" + idProfessionista + "&error=conflitto");
                    return;
                }
            }

            UtenteProfessione up = utenteProfessioneDao.selectByIdUtenteIdProfessione(idProfessionista, idProfessione);
            BigDecimal prezzoTotale = up.getTariffaH().multiply(new BigDecimal(durataOre));

            Richiesta richiesta = new Richiesta();
            richiesta.setUtenteRichiedente(utenteLoggato);
            richiesta.setUtenteRichiesto(utenteDao.ricercaPerId(idProfessionista));
            richiesta.setProfessione(professioneDao.selectById(idProfessione));
            richiesta.setData(dataScelta);
            richiesta.setOrarioInizio(oraInizio);
            richiesta.setOrarioFine(oraFine);
            richiesta.setCostoEffettivo(prezzoTotale);
            richiesta.setStato(StatoRichiesta.in_attesa);
            richiesta.setIndirizzo(indirizzo);
            richiesta.setDescrizione(request.getParameter("descrizione"));

            richiestaDao.insert(richiesta);
            response.sendRedirect(request.getContextPath() + "/CronologiaRichiesteServlet");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/HomepageServlet");
        }
    }
}