package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.ProfessioneDao;
import org.elis.progetto.model.Professione;


@WebServlet("/HomepageServlet")
public class HomepageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProfessioneDao professioneDao;

	public void init() throws ServletException {
	    professioneDao = DaoFactory.getInstance().getProfessioneDao();  
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<Professione> professioni = professioneDao.selectAll();
			request.setAttribute("professioni", professioni);

			request.getRequestDispatcher("/WEB-INF/jsp/pubblico/Homepage.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel caricamento della homepage: " + e.getMessage());
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
