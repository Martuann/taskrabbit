package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.ProfessioneDao;
import org.elis.dao.definition.RecensioneDao;
import org.elis.dao.definition.RichiestaDao;
import org.elis.dao.definition.UtenteDao;
import org.elis.dao.definition.VeicoloDao;
import org.elis.progetto.model.Recensione;
import org.elis.progetto.model.Richiesta;
import org.elis.progetto.model.StatoRichiesta;
import org.elis.progetto.model.Utente;

/**
 * Servlet implementation class cronologiaRichieste
 */
@WebServlet("/CronologiaRichiesteServlet")
public class CronologiaRichiesteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private RichiestaDao richiestaDao;
    private UtenteDao utenteDao;
    private ProfessioneDao professioneDao;
    private VeicoloDao veicoloDao;
    private RecensioneDao recensioneDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CronologiaRichiesteServlet() {
        super();
        richiestaDao = DaoFactory.getInstance().getRichiestaDao();
        utenteDao = DaoFactory.getInstance().getUtenteDao();
        professioneDao = DaoFactory.getInstance().getProfessioneDao();
        veicoloDao = DaoFactory.getInstance().getVeicoloDao();
        recensioneDao = DaoFactory.getInstance().getRecensioneDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long idUtente = Long.parseLong(request.getParameter("id"));
		try {
			List<Richiesta> richieste = richiestaDao.selectByIdUtenteRichiedente(idUtente);
			if(!richieste.isEmpty()) {
				request.setAttribute("emptyMessage1", "none");
			} 
			request.setAttribute("richieste", richieste);
			
			int counter=0;
			for(Richiesta r : richieste) {
				Utente richiedente = utenteDao.ricercaPerId(r.getIdUtenteRichiesto());
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
				
				if(r.getStato()!=StatoRichiesta.completato || hasRecensioneAlready(r)) {
					request.setAttribute("recensioneFlag"+counter, "none");
				}
				request.setAttribute("idProfessionista"+counter, r.getIdUtenteRichiesto());
				
				counter++;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
	    }
		
		request.getRequestDispatcher("/PagineWeb/cronologiaRichieste.jsp?id="+idUtente).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private boolean hasRecensioneAlready(Richiesta richiesta) {
		for(Recensione rec : recensioneDao.selectAll()) {
			if(rec.getId_utenteScrittore()==richiesta.getIdUtenteRichiedente() &&
			   rec.getId_utenteRicevente()==richiesta.getIdUtenteRichiesto()) {
				return true;
			}
		}
		return false;
	}
}
