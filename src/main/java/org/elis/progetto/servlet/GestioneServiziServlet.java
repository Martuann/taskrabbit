package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.ProfessioneDao;
import org.elis.dao.definition.UtenteProfessioneDao;
import org.elis.dao.definition.UtenteVeicoloDao;
import org.elis.dao.definition.VeicoloDao;
import org.elis.progetto.model.Professione;
import org.elis.progetto.model.Ruolo;
import org.elis.progetto.model.Utente;
import org.elis.progetto.model.UtenteProfessione;
import org.elis.progetto.model.UtenteVeicolo;
import org.elis.progetto.model.Veicolo;

@WebServlet("/GestioneServiziServlet")
public class GestioneServiziServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProfessioneDao professioneDao;
	private   VeicoloDao veicoloDao;
	private   UtenteProfessioneDao utenteProfessioneDao;
	private   UtenteVeicoloDao utenteVeicoloDao;

	@Override
	public void init() throws ServletException {
		professioneDao=DaoFactory.getInstance().getProfessioneDao();
		veicoloDao=DaoFactory.getInstance().getVeicoloDao();
		utenteProfessioneDao=DaoFactory.getInstance().getUtenteProfessioneDao();
		utenteVeicoloDao=DaoFactory.getInstance().getUtenteVeicoloDao();
		
	} 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	

    	HttpSession session = request.getSession();
    	Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");

    
    	
        try {
          

            List<Professione> catalogoTutteLeProfessioni = professioneDao.selectAll();
            List<Veicolo> catalogoTuttiIVeicoli = veicoloDao.getAllVeicoli();
            List<UtenteProfessione> professioniPosseduteDallUtente = utenteProfessioneDao.selectByUtente(utenteLoggato.getId());
            List<UtenteVeicolo> veicoliPossedutiDallUtente = utenteVeicoloDao.selectByUtente(utenteLoggato.getId());
            
            Set<Long> idProfessioniPossedute = professioniPosseduteDallUtente.stream()
                    .map(UtenteProfessione::getIdProfessione)
                    .collect(Collectors.toSet());

            List<Professione> professioniDisponibili = catalogoTutteLeProfessioni.stream()
                    .filter(p -> !idProfessioniPossedute.contains(p.getId()))
                    .collect(Collectors.toList());

            Set<Long> idVeicoliPosseduti = veicoliPossedutiDallUtente.stream()
                    .map(UtenteVeicolo::getIdVeicolo)
                    .collect(Collectors.toSet());

            List<Veicolo> veicoliDisponibili = catalogoTuttiIVeicoli.stream()
                    .filter(v -> !idVeicoliPosseduti.contains(v.getId()))
                    .collect(Collectors.toList());
            
            request.setAttribute("catalogoProfessioni", catalogoTutteLeProfessioni);
            request.setAttribute("professioniUtente", professioniPosseduteDallUtente);
            request.setAttribute("catalogoVeicoli", catalogoTuttiIVeicoli);
            request.setAttribute("veicoliUtente", veicoliPossedutiDallUtente);
            request.setAttribute("professioniDisponibili", professioniDisponibili);
            request.setAttribute("veicoliDisponibili", veicoliDisponibili);
            request.getRequestDispatcher("/WEB-INF/jsp/professionista/gestioneServizi.jsp").forward(request, response);            
        } catch (Exception exception) {
            exception.printStackTrace();
            response.sendError(500, "Errore nel caricamento dati: " + exception.getMessage());
        }
    	

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");

    
    	

       String operazioneRichiesta = request.getParameter("azione");
        Utente utenteLoggatoInSessione = (Utente) request.getSession().getAttribute("utenteLoggato");

  
        
        String urlRedirect = request.getContextPath() + "/GestioneServiziServlet";

        try {
           

            if ("aggiungi_professione".equals(operazioneRichiesta)) {
                Long idProfessione = Long.parseLong(request.getParameter("idProfessioneScelta"));
                BigDecimal tariffaOraria = validaEConvertiPrezzo(request.getParameter("tariffaOraria"));
                List<UtenteProfessione> mieProfessioni = utenteProfessioneDao.selectByUtente(utenteLoggatoInSessione.getId());
                boolean giaPosseduta = mieProfessioni.stream()
                        .anyMatch(up -> up.getIdProfessione() == idProfessione);

                if (giaPosseduta) {
                    throw new IllegalArgumentException("Professione già assegnata.");
                }
                utenteProfessioneDao.insert(new UtenteProfessione(utenteLoggatoInSessione.getId(), idProfessione, tariffaOraria));

            } else if ("rimuovi_professione".equals(operazioneRichiesta)) {
                long idDaRimuovere = Long.parseLong(request.getParameter("idProfessioneDaRimuovere"));
                utenteProfessioneDao.delete(idDaRimuovere);

            } else if ("modifica_tariffa".equals(operazioneRichiesta)) {
                long idAssociazione = Long.parseLong(request.getParameter("idAssociazione"));
                long idProfessione = Long.parseLong(request.getParameter("idProfessione"));
                BigDecimal nuovaTariffa = validaEConvertiPrezzo(request.getParameter("nuovaTariffa"));
                
                UtenteProfessione upAggiornato = new UtenteProfessione();
                upAggiornato.setId(idAssociazione);
                upAggiornato.setIdUtente(utenteLoggatoInSessione.getId());
                upAggiornato.setIdProfessione(idProfessione);
                upAggiornato.setTariffaH(nuovaTariffa);
                
                utenteProfessioneDao.update(upAggiornato);

            } else if ("aggiungi_veicolo".equals(operazioneRichiesta)) {
                Long idVeicolo = Long.parseLong(request.getParameter("idVeicoloScelto"));
                BigDecimal sovrapprezzo = validaEConvertiPrezzo(request.getParameter("prezzoAggiuntivo"));
                List<UtenteVeicolo> mieiVeicoli = utenteVeicoloDao.selectByUtente(utenteLoggatoInSessione.getId());
                boolean veicoloGiaPosseduto = mieiVeicoli.stream()
                        .anyMatch(uv -> uv.getIdVeicolo() == idVeicolo);
                
                if (veicoloGiaPosseduto) {
                    throw new IllegalArgumentException("Veicolo già assegnato.");
                }
                utenteVeicoloDao.associaVeicolo(new UtenteVeicolo(utenteLoggatoInSessione.getId(), idVeicolo, sovrapprezzo));

            } else if ("rimuovi_veicolo".equals(operazioneRichiesta)) {
                long idVeicoloDaRimuovere = Long.parseLong(request.getParameter("idVeicoloDaRimuovere"));
                utenteVeicoloDao.rimuoviAssociazione(utenteLoggatoInSessione.getId(), idVeicoloDaRimuovere);

            } else if ("modifica_sovrapprezzo".equals(operazioneRichiesta)) {
                long idVeicolo = Long.parseLong(request.getParameter("idVeicolo"));
                BigDecimal nuovoPrezzo = validaEConvertiPrezzo(request.getParameter("nuovoPrezzo"));
                utenteVeicoloDao.aggiornaPrezzoServizio(utenteLoggatoInSessione.getId(), idVeicolo, nuovoPrezzo);
            }

        } 
        catch (NumberFormatException e) {
            e.printStackTrace();
            urlRedirect += "?errore=input";
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            if (e.getMessage().contains("già assegnat")) {
                urlRedirect += "?errore=duplicato";
            } else {
                urlRedirect += "?errore=input";
            }}
        catch (Exception exception) {
            exception.printStackTrace();
            urlRedirect += "?errore=1";
        }

        response.sendRedirect(urlRedirect);
    }

    








private BigDecimal validaEConvertiPrezzo(String prezzoStr) throws IllegalArgumentException {
 if (prezzoStr == null || prezzoStr.trim().isEmpty()) {
     throw new IllegalArgumentException("Aggiungere un prezzo al servizio");
 }
 
 prezzoStr = prezzoStr.replace(",", ".");
 
 BigDecimal prezzo;
 try {
     prezzo = new BigDecimal(prezzoStr);
 } catch (NumberFormatException e) {
     throw new IllegalArgumentException("Formato numero non valido");
 }
 
 if (prezzo.compareTo(BigDecimal.ZERO) < 0) {
     throw new IllegalArgumentException("Il prezzo non può essere negativo");
 }
 
 if (prezzo.compareTo(new BigDecimal("999999.99")) > 0) {
     throw new IllegalArgumentException("Il prezzo inserito è troppo alto");
 }
 
 return prezzo;
}
}