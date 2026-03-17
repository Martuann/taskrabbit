package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.RichiestaDao;
import org.elis.progetto.model.Richiesta;
import org.elis.progetto.model.StatoRichiesta;

/**
 * Servlet implementation class AggiornaRichiesta
 */
@WebServlet("/AggiornaRichiesta")
public class AggiornaRichiestaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RichiestaDao richiestaDao;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AggiornaRichiestaServlet() {
        super();
        richiestaDao = DaoFactory.getInstance().getRichiestaDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StatoRichiesta stato = StatoRichiesta.valueOf(request.getParameter("type"));
		Long idRichiesta = Long.parseLong(request.getParameter("id1"));
		Richiesta richiesta = richiestaDao.selectById(idRichiesta);
		richiesta.setStato(stato);
		richiestaDao.update(richiesta);
		if(request.getParameter("redirect")!=null) {
			response.sendRedirect(request.getParameter("redirect")+"?id="+request.getParameter("id2"));
			return;
		}
		response.sendRedirect("GestioneRichiesteServlet?id="+request.getParameter("id2"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
