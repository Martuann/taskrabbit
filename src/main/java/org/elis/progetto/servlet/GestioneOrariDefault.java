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
import java.util.List;

import org.elis.dao.definition.OrarioBaseDao;
import org.elis.dao.mysql.MysqlOrarioBaseDao;
import org.elis.progetto.model.OrarioBase;
import org.elis.progetto.model.Ruolo;
import org.elis.progetto.model.Utente;
import org.elis.utilities.DataSourceConfig;

@WebServlet("/GestioneOrariDefault")
public class GestioneOrariDefault extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GestioneOrariDefault() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Utente utenteTestDiProva = new Utente();
        utenteTestDiProva.setId(1L);
        utenteTestDiProva.setNome("Mario");
        utenteTestDiProva.setCognome("Rossi");
        utenteTestDiProva.setRuolo(Ruolo.PROFESSIONISTA);
        request.getSession().setAttribute("utenteLoggato", utenteTestDiProva);

    	HttpSession session = request.getSession();
    	Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");

    	if (utenteLoggato == null) {
    	    response.sendRedirect(request.getContextPath() + "/login.jsp"); 
    	    return;
    	}
    	if (utenteLoggato.getRuolo()!=Ruolo.PROFESSIONISTA) {
    	    response.sendRedirect(request.getContextPath() + "/index.jsp"); 
    	    return;
    	}
    	OrarioBaseDao orarioProfessionista = new MysqlOrarioBaseDao(DataSourceConfig.getDataSource());

    	try {
    		request.setAttribute("orarioDefault", orarioProfessionista.getOrariByUtente(utenteLoggato.getId()));
    		request.getRequestDispatcher("/PagineWeb/GestioneDisponibilitaBase.jsp").forward(request, response);
    	}
    	catch(Exception e){
    		response.sendRedirect(request.getContextPath() + "/GestioneOrariDefault?error=loading");    	}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
    	Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");
    	OrarioBaseDao orarioProfessionista = new MysqlOrarioBaseDao(DataSourceConfig.getDataSource());

    	try {
    		for(int i=0; i<DayOfWeek.values().length; i++) {
    	    	String booleanSeLavoraoNoString = request.getParameter("lavora_"+i);
    	    	String inizio = request.getParameter("oraInizio_" + i);
    	    	String fine = request.getParameter("oraFine_" + i);
    	    	String giornoStr = request.getParameter("giorno_" + i);
    	
    	    	if(giornoStr != null) {
    	    		int idGiorno = Integer.parseInt(giornoStr);
    	    		DayOfWeek giornata = DayOfWeek.of(idGiorno);
    	    		
    		    	if(booleanSeLavoraoNoString != null) {
    		    		LocalTime orarioInizio = LocalTime.parse(inizio);
    		    	    LocalTime orarioFine = LocalTime.parse(fine);
    		            orarioProfessionista.salvaOrario(new OrarioBase(giornata, orarioInizio, orarioFine, utenteLoggato.getId()));
    		    	} else {
    	                orarioProfessionista.eliminaOrario(utenteLoggato.getId(), idGiorno);
    				}
    	    	}
    		}
    		response.sendRedirect(request.getContextPath() + "/GestioneOrariDefault?success=true");
    	} catch (Exception e) {
    		response.sendRedirect(request.getContextPath() + "/GestioneOrariDefault?error=save");
    	}
	}
}
