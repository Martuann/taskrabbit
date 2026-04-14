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
		HttpSession session = request.getSession();
	    Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");
	    
	

	    try {
	        String type = request.getParameter("type");
	        long idRichiesta = Long.parseLong(request.getParameter("id1"));
	        StatoRichiesta nuovoStato = StatoRichiesta.valueOf(type);
	        
	        Richiesta richiesta = richiestaDao.selectById(idRichiesta);
	        
	        if (richiesta != null) {
	        	if (richiesta.getUtenteRichiesto().getId().equals(utenteLoggato.getId()) || 
	        		    richiesta.getUtenteRichiedente().getId().equals(utenteLoggato.getId())) {
	                richiesta.setStato(nuovoStato);
	                richiestaDao.update(richiesta);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    String redirectParam = request.getParameter("redirect");
	    
	    if (redirectParam != null && !redirectParam.isEmpty()) {
	    	response.sendRedirect(request.getContextPath() + "/" + redirectParam);
	    	} else {
	        if (utenteLoggato.getRuolo() == Ruolo.PROFESSIONISTA) { 
	            response.sendRedirect(request.getContextPath() +"/GestioneRichiesteServlet");
	        } else {
	            response.sendRedirect(request.getContextPath() +"/CronologiaRichiesteServlet");
	        }
	    }
	}

 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	    Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");

        long idRichiesta = 0;
        String idRichiestaStr = request.getParameter("id1");
        if(idRichiestaStr != null && !idRichiestaStr.trim().isEmpty()) { 
	    	idRichiesta = Long.parseLong(idRichiestaStr);
	    }
	    

        String nomeNuovoStato = request.getParameter("type");
        StatoRichiesta nuovoStato = null;
        
        
        if(nomeNuovoStato != null && !nomeNuovoStato.trim().isEmpty()) {

        	nuovoStato = StatoRichiesta.valueOf(nomeNuovoStato);
        }
     //   System.out.println(richiesta.getIdUtenteRichiedente()+"\n"+utenteLoggato.getId());
        try {
            Richiesta richiesta = richiestaDao.selectById(idRichiesta);

	        if(richiesta != null && nuovoStato!=null) {
	        	//Separazione dei permessi
	        	switch(nuovoStato) {
	        	case completato: //solo il richiedente può segnare come "COMPLETATO" lo stato della richiesta
	        		if(!richiesta.getUtenteRichiedente().getId().equals(utenteLoggato.getId())) {
	        			response.sendRedirect(request.getContextPath() +"/HomepageServlet");
	        			return;
	        		}
	        		break;
	        	case in_attesa: //AggiornaRichiestaServlet non può cambiare lo stato in: "IN_ATTESA"
	        		response.sendRedirect(request.getContextPath() +"/HomepageServlet");
	        		return;
	        	default: //solo il professionista può segnare come "RIFIUTATO" o "IN_CORSO" lo stato della richiesta
	        		if(!richiesta.getUtenteRichiesto().getId().equals(utenteLoggato.getId()))
 {
	        			response.sendRedirect(request.getContextPath() +"/HomepageServlet");
	        			return;
	        		}
	        	}
	        	richiesta.setStato(nuovoStato);
                richiestaDao.update(richiesta);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
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
