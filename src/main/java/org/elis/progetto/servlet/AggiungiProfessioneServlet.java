package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.elis.dao.mysql.MysqlProfessioneDao;
import org.elis.progetto.model.Professione;
import org.elis.progetto.model.Ruolo;
import org.elis.progetto.model.Utente;
import org.elis.utilities.DataSourceConfig;

@WebServlet("/AggiungiProfessione")
public class AggiungiProfessioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*HttpSession session = request.getSession();
		Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");

		if (utenteLoggato == null) {
			response.sendRedirect(request.getContextPath() + "/PagineWeb/login.html");
			return;
		}
		if (utenteLoggato.getRuolo()!=Ruolo.ADMIN) {
			response.sendRedirect(request.getContextPath() + "/PagineWeb/Homepage.html");
			return;
		}*/
		request.getRequestDispatcher("/WEB-INF/pagineAdmin/aggiungiProfessione.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");

		if (utenteLoggato == null || utenteLoggato.getRuolo() != Ruolo.ADMIN) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN); // Blocco tentativi non autorizzati
			return;
		}

		String nomeProf = request.getParameter("nomeProfessione");

		if (nomeProf != null && !nomeProf.trim().isEmpty()) {
			try {
				MysqlProfessioneDao dao = new MysqlProfessioneDao(DataSourceConfig.getDataSource());
				if (nomeProf.length() > 30) {
					request.setAttribute("messaggio", "Errore: nome troppo lungo.");
				} else {
					dao.insert(new Professione(nomeProf.trim()));
					request.setAttribute("messaggio", "Professione aggiunta con successo!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("messaggio", "Errore nel salvataggio.");
			}
		} else {
			request.setAttribute("messaggio", "Inserisci un nome valido.");
		}
		request.getRequestDispatcher("/WEB-INF/pagineAdmin/aggiungiProfessione.jsp").forward(request, response);
	}
}

