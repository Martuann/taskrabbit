package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import org.elis.dao.definition.DaoFactory;
import org.elis.progetto.model.Citta;
import org.elis.progetto.model.Utente;

@WebServlet("/Profilo")
public class ProfiloUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		

		Utente utenteProfilo = (Utente) session.getAttribute("utenteLoggato");

		try {

			Citta cittaUtente = DaoFactory.getInstance().getCittaDao().selectById( utenteProfilo.getCitta().getId());
			request.setAttribute("cittaDati", cittaUtente);


			List<Citta> listaCitta = DaoFactory.getInstance().getCittaDao().getAllCitta();
			request.setAttribute("listaCitta", listaCitta);


			request.getRequestDispatcher("/WEB-INF/jsp/utente/profiloUtente.jsp").forward(request, response);;
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(500, "Errore nel caricamento dei dati del profilo.");
		}
	}
}