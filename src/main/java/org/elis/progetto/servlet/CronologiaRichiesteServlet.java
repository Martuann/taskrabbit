package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.ImmagineDao;
import org.elis.dao.definition.ProfessioneDao;
import org.elis.dao.definition.RecensioneDao;
import org.elis.dao.definition.RichiestaDao;
import org.elis.dao.definition.UtenteDao;
import org.elis.dao.definition.VeicoloDao;
import org.elis.progetto.model.Professione;
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
    private ImmagineDao immagineDao;   
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
        immagineDao=DaoFactory.getInstance().getImmagineDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utente utenteLoggato = (Utente) request.getSession().getAttribute("utenteLoggato");
	
		try {
			List<Richiesta> richieste = richiestaDao.selectByIdUtenteRichiedente(utenteLoggato.getId());
			List<Utente> utentiRichiesti=new ArrayList<Utente>();
		
				
				utentiRichiesti=(utenteDao.listaUtentiRichiestiDaRichiedente(utenteLoggato.getId())); 
				
				Map<Long, Utente> mappaProfessionisti = utentiRichiesti.stream()
				        .collect(Collectors.toMap(Utente::getId, u -> u, (u1, u2) -> u1));

				List<Professione> tutteLeProfessioni = professioneDao.selectAll();
	            Map<Long, String> mappaTask = tutteLeProfessioni.stream()
	                .collect(Collectors.toMap(Professione::getId, Professione::getNome));
	            List<Recensione> mieRecensioni = recensioneDao.selectByIdUtenteScrittore(utenteLoggato.getId());				
	            Set<Long> idProfessionistiRecensiti = mieRecensioni.stream()
	            	    .map(Recensione::getId_utenteRicevente)
	            	    .collect(Collectors.toSet());
	            List<Long> idsProfessionisti = utentiRichiesti.stream()
	            	    .map(Utente::getId)
	            	    .collect(Collectors.toList());

	            	Map<Long, String> mappaFoto = immagineDao.getMappaFotoProfilo(idsProfessionisti);
	          
	        request.setAttribute("mappaFoto", mappaFoto);
	        request.setAttribute("giaRecensiti", idProfessionistiRecensiti);
	        request.setAttribute("mappaProfessionisti", mappaProfessionisti);
			request.setAttribute("mappaTask", mappaTask);
			request.setAttribute("richieste", richieste);
			
			
			
			request.getRequestDispatcher("/WEB-INF/jsp/utente/cronologiaRichieste.jsp").forward(request, response);	

		}
		catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Impossibile caricare la cronologia.");
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	

	
}
