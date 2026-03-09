package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.elis.dao.mysql.MySqlVeicoloDao;
import org.elis.dao.mysql.MysqlProfessioneDao;
import org.elis.dao.mysql.MysqlUtenteProfessioneDao;
import org.elis.dao.mysql.MysqlUtenteVeicoloDao;
import org.elis.progetto.model.Professione;
import org.elis.progetto.model.Ruolo;
import org.elis.progetto.model.Utente;
import org.elis.progetto.model.UtenteProfessione;
import org.elis.progetto.model.UtenteVeicolo;
import org.elis.progetto.model.Veicolo;
import org.elis.utilities.DataSourceConfig;

@WebServlet("/GestioneServiziServlet")
public class GestioneServiziServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	//UTENTE DI PROVA RICORDARSI DI CANCELLARLO
    	Utente utenteTestDiProva = new Utente();
        utenteTestDiProva.setId(1L);
        utenteTestDiProva.setNome("Mario");
        utenteTestDiProva.setCognome("Rossi");
        request.getSession().setAttribute("utenteLoggato", utenteTestDiProva);
        utenteTestDiProva.setRuolo(Ruolo.PROFESSIONISTA);

    	
    	
    	
    	
    	
    	
    	
    	HttpSession session = request.getSession();
    	Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");

    	if (utenteLoggato == null) {
    	    response.sendRedirect(request.getContextPath() + "/login.jsp"); 
    	    return;
    	}
    	if (utenteLoggato.getRuolo()!=Ruolo.PROFESSIONISTA) {
    	    response.sendRedirect(request.getContextPath() + "/index.jsp"); 
    	    return;
    	}
    	
        try {
            MysqlProfessioneDao professioneDao = new MysqlProfessioneDao(DataSourceConfig.getDataSource());
            MySqlVeicoloDao veicoloDao = new MySqlVeicoloDao(DataSourceConfig.getDataSource());
            MysqlUtenteProfessioneDao utenteProfessioneDao = new MysqlUtenteProfessioneDao(DataSourceConfig.getDataSource());
            MysqlUtenteVeicoloDao utenteVeicoloDao = new MysqlUtenteVeicoloDao(DataSourceConfig.getDataSource());

            List<Professione> catalogoTutteLeProfessioni = professioneDao.selectAll();
            List<Veicolo> catalogoTuttiIVeicoli = veicoloDao.getAllVeicoli();
            List<UtenteProfessione> professioniPosseduteDallUtente = utenteProfessioneDao.selectByUtente(utenteTestDiProva.getId());
            List<UtenteVeicolo> veicoliPossedutiDallUtente = utenteVeicoloDao.selectByUtente(utenteTestDiProva.getId());

            request.setAttribute("catalogoProfessioni", catalogoTutteLeProfessioni);
            request.setAttribute("professioniUtente", professioniPosseduteDallUtente);
            request.setAttribute("catalogoVeicoli", catalogoTuttiIVeicoli);
            request.setAttribute("veicoliUtente", veicoliPossedutiDallUtente);

            request.getRequestDispatcher("/PagineWeb/gestioneServizi.jsp").forward(request, response);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            response.sendError(500, "Errore nel caricamento dati: " + exception.getMessage());
        }
    	

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");

    	if (utenteLoggato == null) {
    	    response.sendRedirect(request.getContextPath() + "/login.jsp"); 
    	    return;
    	}
    	if (utenteLoggato.getRuolo()!=Ruolo.PROFESSIONISTA) {
    	    response.sendRedirect(request.getContextPath() + "/index.jsp"); 
    	    return;
    	}
    	

       String operazioneRichiesta = request.getParameter("azione");
        Utente utenteLoggatoInSessione = (Utente) request.getSession().getAttribute("utenteLoggato");

  
        
        String urlRedirect = request.getContextPath() + "/GestioneServiziServlet";

        try {
            MysqlUtenteProfessioneDao utenteProfessioneDao = new MysqlUtenteProfessioneDao(DataSourceConfig.getDataSource());
            MysqlUtenteVeicoloDao utenteVeicoloDao = new MysqlUtenteVeicoloDao(DataSourceConfig.getDataSource());

            if ("aggiungi_professione".equals(operazioneRichiesta)) {
                Long idProfessione = Long.parseLong(request.getParameter("idProfessioneScelta"));
                BigDecimal tariffaOraria = validaEConvertiPrezzo(request.getParameter("tariffaOraria"));
                
                utenteProfessioneDao.insert(new UtenteProfessione(utenteLoggatoInSessione.getId(), idProfessione, tariffaOraria));

            } else if ("rimuovi_professione".equals(operazioneRichiesta)) {
                long idDaRimuovere = Long.parseLong(request.getParameter("idProfessioneDaRimuovere"));
                utenteProfessioneDao.delete(idDaRimuovere);

            } else if ("modifica_tariffa".equals(operazioneRichiesta)) {
                long idAssociazione = Long.parseLong(request.getParameter("idAssociazione"));
                long idProfessione = Long.parseLong(request.getParameter("idProfessione"));
                BigDecimal nuovaTariffa = validaEConvertiPrezzo(request.getParameter("nuovaTariffa"));
                
                UtenteProfessione upAggiornato = new UtenteProfessione();
                upAggiornato.setId(idAssociazione);
                upAggiornato.setIdUtente(utenteLoggatoInSessione.getId());
                upAggiornato.setIdProfessione(idProfessione);
                upAggiornato.setTariffaH(nuovaTariffa);
                
                utenteProfessioneDao.update(upAggiornato);

            } else if ("aggiungi_veicolo".equals(operazioneRichiesta)) {
                Long idVeicolo = Long.parseLong(request.getParameter("idVeicoloScelto"));
                BigDecimal sovrapprezzo = validaEConvertiPrezzo(request.getParameter("prezzoAggiuntivo"));
                
                utenteVeicoloDao.associaVeicolo(new UtenteVeicolo(utenteLoggatoInSessione.getId(), idVeicolo, sovrapprezzo));

            } else if ("rimuovi_veicolo".equals(operazioneRichiesta)) {
                long idAssociazioneVeicolo = Long.parseLong(request.getParameter("idVeicoloDaRimuovere"));
                utenteVeicoloDao.rimuoviAssociazione(utenteLoggatoInSessione.getId(), idAssociazioneVeicolo);

            } else if ("modifica_sovrapprezzo".equals(operazioneRichiesta)) {
                long idVeicolo = Long.parseLong(request.getParameter("idVeicolo"));
                BigDecimal nuovoPrezzo = validaEConvertiPrezzo(request.getParameter("nuovoPrezzo"));
                utenteVeicoloDao.aggiornaPrezzoServizio(utenteLoggatoInSessione.getId(), idVeicolo, nuovoPrezzo);
            }

        } 
        catch (NumberFormatException e) {
            e.printStackTrace();
            urlRedirect += "?errore=input";
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            urlRedirect += "?errore=input";}
        catch (Exception exception) {
            exception.printStackTrace();
            urlRedirect += "?errore=1";
        }

        response.sendRedirect(urlRedirect);
    }

    








private BigDecimal validaEConvertiPrezzo(String prezzoStr) throws IllegalArgumentException {
 if (prezzoStr == null || prezzoStr.trim().isEmpty()) {
     throw new IllegalArgumentException("Il prezzo non può essere vuoto");
 }
 
 prezzoStr = prezzoStr.replace(",", ".");
 
 BigDecimal prezzo;
 try {
     prezzo = new BigDecimal(prezzoStr);
 } catch (NumberFormatException e) {
     throw new IllegalArgumentException("Formato numero non valido");
 }
 
 if (prezzo.compareTo(BigDecimal.ZERO) < 0) {
     throw new IllegalArgumentException("Il prezzo non può essere negativo");
 }
 
 if (prezzo.compareTo(new BigDecimal("999999.99")) > 0) {
     throw new IllegalArgumentException("Il prezzo inserito è troppo alto");
 }
 
 return prezzo;
}
}