package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;

import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.ImmagineDao;
import org.elis.progetto.model.Immagine;
import org.elis.progetto.model.Utente;

/**
 * Servlet implementation class EliminaFotoServlet
 */
@WebServlet("/EliminaFoto")
public class EliminaFotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ImmagineDao immagineDao;

    @Override
    public void init() throws ServletException {
        immagineDao = DaoFactory.getInstance().getImmagineDao();
    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminaFotoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
HttpSession session = request.getSession(false);
        
    

        Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");
        String idParam = request.getParameter("id");

        if (idParam != null) {
            try {
                long idImmagine = Long.parseLong(idParam);
                Immagine img = immagineDao.selectById(idImmagine);

                if (img != null && img.getUtente().getId() == utenteLoggato.getId()) {
                    
                    String rootPath = getServletContext().getRealPath("/");
                    File fileDaEliminare = new File(rootPath, img.getPercorso()).getCanonicalFile();

                    if (fileDaEliminare.exists()) {
                        fileDaEliminare.delete();
                    }

                    immagineDao.delete(idImmagine);
                    
                    response.sendRedirect(request.getContextPath() + "/ProfiloProfessionistaServlet?idProfessionista=" + utenteLoggato.getId() + "&deleted=true");
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
				response.sendError(500, "Errore nel caricamento dei dati del profilo.");
            }
        }
        
        response.sendRedirect(request.getContextPath() + "/ProfiloProfessionistaServlet?idProfessionista=" + utenteLoggato.getId() + "&error=delete_failed");
    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
