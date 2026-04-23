package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.RichiestaDao;
import org.elis.progetto.model.Richiesta;
import org.elis.progetto.model.Ruolo;
import org.elis.progetto.model.StatoRichiesta;
import org.elis.progetto.model.Utente;

/**
 * Servlet implementation class AggiornaRichiesta
 */
@WebServlet("/AggiornaRichiesta")
public class AggiornaRichiestaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RichiestaDao richiestaDao;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AggiornaRichiestaServlet() {
        super();
        richiestaDao = DaoFactory.getInstance().getRichiestaDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);

	}

 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	    Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");

        String nomeNuovoStato = request.getParameter("type");
        String idRichiestaStr = request.getParameter("id1");
        
        if (idRichiestaStr == null || idRichiestaStr.trim().isEmpty()
                || nomeNuovoStato == null || nomeNuovoStato.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/HomepageServlet");
            return;
        }
        long idRichiesta;
        StatoRichiesta nuovoStato;

        try {
            idRichiesta  = Long.parseLong(idRichiestaStr.trim());
            nuovoStato   = StatoRichiesta.valueOf(nomeNuovoStato.trim());
        } catch ( IllegalArgumentException e) {
            response.sendRedirect(request.getContextPath() + "/HomepageServlet");
            return;
        }
        if (nuovoStato == StatoRichiesta.in_attesa) {
            response.sendRedirect(request.getContextPath() + "/HomepageServlet");
            return;
        }
    
        try {
            Richiesta richiesta = richiestaDao.selectById(idRichiesta);
           
            if (richiesta == null) {
                response.sendRedirect(request.getContextPath() + "/HomepageServlet");
                return;
            }
            
	        	switch(nuovoStato) {
	        	case completato: //solo il richiedente può segnare come "COMPLETATO" lo stato della richiesta
	        		if(!richiesta.getUtenteRichiedente().getId().equals(utenteLoggato.getId())) {
	        			response.sendRedirect(request.getContextPath() +"/HomepageServlet");
	        			return;
	        		}
	        		break;
	        	
	            default:
                    // in_corso e rifiutato: solo il professionista (utenteRichiesto)
                    if (!richiesta.getUtenteRichiesto().getId().equals(utenteLoggato.getId())) {
                        response.sendRedirect(request.getContextPath() + "/HomepageServlet");
                        return;
                    }
                    break;
	        	}
	        	richiesta.setStato(nuovoStato);
                richiestaDao.update(richiesta);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendError(500, "Errore durante l'aggiornamento della richiesta");
            return;
	    }

	 
	    
	    switch(utenteLoggato.getRuolo()) {
	    case UTENTE_BASE: 
	    	response.sendRedirect(request.getContextPath() +"/CronologiaRichiesteServlet");
	    	break;
	    case PROFESSIONISTA: 
	    	response.sendRedirect(request.getContextPath() +"/GestioneRichiesteServlet");
	    	break;
	    default:
	    	response.sendRedirect(request.getContextPath() +"/HomepageServlet");
	    }
	}

}
