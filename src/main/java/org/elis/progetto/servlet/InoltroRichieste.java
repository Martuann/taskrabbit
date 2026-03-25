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
import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.DisponibilitaDao;
import org.elis.dao.definition.OrarioBaseDao;
import org.elis.dao.definition.ProfessioneDao;
import org.elis.dao.definition.RichiestaDao;
import org.elis.dao.definition.UtenteDao;
import org.elis.dao.definition.UtenteProfessioneDao;
import org.elis.dao.definition.UtenteVeicoloDao;
import org.elis.dao.definition.VeicoloDao;
import org.elis.progetto.model.Disponibilita;
import org.elis.progetto.model.OrarioBase;
import org.elis.progetto.model.Professione;
import org.elis.progetto.model.Richiesta;
import org.elis.progetto.model.StatoRichiesta;
import org.elis.progetto.model.Utente;
import org.elis.progetto.model.UtenteProfessione;
import org.elis.progetto.model.UtenteVeicolo;
import org.elis.progetto.model.Veicolo;

/**
 * Servlet implementation class InoltroRichieste
 */
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
	    /**
	     * @see HttpServlet#HttpServlet()
	     */
	    
	        
    public InoltroRichieste() {
        super();
        
        richiestaDao = DaoFactory.getInstance().getRichiestaDao();
        utenteDao = DaoFactory.getInstance().getUtenteDao();
        professioneDao = DaoFactory.getInstance().getProfessioneDao();
        veicoloDao = DaoFactory.getInstance().getVeicoloDao();
        utenteVeicoloDao = DaoFactory.getInstance().getUtenteVeicoloDao();    
        utenteProfessioneDao=DaoFactory.getInstance().getUtenteProfessioneDao();
        dispDao=DaoFactory.getInstance().getDisponibilitaDao();
        orarioDao=DaoFactory.getInstance().getOrarioBaseDao();
}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	    Long idProfessionista;
    	    try {
    	        idProfessionista = Long.parseLong(request.getParameter("id_professionista"));
    	    } catch (NumberFormatException | NullPointerException e) {
    	    	response.sendRedirect(request.getContextPath() + "/HomepageServlet");
    	    	return;
    	    }

    	    Utente professionista = null;
    	    List<Veicolo> veicolo = null;
    	    List<Disponibilita> dispo = null;
    	    List<OrarioBase> orario = null;
    	    List<UtenteVeicolo> utenteVeicolo = null;
    	    List<Professione> professioni = null;
    	    List<UtenteProfessione> utenteProfessioni = null;
    	    List<Richiesta> occupate = null;

    	    try {
    	        professionista = utenteDao.ricercaPerId(idProfessionista);
    	        if (professionista == null) {
    	        	response.sendError(500, "Errore Imprevisto");    	 
    	        	return;
    	        }
    	        veicolo = veicoloDao.getVeicolibyUtente(idProfessionista);
    	        dispo = dispDao.getDisponibilitaPerUtente(idProfessionista);
    	        orario = orarioDao.getOrariByUtente(idProfessionista);
    	        utenteVeicolo = utenteVeicoloDao.getDettagliVeicoliUtente(idProfessionista);
    	        professioni = professioneDao.selectbyUtente(idProfessionista);
    	        utenteProfessioni = utenteProfessioneDao.selectByUtente(idProfessionista);
    	        occupate = richiestaDao.selectByIdUtenteRichiesto(idProfessionista);
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        response.sendError(500, "Errore nel collegamento con il db");
    	        return;
    	    }

    	    List<Disponibilita> dispProssimeDueSettimane = new ArrayList<Disponibilita>();
    	    LocalDate dataOggi = LocalDate.now();
    	    LocalDate oggiPiuDueSettimane = dataOggi.plusWeeks(2);

    	    while (dataOggi.isBefore(oggiPiuDueSettimane)) {
    	        LocalTime inizio = null;
    	        LocalTime fine = null;
    	        Disponibilita eccezione = null;

    	        for (int i = 0; i < dispo.size(); i++) {
    	            if (dispo.get(i).getData().equals(dataOggi)) {
    	                eccezione = dispo.get(i);
    	                break;
    	            }
    	        }

    	        if (eccezione != null) {
    	            inizio = eccezione.getInizio();
    	            fine = eccezione.getFine();
    	        } else {
    	            DayOfWeek giorno = dataOggi.getDayOfWeek();
    	            for (int i = 0; i < orario.size(); i++) {
    	                if (orario.get(i).getGiornoSettimana().equals(giorno)) {
    	                    inizio = orario.get(i).getOraInizio();
    	                    fine = orario.get(i).getOraFine();
    	                    break;
    	                }
    	            }
    	        }

    	        if (inizio != null && fine != null) {
    	            LocalTime ora = inizio;
    	            while (ora.isBefore(fine)) {
    	                boolean trovato = false;
    	                if (occupate != null) {
    	                    for (int i=0; i<occupate.size();i++){
    	                        if (occupate.get(i).getData().equals(dataOggi) && 
    	                            (occupate.get(i).getStato() == StatoRichiesta.in_corso || occupate.get(i).getStato() == StatoRichiesta.completato) &&
    	                            !ora.isBefore(occupate.get(i).getOrarioInizio()) && ora.isBefore(occupate.get(i).getOrarioFine())) {
    	                            trovato = true;
    	                            break;
    	                        }
    	                    }
    	                }

    	                if (trovato == false) {
    	                    Disponibilita slot = new Disponibilita();
    	                    slot.setUtente(professionista);
    	                    slot.setData(dataOggi);
    	                    slot.setInizio(ora);
    	                    slot.setFine(ora.plusHours(1));
    	                    dispProssimeDueSettimane.add(slot);
    	                }
    	                ora = ora.plusHours(1);
    	            }
    	        }
    	        dataOggi = dataOggi.plusDays(1);
    	    }

    	    request.setAttribute("professionista", professionista);
    	    request.setAttribute("utenteProf", utenteProfessioni);
    	    request.setAttribute("ListaProf", professioni);
    	    request.setAttribute("veicoli", veicolo);
    	    request.setAttribute("utenteVeicolo", utenteVeicolo);
    	 
    	    request.setAttribute("dispProssimeDueSettimane", dispProssimeDueSettimane);

    	    request.getRequestDispatcher("/WEB-INF/jsp/utente/RichiestaUtente.jsp").forward(request, response);	}
    	
		
		
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");

     

        Long idProfessionista, idProfess;
        LocalDate dataValida;
        LocalTime oraInizio;
        int oreSelezionate;
        String indirizzo;
        try {
            idProfessionista = Long.valueOf(request.getParameter("id_professionista"));
            idProfess = Long.valueOf(request.getParameter("id_professione"));
            dataValida = LocalDate.parse(request.getParameter("data_scelta"));
            oraInizio = LocalTime.parse(request.getParameter("ora_inizio"));
            oreSelezionate = Integer.parseInt(request.getParameter("durata_ore"));
            indirizzo=request.getParameter("indirizzo");

        } catch (Exception e) {
        	//response.sendRedirect(request.getContextPath() + "/InoltroRichieste?id_Professionista=" + request.getParameter("id_professionista") + "&error=dati_non_validi");
        	response.sendRedirect(request.getContextPath() + "/HomepageServlet");
        	return;
        }

        LocalTime oraFine = oraInizio.plusHours(oreSelezionate);
        List<Richiesta> occupate;
        List<Disponibilita> dispo;
        List<OrarioBase> orario;
        UtenteProfessione up;
        Utente professionista;
        Professione professione;
        try {
            occupate = richiestaDao.selectByIdUtenteRichiesto(idProfessionista);
            dispo = dispDao.getDisponibilitaPerUtente(idProfessionista);
            orario = orarioDao.getOrariByUtente(idProfessionista);
            up = utenteProfessioneDao.selectByIdUtenteIdProfessione(idProfessionista, idProfess);
            professionista=utenteDao.ricercaPerId(idProfessionista);
            professione=professioneDao.selectById(idProfess);
        } catch (Exception e) {
	        response.sendError(500, "Errore nel collegamento con il db");
            return;
        }

        for (int i = 0; i < occupate.size(); i++) {
            Richiesta r = occupate.get(i);
            if (r.getData().equals(dataValida) && (r.getStato() == StatoRichiesta.in_corso || r.getStato() == StatoRichiesta.completato)) {
                if (oraInizio.isBefore(r.getOrarioFine()) && oraFine.isAfter(r.getOrarioInizio())) {
                	response.sendRedirect(request.getContextPath() + "/InoltroRichieste?id_Professionista=" + idProfessionista + "&error=conflitto");
                	return;
                }
            }
        }

        LocalTime limiteInizio = null;
        LocalTime limiteFine = null;

        for (int i = 0; i < dispo.size(); i++) {
            if (dispo.get(i).getData().equals(dataValida)) {
                limiteInizio = dispo.get(i).getInizio();
                limiteFine = dispo.get(i).getFine();
                break;
            }
        }

        if (limiteInizio == null) {
            DayOfWeek giorno = dataValida.getDayOfWeek();
            for (int i = 0; i < orario.size(); i++) {
                if (orario.get(i).getGiornoSettimana().equals(giorno)) {
                    limiteInizio = orario.get(i).getOraInizio();
                    limiteFine = orario.get(i).getOraFine();
                    break;
                }
            }
        }

        if (limiteInizio == null || oraInizio.isBefore(limiteInizio) || oraFine.isAfter(limiteFine)) {
        	response.sendRedirect(request.getContextPath() + "/InoltroRichieste?id_Professionista=" + idProfessionista + "&error=fuori_orario");
        	return;
        }

        BigDecimal prezzoTotale = up.getTariffaH().multiply(new BigDecimal(request.getParameter("durata_ore")));
        String idVeicoloString = request.getParameter("id_veicolo");

        if (idVeicoloString != null && !idVeicoloString.isEmpty()) {
            try {
                Long idV = Long.valueOf(idVeicoloString);
                UtenteVeicolo uv = utenteVeicoloDao.selectByUtenteEVeicolo(idProfessionista, idV);
                if (uv != null) {
                    prezzoTotale = prezzoTotale.add(uv.getAggiuntaServizio());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Richiesta richiesta = new Richiesta();
        
        if (idVeicoloString != null && !idVeicoloString.isEmpty()) {
            Veicolo v=null;
			try {
				v = veicoloDao.ricercaPerId(Long.valueOf(idVeicoloString));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            richiesta.setVeicolo(v);
        }
        try{richiesta.setUtenteRichiedente(utenteLoggato);}catch(Exception e){
        	response.sendError(500, "Sessione scaduta");
        }
        richiesta.setUtenteRichiesto(professionista);
        richiesta.setProfessione(professione);
        richiesta.setData(dataValida);
        richiesta.setOrarioInizio(oraInizio);
        richiesta.setOrarioFine(oraFine);
        richiesta.setCostoEffettivo(prezzoTotale);
        richiesta.setStato(StatoRichiesta.in_attesa);
        richiesta.setDescrizione(request.getParameter("descrizione"));
        richiesta.setIndirizzo(indirizzo);
        try {
            richiestaDao.insert(richiesta);
        } catch (Exception e) {
        	response.sendError(500, "Errore durante il collegamento con il db");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/CronologiaRichiesteServlet?success=true");   }}

