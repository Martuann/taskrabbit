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
		Long id1 = Long.parseLong(request.getParameter("id1"));
		Long id2 = Long.parseLong(request.getParameter("id2"));
		request.getRequestDispatcher("/PagineWeb/scriviRecensione.jsp?id1="+id1+"&?id2="+id2).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer voto = Integer.parseInt(request.getParameter("voto"));
		String descrizione = request.getParameter("descrizione");
		Long idRecensore = Long.parseLong(request.getParameter("id1"));
		Long idRecensito = Long.parseLong(request.getParameter("id2"));
		recensioneDao.insert(new Recensione(voto,descrizione,LocalDate.now(),idRecensore,idRecensito));
		response.sendRedirect("CronologiaRichiesteServlet?id="+idRecensore);
	}
}
