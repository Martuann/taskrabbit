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
@WebServlet("/recuperaFotoProfilo")
public class RecuperaFotoProfiloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private ImmagineDao immagineDao;
	    @Override
	    public void init() throws ServletException {
	    	immagineDao=DaoFactory.getInstance().getImmagineDao();
	    } 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecuperaFotoProfiloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
	
		
		Utente utente = (Utente) session.getAttribute("utenteLoggato");
		
		try {
		
			Immagine img = immagineDao.selectByIdUtente(utente.getId());
			
			if (img != null && img.getPercorso() != null) {
				String percorsoAssoluto = getServletContext().getRealPath("/") + img.getPercorso();
				File fileFoto = new File(percorsoAssoluto);
				
				if (fileFoto.exists()) {
					response.setContentType(getServletContext().getMimeType(percorsoAssoluto));
					
					// Spara i byte della foto al browser
					try (FileInputStream in = new FileInputStream(fileFoto);
					     OutputStream out = response.getOutputStream()) {
						byte[] buffer = new byte[4096];
						int bytesRead;
						while ((bytesRead = in.read(buffer)) != -1) {
							out.write(buffer, 0, bytesRead);
						}
						return; 
					}
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
