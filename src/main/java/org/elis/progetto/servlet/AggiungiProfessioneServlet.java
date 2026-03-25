package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.ProfessioneDao;
import org.elis.progetto.model.Professione;

@WebServlet("/AggiungiProfessione")
public class AggiungiProfessioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProfessioneDao professioneDao;

	@Override
	public void init() throws ServletException {
	
		professioneDao = DaoFactory.getInstance().getProfessioneDao();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/admin/aggiungiProfessione.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomeProf = request.getParameter("nomeProfessione");

		if (nomeProf != null && !nomeProf.trim().isEmpty()) {
			try {
				if (nomeProf.length() > 30) {
					request.setAttribute("messaggio", "Errore: nome troppo lungo.");
				} else {
					professioneDao.insert(new Professione(nomeProf.trim()));
					request.setAttribute("messaggio", "Professione aggiunta con successo!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("messaggio", "Errore nel salvataggio.");
			}
		} else {
			request.setAttribute("messaggio", "Inserisci un nome valido.");
		}
		request.getRequestDispatcher("/WEB-INF/jsp/admin/aggiungiProfessione.jsp").forward(request, response);
	}
}

