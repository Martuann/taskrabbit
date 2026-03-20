package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.DisponibilitaDao;
import org.elis.dao.definition.OrarioBaseDao;
import org.elis.progetto.model.Disponibilita;
import org.elis.progetto.model.OrarioBase;
import org.elis.progetto.model.Ruolo;
import org.elis.progetto.model.Utente;

/**
 * Servlet implementation class GestioneOrariDateSpecifiche
 */
@WebServlet("/GestioneOrariDateSpecifiche")
public class GestioneOrariDateSpecifiche extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrarioBaseDao orarioDao;
	private DisponibilitaDao dispoDao;
	@Override
	public void init() throws ServletException {
		orarioDao=DaoFactory.getInstance().getOrarioBaseDao();
	    dispoDao=DaoFactory.getInstance().getDisponibilitaDao();
	}    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneOrariDateSpecifiche() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
    	if (utenteLoggato.getRuolo() != Ruolo.PROFESSIONISTA) {
    	    response.sendRedirect(request.getContextPath() + "/index.jsp"); 
    	    return;
    	}
 
        try {
        	request.setAttribute("orarioStandard", orarioDao.getOrariByUtente(utenteLoggato.getId()));
        	request.setAttribute("orarioSettimana", dispoDao.getDisponibilitaPerUtente(utenteLoggato.getId()));
            
            request.getRequestDispatcher("/PagineWeb/GestioneDisponibilitaSettimanale.jsp").forward(request, response);
        } catch(Exception e){
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/GestioneOrariDefault?error=loading");
        }
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	    Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");

	    if (utenteLoggato == null || utenteLoggato.getRuolo() != Ruolo.PROFESSIONISTA) {
	        response.sendRedirect("login.jsp");
	        return;
	    }

	    String offSetParam = request.getParameter("offSetAttuale");
	    int offSet = (offSetParam != null) ? Integer.parseInt(offSetParam) : 0;

	    try {
	        for (int i = 0; i < 7; i++) {
	            String lavora = request.getParameter("lavora_" + i);
	            String inizio = request.getParameter("oraInizio_" + i);
	            String fine = request.getParameter("oraFine_" + i);
	            String dataStr = request.getParameter("data_" + i);

	            if (dataStr != null) {
	                LocalDate dataCorrente = LocalDate.parse(dataStr);
	                
	                if (lavora != null) {
	                    LocalTime orarioInizio = LocalTime.parse(inizio);
	                    LocalTime orarioFine = LocalTime.parse(fine);
	                    
	                    Disponibilita disp = new Disponibilita(utenteLoggato.getId(), dataCorrente, orarioInizio, orarioFine);
	                    OrarioBase base = orarioDao.getOrariByUtenteEGiorno(utenteLoggato.getId(), dataCorrente.getDayOfWeek());

	                    if (base != null && base.getOraInizio().equals(orarioInizio) && base.getOraFine().equals(orarioFine)) {
	                        dispoDao.rimuoviDisponibilitaByIdUtenteData(utenteLoggato.getId(), dataCorrente);
	                    } else {
	                        dispoDao.salvaOAggiorna(disp);
	                    }
	                } else {
	                    Disponibilita chiuso = new Disponibilita(
	                        utenteLoggato.getId(), 
	                        dataCorrente, 
	                        LocalTime.MIDNIGHT, 
	                        LocalTime.MIDNIGHT  
	                    );
	                    dispoDao.salvaOAggiorna(chiuso);
	                }
	            }
	        }
	        response.sendRedirect("GestioneOrariDateSpecifiche?offSet=" + offSet + "&success=true");
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendRedirect("GestioneOrariDateSpecifiche?offSet=" + offSet + "&error=save");
	    }
	}
	}