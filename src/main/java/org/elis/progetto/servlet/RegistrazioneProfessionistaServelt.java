package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import org.elis.dao.definition.UtenteDao;
import org.elis.dao.mysql.MysqlUtenteDAO;
import org.elis.exception.RegisterException;
import org.elis.progetto.model.Ruolo;
import org.elis.progetto.model.Utente;


@WebServlet("/RegistrazioneProfessionistaServlet")
public class RegistrazioneProfessionistaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegistrazioneProfessionistaServlet() {
	super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	request.getRequestDispatcher("registrazioneProfessionista.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	UtenteDao utentiInterni = new MysqlUtenteDAO();

	// Recupero parametri
	String nome = request.getParameter("Nome");
	String cognome = request.getParameter("Cognome");
	String email = request.getParameter("email");
	String password = request.getParameter("password");
	String dataDiNascita = request.getParameter("dataNascita");
	String numero = request.getParameter("telefono");
	String nomeCitta = request.getParameter("citta");
	String provincia = request.getParameter("provincia");
	String codiceFiscale = request.getParameter("codiceFiscale");
	String specializzazione = request.getParameter("professione");

	try {
	    LocalDate ddn = LocalDate.parse(dataDiNascita);
	    long id_citta = 1;


	    Professionista nuovoProf = new Professionista(
		    nome, cognome, email, numero, password, ddn,
		    codiceFiscale, Ruolo.PROFESSIONISTA, id_citta, specializzazione
		    );

	    utentiInterni.Register(nuovoProf);

	    response.sendRedirect(request.getContextPath() + "/loginUtente.jsp?success=prof");

	} catch (RegisterException e) {
	    request.setAttribute("messaggio", e.getMessage());
	    request.getRequestDispatcher("registrazioneProf.jsp").forward(request, response);
	} catch (Exception e) {
	    e.printStackTrace();
	    request.setAttribute("messaggio", "Errore tecnico: riprova più tardi.");
	    request.getRequestDispatcher("registrazioneProf.jsp").forward(request, response);
	}
    }
}