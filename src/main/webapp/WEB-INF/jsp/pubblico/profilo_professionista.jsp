<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.elis.progetto.model.*" %>
<!DOCTYPE html>
<html>
<%
    Utente professionista = (Utente) request.getAttribute("professionista");
    List<Veicolo> veicoli = (List<Veicolo>) request.getAttribute("veicoli");
    List<Professione> professioni = (List<Professione>) request.getAttribute("profeUtente");
    List<UtenteProfessione> utenteProf = (List<UtenteProfessione>) request.getAttribute("utenteProf");
    List<Immagine> galleria = (List<Immagine>) request.getAttribute("galleria");
    String proPic = (String) request.getAttribute("proPicProfilo");
    Double media = (Double) request.getAttribute("media");
    if(professionista == null) {
        response.sendRedirect(request.getContextPath() + "/HomepageServlet");
        return;
    }
%>
<head>
    <meta charset="UTF-8">
    <title>Profilo di <%= ((Utente)request.getAttribute("professionista")).getNome() %>    </title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/profilo_professionista.css">
</head>
<body>
    
    <%@ include file="/WEB-INF/headerFooter/header.jsp"%>

    <main class="container-profilo">
   

        <section class="user-info-section">
            <div class="profile-main-data">
<img id='propicprofilo' 
     src="<%= request.getContextPath() %>/recuperaFoto?idUtenteRichiesto=<%= professionista.getId() %>" 
     alt="Foto Profilo" 
     onerror="this.src='<%= request.getContextPath() %>/immagini/default-avatar.png';">                  
       <h1 id="nomeprofilo"><%= professionista.getNome() + " " + professionista.getCognome()%></h1>
                    
                    <p id="p-veicoli"><strong>Veicoli disponibili:</strong> 
                        <% if(veicoli != null && !veicoli.isEmpty()) { 
                            for(int i=0; i < veicoli.size(); i++) { %>
                                <%= veicoli.get(i).getCategoria() %><%= (i < veicoli.size()-1) ? ", " : "" %>
                        <%  } 
                           } else { %> Nessuno <% } %>
                    </p>
                    
                    <div id="p-professioni">
                        <strong>Servizi e Tariffe orarie:</strong>
                        <ul style="list-style: none; padding-left: 0;">
                        <% 
                            if(utenteProf != null && !utenteProf.isEmpty()) { 
                                for(UtenteProfessione up : utenteProf) { 
                                    String nomeAttivita = "Servizio";
                                    for(Professione p : professioni) {
                                        if(p.getId().equals(up.getProfessione().getId())) {
                                            nomeAttivita = p.getNome();
                                            break;
                                        }
                                    }
                        %>
                            <li><%= nomeAttivita %>: <strong><%= up.getTariffaH() %> €/h</strong></li>
                        <% 
                                } 
                            } else { 
                        %>
                            <li>Tariffe non disponibili</li>
                        <% } %>
                        </ul>
                    </div>
                </div>
        </section>

        <hr>

<%
    Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");
    
    boolean isProprietario = false;
    if (utenteLoggato != null && professionista != null) {
        if (utenteLoggato.getId() == professionista.getId()) {
            isProprietario = true;
        }
    }
%>

<section class="gallery-section">
    <h2>Portfolio Lavori</h2>
    
    <% if(isProprietario) { %>
        <div class="upload-box" style="margin-bottom: 20px; padding: 15px; border: 1px dashed #ccc;">
            <h3>Aggiungi una foto ai tuoi lavori</h3>
            <form action="<%= request.getContextPath() %>/CaricaFotoGalleria" method="post" enctype="multipart/form-data">
                <input type="file" name="fotoLavoro" accept="image/*" required>
                <button type="submit" class="contatta-class">Carica Foto</button>
            </form>
        </div>
    <% } %>

    <% if(galleria != null && !galleria.isEmpty()) { %>
        <div class="carousel-container">
<button type="button" class="carousel-btn prev" onclick="moveCarousel(-1)">&#10094;</button>
            <div class="carousel-track-container">
                <div id="carousel-track">
                    <% for(int i=0; i < galleria.size(); i++) { 
                        Immagine imgCorrente = galleria.get(i);
                    %>
                        <div class="carousel-item">
                            <img class="imglavoro" 
                                 src="<%= request.getContextPath() %>/recuperaFoto?id=<%= imgCorrente.getId() %>"
                                 alt="Lavoro del professionista" onclick="openModal(this.src)">
                            
                            <% if(isProprietario) { %>
                                <a href="<%= request.getContextPath() %>/EliminaFoto?id=<%= imgCorrente.getId() %>" 
                                   class="btn-elimina-carousel" 
                                   onclick="return confirm('Eliminare questa foto?');">
                                   &times;
                                </a>
                            <% } %>
                        </div>
                    <% } %>
                </div>
            </div>

<button type="button" class="carousel-btn next" onclick="moveCarousel(1)">&#10095;</button>
        </div>
    <% } else { %> 
        <p>Nessuna foto di lavoro disponibile.</p> 
    <% } %>
</section>

        <hr>

        <section class="reviews-section">
            <h2>Cosa dicono i clienti | <%  if(media != null) { %><%= media%>⭐<%}%></h2>
            <% 
                List<Recensione> recensioni = (List<Recensione>) request.getAttribute("recensioni"); 
                List<Utente> listaRecensori = (List<Utente>) request.getAttribute("listaRecensori"); 

         
            
            if(recensioni != null && listaRecensori != null && !recensioni.isEmpty() && recensioni.size() == listaRecensori.size()) {
                    for(int i=0; i < recensioni.size(); i++) { 
                        Recensione r = recensioni.get(i);
                        Utente autore = listaRecensori.get(i);
            %>
            <div class="recensione-card">
                <div class="recensione-header">
<img class="propic-recensore" 
     src="<%= request.getContextPath() %>/recuperaFoto?idUtenteRichiesto=<%= autore.getId() %>" 
     style="width:50px; height:50px; border-radius:50%; object-fit: cover;" 
     onerror="this.src='<%= request.getContextPath() %>/immagini/default-avatar.png';">
     <div>
                             <strong class="nomeutente"><%= autore.getNome() + " " + autore.getCognome() %></strong>
                        <p class="rating">Voto: <%= r.getVoto() %>/5 ⭐</p>
                        </div>
                    </div>
             <div class="recensione-body" style="margin-top: 10px;">
            <p class="data-recensione" style="font-size: 0.8em; color: #666;"><%= r.getData().toString() %></p>
            <p class="descrizione"><%= r.getDescrizione() %></p>
        </div>
         </div>
            <%      } 
                }
            else { %> <p>Non ci sono ancora recensioni per questo professionista.</p> <% } %>
	</section>         
	  <% if(!isProprietario && utenteLoggato != null) { %>
    <section class="contact-section">
        <h2>Vuoi contattare questo professionista?</h2>
        <form action="<%= request.getContextPath() %>/InoltroRichieste" method="Get">
            <input type="hidden" name="id_professionista" value="<%= professionista.getId() %>">
            <input type="submit" value="Contatta" class="contatta-class">
        </form>
    </section>
<% } %>
    </main>
<div id="imageModal" class="modal-zoom" onclick="this.style.display='none'">
    <span class="close-zoom">&times;</span>
    <img class="modal-content" id="imgZoom">
</div>
    
    <%@ include file="/WEB-INF/headerFooter/footer.jsp"%>
<script>
let currentIndex = 0;

document.addEventListener("DOMContentLoaded", function() {
    setupCarousel();
    checkButtonsVisibility();
});

// 1. Configurazione iniziale del binario
function setupCarousel() {
    const track = document.getElementById('carousel-track');
    if (track) {
        track.style.display = "flex";
        track.style.flexDirection = "row";
        track.style.flexWrap = "nowrap";
        track.style.width = "max-content";
        track.style.transition = "margin-left 0.5s ease";
    }
}

// 2. Nasconde i bottoni se non c'è abbastanza contenuto da scorrere
function checkButtonsVisibility() {
    const container = document.querySelector('.carousel-track-container');
    const track = document.getElementById('carousel-track');
    const prevBtn = document.querySelector('.carousel-btn.prev');
    const nextBtn = document.querySelector('.carousel-btn.next');

    if (!container || !track) return;

    // Se la larghezza del binario è minore o uguale al contenitore, nascondi i tasti
    if (track.scrollWidth <= container.offsetWidth) {
        prevBtn.classList.add('hidden-btn');
        nextBtn.classList.add('hidden-btn');
    } else {
        prevBtn.classList.remove('hidden-btn');
        nextBtn.classList.remove('hidden-btn');
    }
}

// 3. Funzione di movimento (quella che abbiamo sistemato prima)
function moveCarousel(direction) {
    const track = document.getElementById('carousel-track');
    const items = document.querySelectorAll('.carousel-item');
    const container = document.querySelector('.carousel-track-container');
    
    if (!track || items.length === 0) return;

    const itemWidth = 315; 
    const visibleWidth = container.offsetWidth;
    const maxScroll = track.scrollWidth - visibleWidth;
    
    currentIndex += direction;

    let offset = currentIndex * itemWidth;

    // Limiti
    if (offset < 0) {
        offset = 0;
        currentIndex = 0;
    }
    if (offset > maxScroll) {
        offset = maxScroll;
        currentIndex = Math.ceil(maxScroll / itemWidth);
    }

    track.style.setProperty("margin-left", "-" + offset + "px", "important");
}

// 4. Funzioni per lo Zoom dell'immagine
function openModal(src) {
    const modal = document.getElementById("imageModal");
    const modalImg = document.getElementById("imgZoom");
    modal.style.display = "block";
    modalImg.src = src;
}

// Ricalcola visibilità bottoni se l'utente ridimensiona la finestra
window.addEventListener('resize', checkButtonsVisibility);
</script>

</body>
</html>