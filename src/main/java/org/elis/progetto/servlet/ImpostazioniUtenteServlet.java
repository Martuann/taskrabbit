package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.UtenteDao;
import org.elis.progetto.model.Utente;

@WebServlet("/Impostazioni")
public class ImpostazioniUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtenteDao utenteDao;

	@Override
	public void init() throws ServletException {
		utenteDao = DaoFactory.getInstance().getUtenteDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("utenteLoggato") == null) {
			response.sendRedirect(request.getContextPath() + "/Login");
			return;
		}
		request.getRequestDispatcher("/PagineWeb/impostazioni.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utente utente = (Utente) session.getAttribute("utenteLoggato");

		if (utente == null) {
			response.sendRedirect(request.getContextPath() + "/Login");
			return;
		}

		String azione = request.getParameter("azione");

		try {
			if ("cambiaPassword".equals(azione)) {
				String vecchiaPass = request.getParameter("vecchiaPassword");
				String nuovaPass = request.getParameter("nuovaPassword");

				if (utente.getPassword().equals(vecchiaPass)) {
					utente.setPassword(nuovaPass);

					utenteDao.aggiornaUtente(utente);

					session.setAttribute("utenteLoggato", utente);
					response.sendRedirect(request.getContextPath() + "/Impostazioni?success=true");
				} else {
					response.sendRedirect(request.getContextPath() + "/Impostazioni?errorPass=true");
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore DB: " + e.getMessage());
		}
	}
}


