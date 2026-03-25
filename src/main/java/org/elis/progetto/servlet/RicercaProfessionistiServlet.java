package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import org.elis.progetto.model.Citta;
import org.elis.progetto.model.Utente;
import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.UtenteDao;


@WebServlet("/RicercaProfessionistiServlet")
public class RicercaProfessionistiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtenteDao utenteDao;

	public RicercaProfessionistiServlet() {
		super();
		utenteDao = DaoFactory.getInstance().getUtenteDao();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String professioneCercata = request.getParameter("professione");
		//String idCittaCambiataStr = request.getParameter("idCittaCambiata");
		String nomeCittaCambiata = request.getParameter("nomeCittaCambiata");
		String action = request.getParameter("action");

		Utente utenteLoggato = (Utente) request.getSession().getAttribute("utenteLoggato");

		long idCittaDaFiltrare = -1;
		String messaggioFiltro = "in tutta Italia";

		if (nomeCittaCambiata != null && !nomeCittaCambiata.trim().isEmpty()) {
			try {

				String cittaDaPulire = nomeCittaCambiata.split(" \\(")[0].trim();
		        Citta c = DaoFactory.getInstance().getCittaDao().getByName(cittaDaPulire);
		        if (c != null) {
		            idCittaDaFiltrare = c.getId();
		            messaggioFiltro = "a " + c.getNome();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (utenteLoggato != null && !"reset".equals(action)) {
			idCittaDaFiltrare = utenteLoggato.getIdCitta();
			try {
				Citta c = DaoFactory.getInstance().getCittaDao().selectById(idCittaDaFiltrare);
				if (c != null) messaggioFiltro = "a " + c.getNome();
			} catch (Exception e) { e.printStackTrace(); }
		}


		if (professioneCercata == null || professioneCercata.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/HomepageServlet");
			return;
		}


		try {
			List<Utente> listaTrovati = utenteDao.ricercaTramiteProfessione(professioneCercata);
			List<Citta> tutteLeCitta = DaoFactory.getInstance().getCittaDao().getAllCitta();


			if (idCittaDaFiltrare != -1 && listaTrovati != null) {
				long finalId = idCittaDaFiltrare;
				listaTrovati.removeIf(u -> u.getIdCitta() != finalId);
			}

			request.setAttribute("listaCitta", tutteLeCitta);
			request.setAttribute("idCittaRicerca", idCittaDaFiltrare);
			request.setAttribute("professionisti", listaTrovati);
			request.setAttribute("query", professioneCercata);
			request.setAttribute("messaggioFiltro", messaggioFiltro);
			request.getRequestDispatcher("/WEB-INF/jsp/pubblico/RisultatiRicerca.jsp").forward(request, response);


		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(500);

		}
	}




	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
