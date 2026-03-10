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

@WebServlet("/RegistrazioneUtenteServlet")
public class RegistrazioneUtenteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegistrazioneUtenteServlet() {
	super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("registrazioneUtente.jsp").forward(request, response);
	    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	UtenteDao utentiInterni = (UtenteDao) new MysqlUtenteDAO();

	// Recupero parametri
	String nome = request.getParameter("nome");
	String cognome = request.getParameter("cognome");
	String email = request.getParameter("email");
	String password = request.getParameter("password");
	String dataDiNascita = request.getParameter("dataNascita");
	String numero = request.getParameter("telefono");
	String citta = request.getParameter("citta");
	String provincia = request.getParameter("provincia");
	String codiceFiscale = request.getParameter("codiceFiscale");

	try {
	    // 1. Gestione della data (parsing stringa -> LocalDate)
	    LocalDate ddn = LocalDate.parse(dataDiNascita);

	    // 2. Logica per la Città (Esempio ipotetico del tuo DAO)
	    // Se non hai ancora l'ID, dovresti prima salvare/cercare la città
	    long id_citta = 1; // Qui dovresti avere un metodo: cittaDao.getOrCreate(nomeCitta, provincia)

	    // 3. Creazione oggetto Utente (Assicurati che il costruttore corrisponda)
	    // Nota: Ho aggiunto Ruolo.CLIENTE come esempio se hai un Enum
	    Utente nuovoUtente = new Utente(nome, cognome, email, numero, password, ddn, codiceFiscale, Ruolo.UTENTE_BASE, id_citta);

	    utentiInterni.Register(nuovoUtente);

	    // Redirect al successo
	    response.sendRedirect(request.getContextPath() + "/loginUtente.jsp");

	} catch (RegisterException e) {
	    request.setAttribute("messaggio", "Errore registrazione: " + e.getMessage());
	    request.getRequestDispatcher("registrazioneUtente.jsp").forward(request, response);
	} catch (Exception e) {
	    e.printStackTrace();
	    request.setAttribute("messaggio", "Errore generico del sistema.");
	    request.getRequestDispatcher("registrazioneUtente.jsp").forward(request, response);
	}
    }
}