package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import javax.sql.DataSource;
import org.elis.progetto.model.Utente;
import org.elis.utilities.DataSourceConfig;
import org.elis.dao.mysql.MysqlUtenteDao;
import org.elis.dao.definition.UtenteDao;

import org.elis.dao.mysql.MysqlUtenteDao;

/**
 * Servlet implementation class RicercaProfessionistiServlet
 */
@WebServlet("/RicercaProfessionistiServlet")
public class RicercaProfessionistiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RicercaProfessionistiServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String professioneCercata = request.getParameter("professione");
        
  
        if (professioneCercata == null || professioneCercata.isEmpty()) {
            response.sendRedirect("PagineWeb/Homepage.jsp");
            return;
        }

       
        try {
            
            DataSource ds = DataSourceConfig.getDataSource();

           
            UtenteDao utenteDao = new MysqlUtenteDao(ds); 

         
            List<Utente> listaTrovati = utenteDao.ricercaTramiteProfessione(professioneCercata);

        
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
