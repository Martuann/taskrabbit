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
import org.elis.progetto.model.Veicolo;
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
	private static int counter;
       
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
			List<Richiesta> richieste = richiestaDao.selectByIdUtenteRichiesto(idProfessionista);
			List<Richiesta> richiesteAccettate = new ArrayList<>();
			int size = richieste.size();
			for(int i=0;i<size;i++) {
				if(richieste.get(i).getStato()!=StatoRichiesta.in_attesa) {
					richiesteAccettate.add(richieste.get(i));
					richieste.remove(i);
				}
			}
			request.setAttribute("richieste", richieste);
			request.setAttribute("richiesteAccettate", richiesteAccettate);
			
			counter=0;
			
			aggiornaRichieste(richieste, request);

			aggiornaRichieste(richiesteAccettate, request);
		}
		catch (Exception e) {
			e.printStackTrace();
	    }
		request.getRequestDispatcher("/PagineWeb/gestioneRichieste.jsp").forward(request, response);
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
	
	private void aggiornaRichieste(List<Richiesta> richieste, HttpServletRequest request) throws Exception {
		for(Richiesta r : richieste) {
			Utente richiedente = utenteDao.ricercaPerId(r.getIdUtenteRichiedente());
			request.setAttribute("nomeutente"+counter,richiedente.getNome()+" "+richiedente.getCognome());
			
			request.setAttribute("statoRichiesta"+counter, nomeStatoRichiesta(r.getStato()));
			
			String professione = professioneDao.selectById(r.getIdProfessione()).getNome();
			request.setAttribute("task"+counter,professione);
			
			request.setAttribute("data"+counter, r.getData().toString());
			
			request.setAttribute("orario"+counter, r.getOrarioInizio().toString()+" - "+r.getOrarioFine().toString());
			
			Veicolo veicolo = veicoloDao.ricercaPerId(r.getIdVeicolo());
			if(veicolo==null) {
				request.setAttribute("veicolo"+counter, "nessuno");
			}
			else {
				request.setAttribute("veicolo"+counter, veicolo.getCategoria());
			}
			
			request.setAttribute("indirizzo"+counter, r.getIndirizzo());

			request.setAttribute("costoeffettivo"+counter, r.getCostoEffettivo());
			
			counter++;
		}
	}
}
