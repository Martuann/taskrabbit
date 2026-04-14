package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

import org.elis.dao.definition.CittaDao;
import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.UtenteDao;
import org.elis.progetto.model.Citta;
import org.elis.progetto.model.Utente;

@WebServlet("/AggiornaProfilo")
public class AggiornaProfiloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private  CittaDao cittaDao;
    private UtenteDao utenteDao;
	@Override
	public void init() throws ServletException {
		cittaDao=DaoFactory.getInstance().getCittaDao();
        utenteDao=DaoFactory.getInstance().getUtenteDao();

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utente utenteProfilo = (Utente) session.getAttribute("utenteLoggato");

		try {
			String nome = request.getParameter("nome");
			String cognome = request.getParameter("cognome");
			String email = request.getParameter("email");
			String telefono = request.getParameter("telefono");
			String ddnStr = request.getParameter("ddn");
			String idCittaStr = request.getParameter("idCitta");
			String nuovaPw = request.getParameter("nuovaPassword");
			
			if(nome != null && nome.length() > 0) {
			    nome = nome.substring(0, 1).toUpperCase() + (nome.length() > 1 ? nome.substring(1) : "");
			}
			if(cognome != null && cognome.length() > 0) {
				cognome = cognome.substring(0,1).toUpperCase() + cognome.substring(1);
			}
			
			utenteProfilo.setNome(nome);
			utenteProfilo.setCognome(cognome);
			utenteProfilo.setEmail(email);
			utenteProfilo.setTelefono(telefono);

			if (ddnStr != null && !ddnStr.isEmpty()) {
				utenteProfilo.setDdn(LocalDate.parse(ddnStr));
			}

			if (idCittaStr != null && !idCittaStr.isEmpty()) {
		Citta	citta	=cittaDao.selectById(Long.parseLong(idCittaStr));
				
				utenteProfilo.setCitta(citta);
			}

			if (nuovaPw != null && !nuovaPw.trim().isEmpty()) {
				utenteProfilo.setPassword(nuovaPw);
			}

			Utente utenteAgg=utenteDao.aggiornaUtente(utenteProfilo);

			session.setAttribute("utenteLoggato", utenteAgg);
			
			response.sendRedirect(request.getContextPath() + "/Profilo?update=success");

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/Profilo?update=error");
		}
	}
}