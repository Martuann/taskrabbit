package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.ProfessioneDao;
import org.elis.dao.definition.RichiestaDao;
import org.elis.dao.definition.UtenteDao;
import org.elis.dao.definition.VeicoloDao;
import org.elis.progetto.model.Richiesta;
import org.elis.progetto.model.StatoRichiesta;
import org.elis.progetto.model.Utente;

/**
 * Servlet implementation class GestioneRichiesteServlet
 */
@WebServlet("/GestioneRichiesteServlet")
public class GestioneRichiesteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RichiestaDao richiestaDao;
	private UtenteDao utenteDao;
	private VeicoloDao veicoloDao;
	private ProfessioneDao professioneDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneRichiesteServlet() {
        super();
        richiestaDao = DaoFactory.getInstance().getRichiestaDao();
    	utenteDao = DaoFactory.getInstance().getUtenteDao();
    	veicoloDao = DaoFactory.getInstance().getVeicoloDao();
    	professioneDao = DaoFactory.getInstance().getProfessioneDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long idProfessionista = Long.parseLong(request.getParameter("id"));
		try {
			Utente utenteLoggato = utenteDao.ricercaPerId(idProfessionista);
			request.setAttribute("utenteLoggato", utenteLoggato);
			
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
				
				switch(r.getStato()) {
				case in_attesa:
					request.setAttribute("statoRichiesta"+counter, "In attesa di conferma");
					request.setAttribute("coloreStato"+counter, "#E4A11B");
					break;
				case in_corso:
					request.setAttribute("statoRichiesta"+counter, "In corso");
					request.setAttribute("coloreStato"+counter, "#E4A11B");
					break;
				case completato:
					request.setAttribute("statoRichiesta"+counter, "Completato");
					request.setAttribute("coloreStato"+counter, "#14A44D");
					break;
				case rifiutato:
					request.setAttribute("statoRichiesta"+counter, "Rifiutato");
					request.setAttribute("coloreStato"+counter, "#DC4C64");
				}
				
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
	
	private boolean hasRichiesteInAttesa(List<Richiesta> richieste) {
		for(Richiesta r : richieste) {
			if(r.getStato()==StatoRichiesta.in_attesa) {
				return true;
			}
		}
		return false;
	}
}
