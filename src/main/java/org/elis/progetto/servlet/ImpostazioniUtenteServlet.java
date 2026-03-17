package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.elis.progetto.model.Utente;
import org.elis.utilities.DataSourceConfig;

@WebServlet("/Impostazioni")
public class ImpostazioniUtenteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("utenteLoggato") == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }
        request.getRequestDispatcher("/PagineWeb/impostazioni.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utenteLoggato");
        
        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        String azione = request.getParameter("azione");

        if ("cambiaPassword".equals(azione)) {
            String vecchiaPass = request.getParameter("vecchiaPassword");
            String nuovaPass = request.getParameter("nuovaPassword");

            if (utente.getPassword().equals(vecchiaPass)) {
                String query = "UPDATE utente SET password = ? WHERE id = ?";
                try (Connection conn = DataSourceConfig.getConnection();
                     PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setString(1, nuovaPass);
                    ps.setLong(2, utente.getId());
                    if (ps.executeUpdate() > 0) {
                        utente.setPassword(nuovaPass);
                        session.setAttribute("utenteLoggato", utente);
                        response.sendRedirect(request.getContextPath() + "/Impostazioni?success=true");
                    }
                } catch (Exception e) { e.printStackTrace(); }
            } else {
                response.sendRedirect(request.getContextPath() + "/Impostazioni?errorPass=true");
            }
        } else if ("eliminaAccount".equals(azione)) {
            String query = "DELETE FROM utente WHERE id = ?";
            try (Connection conn = DataSourceConfig.getConnection();
                 PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setLong(1, utente.getId());
                if (ps.executeUpdate() > 0) {
                    session.invalidate();
                    response.sendRedirect(request.getContextPath() + "/Login?accountEliminato=true");
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
    }
}