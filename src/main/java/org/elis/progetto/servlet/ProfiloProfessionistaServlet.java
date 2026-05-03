package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;

import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.ImmagineDao;
import org.elis.dao.definition.ProfessioneDao;
import org.elis.dao.definition.RecensioneDao;
import org.elis.dao.definition.UtenteDao;
import org.elis.dao.definition.VeicoloDao;
import org.elis.dao.definition.UtenteProfessioneDao;
import org.elis.progetto.model.Immagine;
import org.elis.progetto.model.Professione;
import org.elis.progetto.model.Recensione;
import org.elis.progetto.model.Ruolo;
import org.elis.progetto.model.Utente;
import org.elis.progetto.model.UtenteProfessione;
import org.elis.progetto.model.Veicolo;

@WebServlet("/ProfiloProfessionistaServlet")
public class ProfiloProfessionistaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtenteDao utenteDao;
	private ProfessioneDao professioneDao;
	private ImmagineDao immagineDao;
	private RecensioneDao recensioneDao;
	private VeicoloDao veicoloDao;
	private UtenteProfessioneDao utenteProfessioneDao;

	@Override
	public void init() throws ServletException {
		utenteDao = DaoFactory.getInstance().getUtenteDao();
		professioneDao = DaoFactory.getInstance().getProfessioneDao();
		immagineDao = DaoFactory.getInstance().getImmagineDao();
		recensioneDao = DaoFactory.getInstance().getRecensioneDao();
		veicoloDao = DaoFactory.getInstance().getVeicoloDao();
		utenteProfessioneDao = DaoFactory.getInstance().getUtenteProfessioneDao();
	}

	public ProfiloProfessionistaServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idParam = request.getParameter("idProfessionista");
		long idProfessionista;
		if(idParam == null) {
			response.sendError(404, "Profilo non trovato");
			return;
		}
		try {
			idProfessionista = Long.parseLong(idParam);
		} catch (NumberFormatException e) {
			response.sendError(404, "Profilo non trovato");
			return;
		}
		
		
		
		
		try {
			Utente professionista = utenteDao.ricercaPerId(idProfessionista);
			
			if (professionista == null|| !professionista.getRuolo().equals(Ruolo.PROFESSIONISTA)) {
				response.sendError(404, "Profilo non trovato");
				return;
			}
			
			
			
			
			Double media = recensioneDao.selectAvgByUtente(idProfessionista);
			List<Professione> professioniUtente = professioneDao.selectbyUtente(idProfessionista);
			List<UtenteProfessione> utenteProf = utenteProfessioneDao.selectByUtenteandtariffa(idProfessionista);
			List<Immagine> immagini = immagineDao.selectByIdUtente(idProfessionista);
			List<Recensione> recensioni = recensioneDao.selectByIdUtenteRicevente(idProfessionista);
			List<Veicolo> veicoli = veicoloDao.getVeicolibyUtente(idProfessionista);
			
			List<Utente> listaRecensori = new ArrayList<>();
			Map<Utente, String> mappaRecensori = new HashMap<>();
			
			if (recensioni != null && !recensioni.isEmpty()) {
				List<Long> idRecensori = recensioni.stream()
						.map(r -> r.getUtenteScrittore().getId())
						.collect(Collectors.toList());
				Map<Long, String> mappaFotoProfilo = immagineDao.getMappaFotoProfilo(idRecensori);
				for (Recensione r : recensioni) {
					Utente autore = r.getUtenteScrittore();
					listaRecensori.add(autore);
					
					String foto = mappaFotoProfilo.get(autore.getId());
					if (foto == null) {
						foto = "img/default-avatar.png";
					}
					mappaRecensori.put(autore, foto);
				}
			}
			
			
			String proPic = "img/default-avatar.png";
			List<Immagine> galleria = new ArrayList<>();
			if (immagini != null) {
				for (Immagine img : immagini) {
					if (img.getIsFotoProfilo()) {
						proPic = img.getPercorso();
					} else {
						galleria.add(img);
					}
				}
			}
			
			request.setAttribute("media", media);
			request.setAttribute("professionista", professionista);
			request.setAttribute("profeUtente", professioniUtente);
			request.setAttribute("utenteProf", utenteProf);
			request.setAttribute("veicoli", veicoli);
			request.setAttribute("recensioni", recensioni);
			request.setAttribute("listaRecensori", listaRecensori);
			request.setAttribute("mappaRecensori", mappaRecensori);
			
			request.setAttribute("galleria", galleria);
			request.setAttribute("proPicProfilo", proPic);

			request.getRequestDispatcher("/WEB-INF/jsp/pubblico/profilo_professionista.jsp").forward(request, response);
		} catch (Exception e) {
		    e.printStackTrace();
		    response.sendRedirect(request.getContextPath() + "/HomepageServlet?error=db");
		}
	}
}