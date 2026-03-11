package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.elis.dao.definition.UtenteDao;
import org.elis.dao.mysql.MysqlUtenteDao;

import java.io.IOException;

import org.elis.progetto.model.Utente;
import org.elis.utilities.DataSourceConfig;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		// TODO Auto-generated method stub
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
	    String password = request.getParameter("password");

	   
	    UtenteDao dao = new MysqlUtenteDao(DataSourceConfig.getDataSource());

	    try {
	       
	        Utente utente = dao.login(email, password);

	        if (utente != null) {
	          
	            HttpSession session = request.getSession();
	            session.setAttribute("utenteLoggato", utente);
	            
	          
	            response.sendRedirect(request.getContextPath() + "/index.jsp");
	        } else {
	            
	            response.sendRedirect(request.getContextPath() + "/login.jsp?errore=1");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendError(500, "Errore interno del server durante il login");
	        
	        doGet(request,response);
	    }
	}

}


