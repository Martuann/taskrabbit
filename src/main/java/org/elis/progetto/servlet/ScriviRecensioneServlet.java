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
import org.elis.dao.definition.RecensioneDao;
import org.elis.dao.definition.RichiestaDao;
import org.elis.dao.definition.UtenteDao;
import org.elis.progetto.model.Recensione;
import org.elis.progetto.model.Utente;

/**
 * Servlet implementation class ScriviRecensioneServlet
 */
@WebServlet("/ScriviRecensioneServlet")
public class ScriviRecensioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RecensioneDao recensioneDao;
	private RichiestaDao richiestaDao;
private UtenteDao utenteDao;
	public void init() throws ServletException {
        richiestaDao = DaoFactory.getInstance().getRichiestaDao();
        recensioneDao = DaoFactory.getInstance().getRecensioneDao();
        utenteDao=DaoFactory.getInstance().getUtenteDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	    Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");

		String votoStr=request.getParameter("voto");
		String descrizione=request.getParameter("descrizione");
		String idRecensitoStr=	request.getParameter("id");
		
		
		
		if (votoStr == null || idRecensitoStr == null || utenteLoggato == null) {
	        response.sendRedirect(request.getContextPath() + "/CronologiaRichiesteServlet?error=missing_data");
	        return;
	    }
		
		
		
		try {
            int voto = Integer.parseInt(votoStr);
            long idRecensito = Long.parseLong(idRecensitoStr);
            if (voto < 1 || voto > 5) {
                response.sendRedirect(request.getContextPath() + "/CronologiaRichiesteServlet?error=invalid_vote");
                return;
            }
            Utente utenteRecensito = utenteDao.ricercaPerId(idRecensito);
		
		if (utenteRecensito != null) {
            if (richiestaDao.haLavorato(utenteLoggato.getId(), utenteRecensito.getId())) {
            	if (!recensioneDao.esisteRecensionePerRichiesta(utenteLoggato.getId(), utenteRecensito.getId())) {
                    
                    Recensione r = new Recensione();
                    r.setVoto(voto);
                    r.setDescrizione(descrizione);
                    r.setData(LocalDate.now());
                    r.setUtenteScrittore(utenteLoggato);
                    r.setUtenteRicevente(utenteRecensito);
                    
                    recensioneDao.insert(r);
                    
                    response.sendRedirect(request.getContextPath()+"/CronologiaRichiesteServlet?success=true");
                    return;
                } else {
                	response.sendRedirect(request.getContextPath() + "/CronologiaRichiesteServlet?error=already_reviewed");
                	return;
                }
            } else {
            	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Non autorizzato a recensire questo utente.");
            	return;
            }
          
        }else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Professionista non trovato.");
            return;
        }
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendRedirect(request.getContextPath() + "/CronologiaRichiesteServlet?error=true");
	            return;
	        }
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idProfStr = request.getParameter("idProfessionista");
		if (idProfStr == null || idProfStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id Professionista mancante.");
            return;
        }

        try {
            long id = Long.parseLong(idProfStr);
            request.setAttribute("idProfessionista", id);
            request.getRequestDispatcher("/WEB-INF/jsp/utente/scriviRecensione.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id Professionista non valido.");
        }
	}
}
