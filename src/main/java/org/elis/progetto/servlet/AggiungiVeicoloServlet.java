package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.VeicoloDao;
import org.elis.progetto.model.Veicolo;

@WebServlet("/AggiungiVeicolo")
public class AggiungiVeicoloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VeicoloDao veicoloDao;

    @Override
    public void init() throws ServletException {
       
        veicoloDao = DaoFactory.getInstance().getVeicoloDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        request.getRequestDispatcher("/WEB-INF/jsp/admin/aggiungiVeicolo.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoria = request.getParameter("tipoVeicolo");

        if (categoria != null && !categoria.trim().isEmpty()) {
            try {
                if (categoria.length() > 30) {
                    request.setAttribute("messaggio", "Errore: nome troppo lungo.");
                } else {
                    Veicolo nuovoVeicolo = new Veicolo();
                    nuovoVeicolo.setCategoria(categoria.trim());
                    veicoloDao.aggiungiVeicolo(nuovoVeicolo);
                    request.setAttribute("messaggio", "Veicolo aggiunto con successo!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("messaggio", "Errore nel salvataggio del veicolo.");
            }
        } else {
            request.setAttribute("messaggio", "Inserisci una categoria valida.");
        }
        request.getRequestDispatcher("/WEB-INF/jsp/admin/aggiungiVeicolo.jsp").forward(request, response);
    }
}
