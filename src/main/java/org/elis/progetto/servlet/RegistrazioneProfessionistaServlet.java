package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.elis.dao.definition.CittaDao;
import org.elis.dao.definition.UtenteDao;
import org.elis.dao.definition.UtenteProfessioneDAO;
import org.elis.dao.mysql.MysqlCittaDao;
import org.elis.dao.mysql.MysqlProfessioneDao;
import org.elis.dao.mysql.MysqlUtenteDAO;
import org.elis.dao.mysql.MysqlUtenteProfessioneDao;
import org.elis.exception.RegisterException;
import org.elis.progetto.model.Citta;
import org.elis.progetto.model.Professione;
import org.elis.progetto.model.Ruolo;
import org.elis.progetto.model.Utente;
import org.elis.progetto.model.UtenteProfessione;
import org.elis.utilities.DataSourceConfig;




@WebServlet("/RegistrazioneProfessionistaServlet")
public class RegistrazioneProfessionistaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public RegistrazioneProfessionistaServlet() {
	super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	List<Professione> tutteLeProfessioni = new MysqlProfessioneDao(DataSourceConfig.getDataSource()).selectAll();
	
	request.setAttribute("listaProfessioni", tutteLeProfessioni);
	request.getRequestDispatcher("/PagineWeb/registrazioneProfessionista.jsp").forward(request, response);	
	

	
    }
    

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	UtenteDao utentiInterni = new MysqlUtenteDAO(DataSourceConfig.getDataSource());
	CittaDao cittaInterna = new MysqlCittaDao(DataSourceConfig.getDataSource());
    UtenteProfessioneDAO utenteProfessioneInterno = new MysqlUtenteProfessioneDao(DataSourceConfig.getDataSource());

	String nome = request.getParameter("nome");
	String cognome = request.getParameter("cognome");
	String email = request.getParameter("email");
	String password = request.getParameter("password");
	String dataDiNascita = request.getParameter("dataNascita");
	String numero = request.getParameter("telefono");
	String nomeCitta = request.getParameter("citta");
	String provincia = request.getParameter("provincia");
	String codiceFiscale = request.getParameter("codiceFiscale");
	String listaIdProfessioni[] = request.getParameterValues("professione");

	List<String> errori = new ArrayList<>();
	LocalDate ddn=null;
	if (nome == null || nome.trim().length() < 2) {
        errori.add("Il nome deve contenere almeno 2 caratteri.");
    }
    
    if (email == null || !email.contains("@")) {
        errori.add("Per favore, inserisci un indirizzo email valido.");
    }
    try {
        if (utentiInterni.presenzaUtente(email)) {
            errori.add("Questa email è già registrata. Usa un'altra email o fai il login.");
        }
    } catch (Exception e) {
        e.printStackTrace(); 
        errori.add("Si è verificato un problema tecnico durante il controllo dell'email. Riprova più tardi.");
    }
    
    
    if (password == null || password.length() < 8) {
        errori.add("La password deve essere lunga almeno 8 caratteri.");
    }
    
    if (listaIdProfessioni == null || listaIdProfessioni.length == 0) {
        errori.add("Devi selezionare almeno una specializzazione.");
    }
    
    if (dataDiNascita == null || dataDiNascita.trim().isEmpty()) {
        errori.add("La data di nascita è obbligatoria.");
    } else {
        try {
           ddn = LocalDate.parse(dataDiNascita);
            
            LocalDate oggi = LocalDate.now();
            
            Period eta = Period.between(ddn, oggi);
            
            if (eta.getYears() < 18) {
                errori.add("Devi essere maggiorenne (avere almeno 18 anni) per registrarti come professionista.");
            }
            
        } catch (DateTimeParseException e) {
            errori.add("Il formato della data di nascita non è valido.");
        }}
    

    if (!errori.isEmpty()) {
        
   
        List<Professione> tutteLeProfessioni = new MysqlProfessioneDao(DataSourceConfig.getDataSource()).selectAll();
        request.setAttribute("listaProfessioni", tutteLeProfessioni);
        
      
        request.setAttribute("listaErrori", errori); 
        
      
        request.getRequestDispatcher("/PagineWeb/registrazioneProfessionista.jsp").forward(request, response);
        return; 
    }

	
	
	
	
	try {
	    Citta citta =new Citta(nomeCitta, provincia);
	    cittaInterna.getOrCreateCitta(citta);
	    


	    Utente nuovoProf = new Utente(
		    nome, cognome, email, numero, password, ddn,
		    codiceFiscale, Ruolo.PROFESSIONISTA,cittaInterna.getOrCreateCitta(citta)  );

	   Long idUtente= utentiInterni.aggiungiUtente(nuovoProf);
	   for(int i=0;i<listaIdProfessioni.length;i++) {
	   
		   utenteProfessioneInterno.insert( new UtenteProfessione(idUtente,Long.parseLong(listaIdProfessioni[i]), null));
	   }
	   
	   response.sendRedirect(request.getContextPath() + "/loginUtente.jsp?success=prof");

	} catch (RegisterException e) {
	    request.setAttribute("listaErrori", List.of(e.getMessage()));
	    request.getRequestDispatcher("/PagineWeb/registrazioneProfessionista.jsp").forward(request, response);
	} catch (Exception e) {
	    e.printStackTrace();
	    request.setAttribute("listaErrori", List.of("Errore tecnico: riprova più tardi."));
	    request.getRequestDispatcher("/PagineWeb/registrazioneProfessionista.jsp").forward(request, response);
	}
    }
}