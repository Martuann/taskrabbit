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

/**
 * Servlet implementation class GestioneOrariDefault
 */
@WebServlet("/GestioneOrariDefault")
public class GestioneOrariDefault extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneOrariDefault() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//UTENTE DI PROVA RICORDARSI DI CANCELLARLO
    	Utente utenteTestDiProva = new Utente();
        utenteTestDiProva.setId(1L);
        utenteTestDiProva.setNome("Mario");
        utenteTestDiProva.setCognome("Rossi");
        request.getSession().setAttribute("utenteLoggato", utenteTestDiProva);
        utenteTestDiProva.setRuolo(Ruolo.PROFESSIONISTA);

		
		
		
		
		
		
		
		
		
		
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
    		
            response.sendError(500, "Errore nel caricamento dati: " + e.getMessage());

    		
    	}
    	

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
    	Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");
    	OrarioBaseDao orarioProfessionista = new MysqlOrarioBaseDao(DataSourceConfig.getDataSource());

    	for(int i=0;i<DayOfWeek.values().length;i++) {
    	String booleanSeLavoraoNoString =request.getParameter("lavora_"+i);
    	String inizio =request.getParameter("oraInizio_" + i);
    	String fine = request.getParameter("oraFine_" + i);
    	String giornoStr = request.getParameter("giorno_" + i);

    	if((booleanSeLavoraoNoString != null)) {
    		try {
    			LocalTime orarioInizio = LocalTime.parse(inizio);
    	        LocalTime orarioFine =  LocalTime.parse(fine);
                DayOfWeek giornata = DayOfWeek.of(Integer.parseInt(giornoStr));
                orarioProfessionista.salvaOrario(new OrarioBase(giornata,orarioInizio,orarioFine,utenteLoggato.getId()));
    		}
    		catch(Exception e) {}}
    		else if(booleanSeLavoraoNoString== null){
    			
                DayOfWeek giornata = DayOfWeek.of(Integer.parseInt(giornoStr));
                try {
					orarioProfessionista.eliminaOrario(utenteLoggato.getId(),Integer.parseInt(giornoStr));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
    			
    			
    			
}	
    		
    		

    		

    	}
    	response.sendRedirect(request.getContextPath() + "/GestioneOrariDefault");
	}

}
