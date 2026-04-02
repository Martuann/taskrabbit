package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.elis.dao.definition.CittaDao;
import org.elis.dao.definition.DaoFactory;
import org.elis.progetto.model.Citta;

/**
 *
 */
@WebServlet("/AggiungiCittaServlet")
public class AggiungiCittaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CittaDao cittaDao;

    @Override
    public void init() throws ServletException {
        
        cittaDao = DaoFactory.getInstance().getCittaDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        request.getRequestDispatcher("/WEB-INF/jsp/admin/aggiungiCitta.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomeCitta = request.getParameter("nomeCitta");
        String provincia = request.getParameter("provincia");
       
        	if (nomeCitta != null && !nomeCitta.trim().isEmpty() && provincia != null && !provincia.trim().isEmpty()) {
                try {
                    
                    if (nomeCitta.length() > 30) {
                        request.setAttribute("messaggio", "Errore: nome città troppo lungo.");
                    } else if (provincia.trim().length() != 2) {
                        request.setAttribute("messaggio", "Errore: la provincia deve essere di esattamente 2 caratteri.");
                    } else {
                        Citta nuovaCitta = new Citta();
                        nuovaCitta.setNome(nomeCitta.trim());
                        nuovaCitta.setProvincia(provincia.trim().toUpperCase()); 
                        
                        cittaDao.aggiungiCitta(nuovaCitta);
                        
                        request.setAttribute("messaggio", "Città aggiunta con successo!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("messaggio", "Errore nel salvataggio della città.");
                }
            } else {
                request.setAttribute("messaggio", "Errore: compila tutti i campi richiesti.");
            }

    
 
        
        request.getRequestDispatcher("/WEB-INF/jsp/admin/aggiungiCitta.jsp").forward(request, response);

    }}

	


		