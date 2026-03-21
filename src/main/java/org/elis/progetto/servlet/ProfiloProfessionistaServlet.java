package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idParam = request.getParameter("id1");
		long idProfessionista = (idParam != null) ? Long.parseLong(idParam) : 2L;

		try {
			Utente professionista = utenteDao.ricercaPerId(idProfessionista);
			List<Professione> professioniUtente = professioneDao.selectbyUtente(idProfessionista);
			List<UtenteProfessione> utenteProf = utenteProfessioneDao.selectByUtente(idProfessionista);
			List<Immagine> immagini = immagineDao.selectByIdUtente(idProfessionista);
			List<Recensione> recensioni = recensioneDao.selectByIdUtenteRicevente(idProfessionista);
			Map<Utente, String> recensori = utenteDao.getRecensoriConFoto(idProfessionista);
			List<Veicolo> veicoli = veicoloDao.getVeicolibyUtente(idProfessionista);

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

			request.setAttribute("professionista", professionista);
			request.setAttribute("profeUtente", professioniUtente);
			request.setAttribute("utenteProf", utenteProf);
			request.setAttribute("veicoli", veicoli);
			request.setAttribute("recensioni", recensioni);
			request.setAttribute("mappaRecensori", recensori);
			request.setAttribute("listaRecensori", new ArrayList<>(recensori.keySet()));
			request.setAttribute("galleria", galleria);
			request.setAttribute("proPicProfilo", proPic);

			request.getRequestDispatcher("/WEB-INF/jsp/pubblico/profilo_professionista.jsp").forward(request, response);;
			} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}