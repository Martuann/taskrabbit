package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.RecensioneDao;
import org.elis.dao.definition.UtenteDao;
import org.elis.progetto.model.Recensione;
import org.elis.progetto.model.Utente;

/**
 * Servlet implementation class ScriviRecensioneServlet
 */
@WebServlet("/ScriviRecensioneServlet")
public class ScriviRecensioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RecensioneDao recensioneDao;
private UtenteDao utenteDao;
    public ScriviRecensioneServlet() {
        super();
        recensioneDao = DaoFactory.getInstance().getRecensioneDao();
        utenteDao=DaoFactory.getInstance().getUtenteDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	    Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");

		String votoStr=request.getParameter("voto");
		String descrizione=request.getParameter("descrizione");
		String idRecensitoStr=	request.getParameter("id");
		
		
		
		
		if (votoStr != null && idRecensitoStr != null) {
	        try {
	            int voto = Integer.parseInt(votoStr);
	            long idRecensito = Long.parseLong(idRecensitoStr);
	            
	            Utente utenteRecensito = utenteDao.ricercaPerId(idRecensito);
	            
	            if (utenteRecensito != null && utenteLoggato != null) {
	                Recensione r = new Recensione();
	                r.setVoto(voto);
	                r.setDescrizione(descrizione);
	                r.setData(LocalDate.now());
	                r.setUtenteScrittore(utenteLoggato);
	                r.setUtenteRicevente(utenteRecensito);
	                
	                recensioneDao.insert(r);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendRedirect(request.getContextPath() + "/CronologiaRichiesteServlet?error=true");
	            return;
	        }
		
		response.sendRedirect(request.getContextPath()+"/CronologiaRichiesteServlet");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long id = 0L;
		if(request.getParameter("idProfessionista")!=null||!request.getParameter("idProfessionista").trim().isEmpty() ) {
			id = Long.parseLong(request.getParameter("idProfessionista"));
		}
		request.setAttribute("idProfessionista", id);

		request.getRequestDispatcher("/WEB-INF/jsp/utente/scriviRecensione.jsp").forward(request, response);	
	}
}
