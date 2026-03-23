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
	        	if (richiesta.getIdUtenteRichiesto().equals(utenteLoggato.getId()) || 
	        		    richiesta.getIdUtenteRichiedente().equals(utenteLoggato.getId())) {
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
