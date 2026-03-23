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
import org.elis.progetto.model.Recensione;
import org.elis.progetto.model.Utente;

/**
 * Servlet implementation class ScriviRecensioneServlet
 */
@WebServlet("/ScriviRecensioneServlet")
public class ScriviRecensioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RecensioneDao recensioneDao;

    public ScriviRecensioneServlet() {
        super();
        recensioneDao = DaoFactory.getInstance().getRecensioneDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	    Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");

		Integer voto = Integer.parseInt(request.getParameter("voto"));
		String descrizione = request.getParameter("descrizione");
		Long idRecensore = utenteLoggato.getId();
		Long idRecensito = Long.parseLong(request.getParameter("id"));
		recensioneDao.insert(new Recensione(voto,descrizione,LocalDate.now(),idRecensore,idRecensito));
		response.sendRedirect(request.getContextPath()+"/CronologiaRichiesteServlet");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long id = 0L;
		if(!request.getParameter("idProfessionista").trim().isEmpty() || request.getParameter("idProfessionista")!=null) {
			id = Long.parseLong(request.getParameter("idProfessionista"));
		}
		request.setAttribute("idProfessionista", id);

		request.getRequestDispatcher("/WEB-INF/jsp/utente/scriviRecensione.jsp").forward(request, response);	
	}
}
