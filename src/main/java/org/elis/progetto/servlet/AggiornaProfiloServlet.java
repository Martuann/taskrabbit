package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.elis.dao.definition.CittaDao;
import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.UtenteDao;
import org.elis.progetto.model.Citta;
import org.elis.progetto.model.Utente;

@WebServlet("/AggiornaProfilo")
public class AggiornaProfiloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private  CittaDao cittaDao;
    private UtenteDao utenteDao;
	@Override
	public void init() throws ServletException {
		cittaDao=DaoFactory.getInstance().getCittaDao();
        utenteDao=DaoFactory.getInstance().getUtenteDao();

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utente utenteProfilo = (Utente) session.getAttribute("utenteLoggato");

		
			String nome = request.getParameter("nome");
			String cognome = request.getParameter("cognome");
			String email = request.getParameter("email");
			String telefono = request.getParameter("telefono");
			String ddnStr = request.getParameter("ddn");
			String idCittaStr = request.getParameter("idCitta");
			String nuovaPw = request.getParameter("nuovaPassword");
		    List<String> errori = new ArrayList<>();

			 if (nome == null || nome.trim().length() < 2)
			        errori.add("Il nome deve contenere almeno 2 caratteri.");

			    if (cognome == null || cognome.trim().length() < 2)
			        errori.add("Il cognome deve contenere almeno 2 caratteri.");

			    if (email == null || !email.contains("@"))
			        errori.add("Inserisci un indirizzo email valido.");

			    if (telefono == null || !telefono.matches("[0-9]{8,15}"))
			        errori.add("Il numero di telefono non è valido.");

			    LocalDate ddn = null;
			    if (ddnStr != null && !ddnStr.isEmpty()) {
			        try {
			            ddn = LocalDate.parse(ddnStr);
			            if (Period.between(ddn, LocalDate.now()).getYears() < 18)
			                errori.add("Devi essere maggiorenne.");
			        } catch (DateTimeParseException e) {
			            errori.add("Formato data non valido.");
			        }
			    }

			    if (nuovaPw != null && !nuovaPw.trim().isEmpty() && nuovaPw.length() < 8)
			        errori.add("La nuova password deve essere lunga almeno 8 caratteri.");

			    if (!errori.isEmpty()) {
			    	request.setAttribute("listaErrori", errori);
			    	caricaDatiForm(request);
			    	request.getRequestDispatcher("/Profilo").forward(request, response);
			    	return;
			    }

			    try {
			        if (nome != null && !nome.isEmpty())
			            utenteProfilo.setNome(nome.substring(0,1).toUpperCase() + nome.substring(1));
			        if (cognome != null && !cognome.isEmpty())
			            utenteProfilo.setCognome(cognome.substring(0,1).toUpperCase() + cognome.substring(1));

			        utenteProfilo.setEmail(email.trim());
			        utenteProfilo.setTelefono(telefono);

			        if (ddn != null)
			            utenteProfilo.setDdn(ddn);

			        if (idCittaStr != null && !idCittaStr.isEmpty())
			            utenteProfilo.setCitta(cittaDao.selectById(Long.parseLong(idCittaStr)));

			        if (nuovaPw != null && !nuovaPw.trim().isEmpty())
			            utenteProfilo.setPassword(nuovaPw);

			        Utente utenteAgg = utenteDao.aggiornaUtente(utenteProfilo);
			        session.setAttribute("utenteLoggato", utenteAgg);
			        response.sendRedirect(request.getContextPath() + "/Profilo?update=success");

			    } catch (Exception e) {
			        e.printStackTrace();
			        request.setAttribute("erroreGenerico", "Errore durante l'aggiornamento.");
			     caricaDatiForm(request);
			        request.getRequestDispatcher("/Profilo").forward(request, response);			    }

		
	}
	
    private void caricaDatiForm(HttpServletRequest request) {
        try {
            request.setAttribute("listaCitta", cittaDao.getAllCitta());
        } catch (Exception e) {
            e.printStackTrace();
        }
	
}}
	