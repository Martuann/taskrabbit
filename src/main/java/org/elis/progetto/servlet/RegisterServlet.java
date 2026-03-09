package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.elis.dao.definition.ProfessioneDao;
import org.elis.dao.definition.RichiestaDao;
import org.elis.dao.definition.UtenteDao;
import org.elis.dao.definition.professioneDao;
import org.elis.dao.mysql.MysqlProfessioneDao;
import org.elis.dao.mysql.MysqlRichiestaDao;
import org.elis.dao.mysql.MysqlUtenteDAO;
import org.elis.progetto.model.Citta;
import org.elis.progetto.model.Professione;
import org.elis.progetto.model.Ruolo;
import org.elis.progetto.model.Utente;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
		UtenteDao  utentiInterni= new MysqlUtenteDAO();
        ProfessioneDao professioniInterne= new MysqlProfessioneDao();
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String dataDiNascita=request.getParameter("dataDiNascita");
		String codiceFiscale=request.getParameter("codicefiscale");
		String numero=request.getParameter("telefono");
        String nomeCitta=request.getParameter("nomeCitta");
        String provincia=request.getParameter("provincia");
        Citta citta=new Citta(nomeCitta,provincia);
		try {
			utentiInterni.Register(new Utente(nome, cognome, email, password,LocalDate.parse(dataDiNascita),codiceFiscale,numero));
			response.sendRedirect(request.getContextPath() + "/login");
		}
		catch(RegisterException e) {
			e.printStackTrace();
			request.setAttribute("messaggio", e.getMessage());
			request.getRequestDispatcher("/html/Registrazione.jsp").forward(request, response);
		}
        catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("messaggio", e.getMessage());
			request.getRequestDispatcher("/html/Registrazione.jsp").forward(request, response);
		}
	}

}
