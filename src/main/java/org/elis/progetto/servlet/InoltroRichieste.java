package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.DisponibilitaDao;
import org.elis.dao.definition.OrarioBaseDao;
import org.elis.dao.definition.ProfessioneDao;
import org.elis.dao.definition.RecensioneDao;
import org.elis.dao.definition.RichiestaDao;
import org.elis.dao.definition.UtenteDao;
import org.elis.dao.definition.UtenteProfessioneDao;
import org.elis.dao.definition.UtenteVeicoloDao;
import org.elis.dao.definition.VeicoloDao;
import org.elis.progetto.model.Disponibilita;
import org.elis.progetto.model.OrarioBase;
import org.elis.progetto.model.Professione;
import org.elis.progetto.model.Utente;
import org.elis.progetto.model.UtenteProfessione;
import org.elis.progetto.model.UtenteVeicolo;
import org.elis.progetto.model.Veicolo;

/**
 * Servlet implementation class InoltroRichieste
 */
@WebServlet("/InoltroRichieste")
public class InoltroRichieste extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private RichiestaDao richiestaDao;
	    private UtenteDao utenteDao;
	    private ProfessioneDao professioneDao;
	    private VeicoloDao veicoloDao;
       private UtenteVeicoloDao utenteVeicoloDao;
       private UtenteProfessioneDao utenteProfessione;
       private DisponibilitaDao dispDao;
       private OrarioBaseDao orarioDao;
	    /**
	     * @see HttpServlet#HttpServlet()
	     */
	    
	        
	    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InoltroRichieste() {
        super();
        
        richiestaDao = DaoFactory.getInstance().getRichiestaDao();
        utenteDao = DaoFactory.getInstance().getUtenteDao();
        professioneDao = DaoFactory.getInstance().getProfessioneDao();
        veicoloDao = DaoFactory.getInstance().getVeicoloDao();
        utenteVeicoloDao = DaoFactory.getInstance().getUtenteVeicoloDao();    
utenteProfessione=DaoFactory.getInstance().getUtenteProfessioneDao();
dispDao=DaoFactory.getInstance().getDisponibilitaDao();
orarioDao=DaoFactory.getInstance().getOrarioBaseDao();
}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
    	Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");

    	if (utenteLoggato == null) {
    	    response.sendRedirect(request.getContextPath() + "/login.jsp");
    	    return;
    	}
		
		Long idProfessionsta=Long.parseLong(request.getParameter("id_Professionista"));
		Utente professionista = utenteDao.ricercaPerId(idProfessionsta);
		List<Veicolo> veicolo =veicoloDao.getVeicolibyUtente(idProfessionsta);
		List<Disponibilita> dispo = dispDao.getDisponibilitaPerUtente(idProfessionsta);
		List<OrarioBase> orario =orarioDao.getOrariByUtente(idProfessionsta);
		List<UtenteVeicolo> utenteVeicolo = utenteVeicoloDao.getDettagliVeicoliUtente(idProfessionsta);
        List<Professione> professioni = professioneDao.selectbyUtente(idProfessionsta);
	    List<UtenteProfessione> utenteProfessioni = utenteProfessione.selectByUtente(idProfessionsta);
		
		
		
	    request.setAttribute("professionista", professionista);
		request.setAttribute("utenteProf", utenteProfessioni);
		request.setAttribute("ListaProf", professioni);
		request.setAttribute("veicoli", veicolo);
		request.setAttribute("utenteVeicolo", utenteVeicolo);
		request.setAttribute("disponibilita", dispo);
		request.setAttribute("orario",orario);
		
		
		List<Disponibilita> dispProssimeDueSettimane = new ArrayList<Disponibilita>();
		LocalDate dataOggi= LocalDate.now();
		LocalDate oggiPiuDueSettimane =dataOggi.plusWeeks(2);
		Disponibilita eccezione  =null;
		while(dataOggi.isBefore(oggiPiuDueSettimane)) {
			LocalTime inizio=null;
			LocalTime fine = null;
			for(int i=0;i<dispo.size();i++) {
				if(dispo.get(i).equals(dataOggi)) {
					eccezione =dispo.get(i);
					break;}
				}	if(eccezione!=null) {
					inizio = eccezione.getInizio();
					fine=eccezione.getFine();
				}
				
				
				else {
				DayOfWeek giorno= dataOggi.getDayOfWeek();
				for(int i=0; i<orario.size();i++) {
					if(orario.get(i).getGiornoSettimana().equals(giorno)) {
						inizio=orario.get(i).getOraInizio();
						fine=orario.get(i).getOraFine();
						break;
						
					}
				}
				}
			}
			
			
			
		}
		
		
		
		
		
		
		request.getRequestDispatcher("/PagineWeb/RichiestaUtente.jsp").forward(request, response);

		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
