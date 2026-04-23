package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.UtenteDao;
import org.elis.dao.definition.UtenteProfessioneDao;

import java.io.IOException;

import org.elis.progetto.model.Ruolo;
import org.elis.progetto.model.Utente;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private  UtenteDao utenteDao;
    private UtenteProfessioneDao utenteProfessioneDao;
	@Override
	public void init() throws ServletException {
		utenteDao=DaoFactory.getInstance().getUtenteDao();
        utenteProfessioneDao=DaoFactory.getInstance().getUtenteProfessioneDao();

	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/jsp/pubblico/login.jsp").forward(request, response);	
		
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

	    if (email == null || email.trim().isEmpty() || 
	    	    password == null || password.trim().isEmpty()) {
	    	    response.sendRedirect(request.getContextPath() + "/Login?errore=campi_obbligatori");
	    	    return;
	    	}

	    try {

	        Utente utente = utenteDao.login(email.trim(), password.trim());

	        if (utente != null) {
	            HttpSession session = request.getSession();

	        	if (Ruolo.PROFESSIONISTA.equals(utente.getRuolo())) {
	        	  try {
	        	    boolean haTariffeSballate = utenteProfessioneDao.checkTariffeCritiche(utente.getId());
	        	    
	        	    session.setAttribute("alertTariffa", haTariffeSballate);
	        		System.out.println("ciao mamma"+haTariffeSballate);
}catch (Exception e) {
		        	session.setAttribute("alertTariffa", false);
	        		System.out.println("ciao mamma"+"ERRORE");

	        	}}
	            session.setAttribute("utenteLoggato", utente);


	            response.sendRedirect(request.getContextPath() + "/HomepageServlet");
	        } else {

	        	response.sendRedirect(request.getContextPath() + "/Login?errore=1");   
	        	}
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendRedirect(request.getContextPath() + "/Login?errore=tecnico");
	    }
	}

}


