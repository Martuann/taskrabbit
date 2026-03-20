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
import org.elis.progetto.model.Utente;

@WebServlet("/AggiornaProfilo")
public class AggiornaProfiloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("utenteLoggato") == null) {
			response.sendRedirect(request.getContextPath() + "/Login");
			return;
		}

		Utente utenteProfilo = (Utente) session.getAttribute("utenteLoggato");

		try {
			String nome = request.getParameter("nome");
			String cognome = request.getParameter("cognome");
			String email = request.getParameter("email");
			String telefono = request.getParameter("telefono");
			String ddnStr = request.getParameter("ddn");
			String idCittaStr = request.getParameter("idCitta");
			String nuovaPw = request.getParameter("nuovaPassword");

			utenteProfilo.setNome(nome);
			utenteProfilo.setCognome(cognome);
			utenteProfilo.setEmail(email);
			utenteProfilo.setTelefono(telefono);

			if (ddnStr != null && !ddnStr.isEmpty()) {
				utenteProfilo.setDdn(LocalDate.parse(ddnStr));
			}

			if (idCittaStr != null && !idCittaStr.isEmpty()) {
				utenteProfilo.setIdCitta(Long.parseLong(idCittaStr));
			}

			if (nuovaPw != null && !nuovaPw.trim().isEmpty()) {
				utenteProfilo.setPassword(nuovaPw);
			}

			DaoFactory.getInstance().getUtenteDao().aggiornaUtente(utenteProfilo);

			session.setAttribute("utenteLoggato", utenteProfilo);
			response.sendRedirect(request.getContextPath() + "/Profilo?update=success");

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Profilo?update=error");
		}
	}
}