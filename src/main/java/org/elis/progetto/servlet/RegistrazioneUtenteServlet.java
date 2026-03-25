package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import org.elis.dao.definition.CittaDao;
import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.UtenteDao;
import org.elis.exception.RegisterException;
import org.elis.progetto.model.Citta;
import org.elis.progetto.model.Ruolo;
import org.elis.progetto.model.Utente;

@WebServlet("/RegistrazioneUtenteServlet")
public class RegistrazioneUtenteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UtenteDao utenteDao;
   private CittaDao cittaDao;
	@Override
	public void init() throws ServletException {

		utenteDao = DaoFactory.getInstance().getUtenteDao();
		cittaDao=DaoFactory.getInstance().getCittaDao();
	}
    public RegistrazioneUtenteServlet() {
	super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            List<Citta> tutteLeCitta = cittaDao.getAllCitta();
            request.setAttribute("listaCitta", tutteLeCitta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/WEB-INF/jsp/pubblico/registrazioneUtente.jsp").forward(request, response);
    }	    

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	String nome = request.getParameter("nome");
    	String cognome = request.getParameter("cognome");
    	String email = request.getParameter("email");
    	String password = request.getParameter("password");
    	String dataDiNascita = request.getParameter("dataNascita");
    	String numero = request.getParameter("telefono");
    	String nomeCitta = request.getParameter("citta");
    	String provincia = request.getParameter("provincia");
    	String codiceFiscale = request.getParameter("codiceFiscale");
    	String idCittaStr = request.getParameter("id_citta");
    	
    	List<String> errori = new ArrayList<>();
    	LocalDate ddn=null;
    	if (nome == null || nome.trim().length() < 2) {
    	    errori.add("Il nome deve contenere almeno 2 caratteri.");
    	}

    	if (cognome == null || cognome.trim().length() < 2) {
    	    errori.add("Il cognome deve contenere almeno 2 caratteri.");
    	}

    	if (email.isEmpty() || !email.contains("@")) {
    	    errori.add("Per favore, inserisci un indirizzo email valido.");
    	} else {
    	    try {

    	        if (utenteDao.presenzaUtente(email)) {
    	            errori.add("Questa email è già registrata. Usa un'altra email o fai il login.");
    	        }
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        errori.add("Errore tecnico nel controllo email.");
    	    }
    	}

    	if (codiceFiscale.length() != 16) {
    	    errori.add("Il Codice Fiscale deve essere di 16 caratteri.");
    	} else {
    	    try {

    	        if (utenteDao.presenzaCodiceFiscale(codiceFiscale)) {
    	        	errori.add("Questo Codice Fiscale è già associato a un account.");
    	        }
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        errori.add("Errore tecnico nel controllo del Codice Fiscale.");
    	    }
    	}

    	if (numero.isEmpty()) {
    	    errori.add("Il numero di telefono è obbligatorio.");
    	} else if (!numero.matches("[0-9]{8,15}")) {
    	    errori.add("Il numero di telefono non è valido (inserire solo numeri, tra 8 e 15 cifre).");
    	} else {
    	    try {

    	        if (utenteDao.presenzaTelefono(numero)) {
    	            errori.add("Questo numero di telefono è già associato a un altro account.");
    	        }
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        errori.add("Errore tecnico nel controllo del numero di telefono.");
    	    }
    	}



        if (password == null || password.length() < 8) {
            errori.add("La password deve essere lunga almeno 8 caratteri.");
        }



        if (dataDiNascita == null || dataDiNascita.trim().isEmpty()) {
            errori.add("La data di nascita è obbligatoria.");
        } else {
            try {
               ddn = LocalDate.parse(dataDiNascita);

                LocalDate oggi = LocalDate.now();

                Period eta = Period.between(ddn, oggi);

                if (eta.getYears() < 18) {
                    errori.add("Devi essere maggiorenne per registrarti.");
                }

            } catch (DateTimeParseException e) {
                errori.add("Il formato della data di nascita non è valido.");
            }}


        if (!errori.isEmpty()) {




            request.setAttribute("listaErrori", errori);

            caricaCitta(request);
            request.getRequestDispatcher("/WEB-INF/jsp/pubblico/registrazioneUtente.jsp").forward(request, response);
            return;
        }




    	try {
    		Long idCitta = Long.parseLong(idCittaStr);
            Citta cittaScelta = cittaDao.selectById(idCitta);
    		



    	    Utente nuovoUtente = new Utente(
    		    nome, cognome, email, numero, password, ddn,
    		    codiceFiscale, Ruolo.UTENTE_BASE,cittaScelta  );


    	    utenteDao.aggiungiUtente(nuovoUtente);

    	    response.sendRedirect(request.getContextPath() + "/Login?registrato=true");

    	} catch (RegisterException e) {
    		caricaCitta(request);
    	    request.setAttribute("listaErrori", List.of(e.getMessage()));
    	    request.getRequestDispatcher("/WEB-INF/jsp/pubblico/registrazioneUtente.jsp").forward(request, response);
    	} catch (Exception e) {
    		caricaCitta(request);
    	    e.printStackTrace();
    	    request.setAttribute("listaErrori", List.of("Errore tecnico: riprova più tardi."));
    	    request.getRequestDispatcher("/WEB-INF/jsp/pubblico/registrazioneUtente.jsp").forward(request, response);
    	}
        }
    


private void caricaCitta(HttpServletRequest request) {
    try {
        List<Citta> tutteLeCitta = cittaDao.getAllCitta();
        request.setAttribute("listaCitta", tutteLeCitta);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}

