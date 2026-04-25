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

@WebServlet("/CaricaFotoGalleria")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 20)
public class CaricaFotoGalleriaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ImmagineDao immagineDao;

    @Override
    public void init() throws ServletException {
        immagineDao = DaoFactory.getInstance().getImmagineDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utenteLoggato") == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        Utente utente = (Utente) session.getAttribute("utenteLoggato");

        try {
            Part filePart = request.getPart("fotoLavoro"); // Nome del campo nel form HTML
            
            if (filePart != null && filePart.getSize() > 0) {
                String contentType = filePart.getContentType();
                String estensione = ".png"; 
                if (contentType != null) {
                    if (contentType.equals("image/jpeg")) {
                        estensione = ".jpg";
                    } else if (contentType.equals("image/gif")) {
                        estensione = ".gif";
                    }
                }

                String nomeCartella = "GalleriaLavori"; 
                String rootPath = getServletContext().getRealPath("/");
                File directoryBase = new File(rootPath, nomeCartella).getCanonicalFile();

                if (!directoryBase.exists()) {
                    directoryBase.mkdirs();
                }

                String nomeFile = "lavoro_" + utente.getId() + "_" + UUID.randomUUID().toString().substring(0, 8) + estensione;
                File fileFinale = new File(directoryBase, nomeFile).getCanonicalFile();

                if (fileFinale.getPath().startsWith(directoryBase.getPath())) {
                    filePart.write(fileFinale.getAbsolutePath());

                    Immagine nuovaFoto = new Immagine();
                    nuovaFoto.setNome(nomeFile); 
                    nuovaFoto.setPercorso(nomeCartella + "/" + nomeFile);
                    nuovaFoto.setIsFotoProfilo(false); 
                    nuovaFoto.setUtente(utente);
                    
                    immagineDao.insert(nuovaFoto);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Percorso non valido.");
                    return;
                }
            }
            

            response.sendRedirect(request.getContextPath() + "/ProfiloProfessionistaServlet?uploadSuccess=true");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/ProfiloProfessionistaServlet?error=upload_failed");
        }
    }
}