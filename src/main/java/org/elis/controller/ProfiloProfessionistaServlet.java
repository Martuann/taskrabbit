package org.elis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.elis.dao.definition.ImmagineDAO;
import org.elis.dao.definition.RecensioneDAO;
import org.elis.dao.definition.UtenteDao;
import org.elis.dao.definition.UtenteVeicoloDao;
import org.elis.dao.definition.VeicoloDao;
import org.elis.dao.definition.UtenteProfessioneDAO;
import org.elis.dao.definition.professioniDao;
import org.elis.dao.mysql.MySqlVeicoloDao;
import org.elis.dao.mysql.MysqlImmagineDao;
import org.elis.dao.mysql.MysqlProfessioneDao;
import org.elis.dao.mysql.MysqlUtenteDAO;
import org.elis.dao.mysql.MysqlUtenteProfessioneDao;
import org.elis.dao.mysql.MysqlUtenteVeicoloDao;
import org.elis.dao.mysql.mysqlRecensioneDAO;
import org.elis.progetto.model.Immagine;
import org.elis.progetto.model.Professione;
import org.elis.progetto.model.Recensione;
import org.elis.progetto.model.Utente;
import org.elis.progetto.model.UtenteVeicolo;
import org.elis.utilities.DataSourceConfig;

/**
 * Servlet implementation class ProfiloProfessionistaServlet
 */
@WebServlet("/ProfiloProfessionistaServlet")
public class ProfiloProfessionistaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		Long idProfessione = Long.parseLong(request.getParameter("id2"));
		DataSource ds = DataSourceConfig.getDataSource();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskrabbit", "root", "root")){
			UtenteDao utenteDao = new MysqlUtenteDAO(ds);
			professioniDao professioniDao = new MysqlProfessioneDao(connection);
			ImmagineDAO immagineDao = new MysqlImmagineDao(connection);
			RecensioneDAO recensioneDao = new mysqlRecensioneDAO(connection);
			UtenteVeicoloDao utenteVeicoloDao = new MysqlUtenteVeicoloDao(ds);
			VeicoloDao veicoloDao = new MySqlVeicoloDao(ds);
			UtenteProfessioneDAO utenteProfessioneDao = new MysqlUtenteProfessioneDao(connection);
			Utente professionista = utenteDao.ricercaPerId(idProfessionista);
			Professione professione = professioniDao.selectById(idProfessione);
			List<Immagine> immagini = immagineDao.selectByIdUtente(idProfessionista);
			List<Recensione> recensioni = recensioneDao.selectByIdUtenteRicevente(idProfessionista);
			List<Utente> recensori = new ArrayList<>();
			for(Recensione r : recensioni) {
				recensori.add(utenteDao.ricercaPerId(r.getId_utenteScrittore()));
			}
			List<String> veicoli = new ArrayList<>();
			for(UtenteVeicolo uv : utenteVeicoloDao.selectByUtente(idProfessionista)) {
				veicoli.add(veicoloDao.ricercaPerId(uv.getIdVeicolo()).getCategoria());
			}
			request.setAttribute("nomecognome", professionista.getNome()+" "+professionista.getCognome());
			request.setAttribute("veicoli", veicoli);
			request.setAttribute("tariffa", utenteProfessioneDao
				   .selectByIdUtenteIdProfessione(idProfessionista, idProfessione).getTariffaH());
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
			request.setAttribute("task", professione.getNome());
			request.setAttribute("recensioni", recensioni);
			request.setAttribute("recensori", recensori);
			for(int i=0; i<recensioni.size(); i++) {
				request.setAttribute("propic"+i, ((Immagine) immagineDao.selectByIdUtente(recensori.get(i).getId())).getPercorso());
				request.setAttribute("nomecognome"+i, recensori.get(i).getNome());
				request.setAttribute("rating"+i, recensioni.get(i).getVoto());
				request.setAttribute("task"+i, professione.getNome());
				request.setAttribute("data"+i, recensioni.get(i).getData().toString());
				request.setAttribute("descrizione"+i, recensioni.get(i).getDescrizione());
			}
			request.getRequestDispatcher("/PagineWeb/profilo_professionista.jsp?id1="+idProfessionista+"&id2="+idProfessione)
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
