package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
		Long id = Long.parseLong(request.getParameter("id"));
		request.getRequestDispatcher("/PagineWeb/scriviRecensione.jsp?id="+id).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer voto = Integer.parseInt(request.getParameter("voto"));
		String descrizione = request.getParameter("descrizione");
		Long idRecensore = ((Utente) request.getSession().getAttribute("utenteLoggato")).getId();
		Long idRecensito = Long.parseLong(request.getParameter("id"));
		recensioneDao.insert(new Recensione(voto,descrizione,LocalDate.now(),idRecensore,idRecensito));
		response.sendRedirect(request.getContextPath()+"/CronologiaRichiesteServlet");
	}
}
