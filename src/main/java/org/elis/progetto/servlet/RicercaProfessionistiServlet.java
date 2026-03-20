package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.elis.progetto.model.Utente;
import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.UtenteDao;

/**
 * Servlet implementation class RicercaProfessionistiServlet
 */
@WebServlet("/RicercaProfessionistiServlet")
public class RicercaProfessionistiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtenteDao utenteDao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RicercaProfessionistiServlet() {
        super();
        utenteDao = DaoFactory.getInstance().getUtenteDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String professioneCercata = request.getParameter("professione");
        
  
        if (professioneCercata == null || professioneCercata.isEmpty()) {
            response.sendRedirect("/HomepageServlet");
            return;
        }

       
        try {
            List<Utente> listaTrovati = utenteDao.ricercaTramiteProfessione(professioneCercata);
            Utente utenteLoggato = (Utente) request.getSession().getAttribute("utenteLoggato");
            for(Utente u : listaTrovati) {
            	if(u.getId()==utenteLoggato.getId()) {
            		listaTrovati.remove(u);
            		break;
            	}
            }

            request.setAttribute("professionisti", listaTrovati);
            request.setAttribute("query", professioneCercata);

            request.getRequestDispatcher("/PagineWeb/RisultatiRicerca.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
           
            response.getWriter().println( e.getMessage());
        }
        }
    

		
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
