package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.OrarioBaseDao;
import org.elis.progetto.model.OrarioBase;
import org.elis.progetto.model.Utente;

@WebServlet("/GestioneOrariDefault")
public class GestioneOrariDefault extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrarioBaseDao orarioProfessionistaDao;

	@Override
	public void init() throws ServletException {
		 orarioProfessionistaDao=DaoFactory.getInstance().getOrarioBaseDao();
	}
    public GestioneOrariDefault() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	HttpSession session = request.getSession();
    	Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");


    	try {
    		request.setAttribute("orarioDefault", orarioProfessionistaDao.getOrariByUtente(utenteLoggato.getId()));
    		request.getRequestDispatcher("/WEB-INF/jsp/professionista/GestioneDisponibilitaBase.jsp").forward(request, response);;    	}
    	catch(Exception e){
			e.printStackTrace();

    		response.sendRedirect(request.getContextPath() + "/GestioneOrariDefault?error=loading");    	}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
    	Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");

    	
    	
    	try {
    		DayOfWeek[] giorniSettimana = DayOfWeek.values();
    		for(int i=0; i<giorniSettimana.length; i++) {
    	    	String booleanSeLavoraoNoString = request.getParameter("lavora_"+i);
    	    	String inizio = request.getParameter("oraInizio_" + i);
    	    	String fine = request.getParameter("oraFine_" + i);
    	    	String giornoStr = request.getParameter("giorno_" + i);
    	
    	    	if(giornoStr != null) {
    	    		int idGiorno = Integer.parseInt(giornoStr);
    	    		DayOfWeek giornata = DayOfWeek.of(idGiorno);
    	    		
    		    	if(booleanSeLavoraoNoString != null&& "true".equals(booleanSeLavoraoNoString)) {
    		    		LocalTime orarioInizio = LocalTime.parse(inizio);
    		    	    LocalTime orarioFine = LocalTime.parse(fine);

    		    	    if (orarioInizio.isBefore(orarioFine)) {
                            orarioProfessionistaDao.salvaOrario(new OrarioBase(giornata, orarioInizio, orarioFine, utenteLoggato));
                        } else {
                            System.out.println("Orario non valido per " + giornata);
                        }
    		    	} else {
    		    		orarioProfessionistaDao.eliminaOrario(utenteLoggato.getId(), idGiorno);
    				}
    	    	}
    		}
    		response.sendRedirect(request.getContextPath() + "/GestioneOrariDefault?success=true");
    	} catch (Exception e) {
			e.printStackTrace();
    		response.sendRedirect(request.getContextPath() + "/GestioneOrariDefault?error=save");
    	}
	}
}
