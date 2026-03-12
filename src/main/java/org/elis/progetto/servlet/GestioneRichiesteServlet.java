package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.elis.dao.definition.ProfessioneDao;
import org.elis.dao.definition.RichiestaDao;
import org.elis.dao.definition.UtenteDao;
import org.elis.dao.definition.VeicoloDao;
import org.elis.dao.mysql.MySqlVeicoloDao;
import org.elis.dao.mysql.MysqlProfessioneDao;
import org.elis.dao.mysql.MysqlRichiestaDao;
import org.elis.dao.mysql.MysqlUtenteDao;
import org.elis.progetto.model.Richiesta;
import org.elis.progetto.model.StatoRichiesta;
import org.elis.progetto.model.Utente;
import org.elis.utilities.DataSourceConfig;

/**
 * Servlet implementation class GestioneRichiesteServlet
 */
@WebServlet("/GestioneRichiesteServlet")
public class GestioneRichiesteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RichiestaDao richiestaDao = new MysqlRichiestaDao(DataSourceConfig.getDataSource());
	private UtenteDao utenteDao = new MysqlUtenteDao(DataSourceConfig.getDataSource());
	private ProfessioneDao professioneDao = new MysqlProfessioneDao(DataSourceConfig.getDataSource());
	private VeicoloDao veicoloDao = new MySqlVeicoloDao(DataSourceConfig.getDataSource());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneRichiesteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long idProfessionista = Long.parseLong(request.getParameter("id"));
		try {
			List<Richiesta> query = richiestaDao.selectByIdUtenteRichiesto(idProfessionista);
			List<Richiesta> richieste = new ArrayList<>();
			int counter=0;
			for(Richiesta r : query) {
				if(r.getStato()!=StatoRichiesta.in_attesa) {
					richieste.add(r);
				}
				else {
					richieste.add(counter++,r);
				}
			}
			request.setAttribute("richieste", richieste);
			counter=0;
			for(Richiesta r : richieste) {
				Utente richiedente = utenteDao.ricercaPerId(r.getIdUtenteRichiedente());
				request.setAttribute("nomeutente"+counter,richiedente.getNome()+" "+richiedente.getCognome());
				
				request.setAttribute("statoRichiesta"+counter, nomeStatoRichiesta(r.getStato()));
				
				String professione = professioneDao.selectById(r.getIdProfessione()).getNome();
				request.setAttribute("task"+counter,professione);
				
				request.setAttribute("data"+counter, r.getData().toString());
				
				request.setAttribute("orario"+counter, r.getOrarioInizio().toString()+" - "+r.getOrarioFine().toString());
				
				if(r.getIdVeicolo()==null) {
					request.setAttribute("veicolo"+counter, "nessuno");
				}
				else {
					String veicolo = veicoloDao.ricercaPerId(r.getIdVeicolo()).getCategoria();
					request.setAttribute("veicolo"+counter, veicolo);
				}
				
				request.setAttribute("indirizzo"+counter, r.getIndirizzo());

				request.setAttribute("costoeffettivo"+counter, r.getCostoEffettivo());
				
				request.setAttribute("idRichiesta"+counter, r.getId());
				
				counter++;
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
	    }
		request.getRequestDispatcher("/PagineWeb/gestioneRichieste.jsp")
			   .forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private String nomeStatoRichiesta(StatoRichiesta stato) {
		switch(stato) {
		case in_attesa: return "in attesa di conferma";
		case in_corso: return "in corso";
		case completato: return "completato";
		}
		return null;
	}
}
