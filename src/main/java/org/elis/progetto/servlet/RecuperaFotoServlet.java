package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.ImmagineDao;
import org.elis.progetto.model.Immagine;
import org.elis.progetto.model.Utente;

/**
 * Servlet implementation class RecuperaFotoProfiloServlet
 */
@WebServlet("/recuperaFoto")
public class RecuperaFotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private ImmagineDao immagineDao;
	    @Override
	    public void init() throws ServletException {
	    	immagineDao=DaoFactory.getInstance().getImmagineDao();
	    } 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecuperaFotoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Immagine img = null;
	    String idImmagineParam = request.getParameter("id"); 
	    String idUtenteParam = request.getParameter("idUtenteRichiesto"); 

	    try {
	        if (idImmagineParam != null && !idImmagineParam.isEmpty()) {
	            long idImmagine = Long.parseLong(idImmagineParam);
	            img = immagineDao.selectById(idImmagine); 
	        } else if (idUtenteParam != null && !idUtenteParam.isEmpty()) {
	            long idUtente = Long.parseLong(idUtenteParam);
	            img = immagineDao.selectFotoProfiloByUtente(idUtente);
	        } else {
	            HttpSession session = request.getSession(false);
	            if (session != null && session.getAttribute("utenteLoggato") != null) {
	                Utente utente = (Utente) session.getAttribute("utenteLoggato");
	                img = immagineDao.selectFotoProfiloByUtente(utente.getId());
	            }
	        }

	        if (img != null && img.getPercorso() != null) {
	            String rootPath = getServletContext().getRealPath("/");
	            File directoryBase = new File(rootPath).getCanonicalFile();

	            File fileFoto = new File(directoryBase, img.getPercorso()).getCanonicalFile();

	            if (fileFoto.exists() && fileFoto.getPath().startsWith(directoryBase.getPath()) && fileFoto.isFile()) {
	                
	                String mimeType = getServletContext().getMimeType(fileFoto.getAbsolutePath());
	                response.setContentType(mimeType != null ? mimeType : "image/jpeg");
	                response.setContentLength((int) fileFoto.length());

	                try (FileInputStream in = new FileInputStream(fileFoto);
	                     OutputStream out = response.getOutputStream()) {
	                    byte[] buffer = new byte[4096];
	                    int bytesRead;
	                    while ((bytesRead = in.read(buffer)) != -1) {
	                        out.write(buffer, 0, bytesRead);
	                    }
	                    return; 
	                }
	            } else {
	                System.err.println("Accesso negato o file non trovato: " + fileFoto.getPath());
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
