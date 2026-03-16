package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.ImmagineDao;
import org.elis.dao.definition.ProfessioneDao;
import org.elis.dao.definition.RecensioneDao;
import org.elis.dao.definition.UtenteDao;
import org.elis.dao.definition.UtenteVeicoloDao;
import org.elis.dao.definition.VeicoloDao;
import org.elis.dao.definition.UtenteProfessioneDao;

import org.elis.progetto.model.Immagine;
import org.elis.progetto.model.Professione;
import org.elis.progetto.model.Recensione;
import org.elis.progetto.model.Utente;
import org.elis.progetto.model.UtenteProfessione;
import org.elis.progetto.model.UtenteVeicolo;
import org.elis.progetto.model.Veicolo;



/**
 * Servlet implementation class ProfiloProfessionistaServlet
 */
@WebServlet("/ProfiloProfessionistaServlet")
public class ProfiloProfessionistaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtenteDao utenteDao;
	private ProfessioneDao professioneDao;
	private ImmagineDao immagineDao;
	private RecensioneDao recensioneDao;
	private UtenteVeicoloDao utenteVeicoloDao;
	private VeicoloDao veicoloDao;
	private UtenteProfessioneDao utenteProfessioneDao;

	
	
	@Override
	public void init() throws ServletException {
	utenteDao=DaoFactory.getInstance().getUtenteDao();
	professioneDao=DaoFactory.getInstance().getProfessioneDao();
	immagineDao=DaoFactory.getInstance().getImmagineDao();
	recensioneDao=DaoFactory.getInstance().getRecensioneDao();
	utenteVeicoloDao=DaoFactory.getInstance().getUtenteVeicoloDao();
	veicoloDao=DaoFactory.getInstance().getVeicoloDao();
	utenteProfessioneDao=DaoFactory.getInstance().getUtenteProfessioneDao();

	} 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfiloProfessionistaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long idProfessionista = Long.parseLong(request.getParameter("id1"));
		idProfessionista = 2L;
		try{
			
			Utente professionista = utenteDao.ricercaPerId(idProfessionista);
	     	List<Professione> professioniUtente = professioneDao.selectbyUtente(idProfessionista);
			List<UtenteProfessione>utenteProf= utenteProfessioneDao.selectByUtente(idProfessionista);
			List<Immagine> immagini = immagineDao.selectByIdUtente(idProfessionista);
			List<Recensione> recensioni = recensioneDao.selectByIdUtenteRicevente(idProfessionista);
			List<Utente> recensori = utenteDao.getUtentiRecensori(idProfessionista);
			List<Veicolo> veicoli = veicoloDao.getVeicolibyUtente(idProfessionista);
			request.setAttribute("professionista", professionista);
			request.setAttribute("veicoli", veicoli);
			request.setAttribute("tariffe",utenteProf);
			int counter = 0;
			for(Immagine i : immagini) {
				counter++;
				if(i.getIsFotoProfilo()) {
					request.setAttribute("propicprofilo", i.getPercorso());
					break;
				}
				else {
					request.setAttribute("img"+counter, i.getPercorso());
				}
			}
			request.setAttribute("task", professioniUtente);
			request.setAttribute("recensioni", recensioni);
			request.setAttribute("recensori", recensori);
			for(int i=0; i<recensioni.size(); i++) {
				for(Immagine img : immagineDao.selectByIdUtente(recensori.get(i).getId())) {
					if(img.getIsFotoProfilo()) {
						request.setAttribute("propic"+i, img.getPercorso());
						break;
					}
				}
				request.setAttribute("nomeutente"+i, recensori.get(i).getNome()+" "+recensori.get(i).getCognome());
				request.setAttribute("rating"+i, recensioni.get(i).getVoto());
				request.setAttribute("data"+i, recensioni.get(i).getData().toString());
				request.setAttribute("descrizione"+i, recensioni.get(i).getDescrizione());
			}
			request.getRequestDispatcher("/PagineWeb/profilo_professionista.jsp")
				   .forward(request, response);
		}
		catch(Exception e) {
			e.printStackTrace();
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
