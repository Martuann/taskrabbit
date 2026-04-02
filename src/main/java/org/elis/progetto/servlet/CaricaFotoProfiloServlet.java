package org.elis.progetto.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.ImmagineDao;
import org.elis.progetto.model.Immagine;
import org.elis.progetto.model.Utente;

/**
 * Servlet implementation class CaricaFotoProfiloServlet
 */
@WebServlet("/CaricaFotoProfilo")
@MultipartConfig(fileSizeThreshold = 1024*1024 *2, maxFileSize =  1024 * 1024 * 20)
public class CaricaFotoProfiloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ImmagineDao immagineDao;
    @Override
    public void init() throws ServletException {
    	immagineDao=DaoFactory.getInstance().getImmagineDao();
    } 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CaricaFotoProfiloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("utenteLoggato") == null) {
			response.sendRedirect(request.getContextPath() + "/Login");
			return;
		}

		Utente utente = (Utente) session.getAttribute("utenteLoggato");

		try {
			Part filePart = request.getPart("foto");
			
			if (filePart != null && filePart.getSize() > 0) {
				String contentDisposition = filePart.getHeader("content-disposition");
				String estensione = ".png"; 
				if (contentDisposition.contains(".jpg") || contentDisposition.contains(".jpeg")) {
					estensione = ".jpg";
				}

				String nomeFile = "avatar_" + utente.getId() + "_" + UUID.randomUUID().toString().substring(0, 8) + estensione;
				
		
				String cartellaSalvataggio = getServletContext().getRealPath("/") + "CaricamentiUtente/" + File.separator;
				File directory = new File(cartellaSalvataggio);
				if (!directory.exists()) {
					directory.mkdirs();
				}

				String percorsoFinale = cartellaSalvataggio + nomeFile;
				filePart.write(percorsoFinale);

				Immagine nuovaFoto = new Immagine();
				nuovaFoto.setNome(nomeFile); 
				nuovaFoto.setPercorso("CaricamentiUtente/" + nomeFile);
				nuovaFoto.setIsFotoProfilo(true);
				nuovaFoto.setUtente(utente);
				immagineDao.insert(nuovaFoto);
			}
			
			response.sendRedirect(request.getContextPath() + "/Profilo");
	} catch (Exception e) {
		e.printStackTrace();
		response.sendError(500, "Errore durante l'upload della foto.");
	}
	}

}
