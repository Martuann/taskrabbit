<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.elis.progetto.model.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Profilo di <%= ((Utente)request.getAttribute("professionista")).getNome() %></title>
    <link rel="stylesheet" href="css/profilo_professionista.css">
</head>
<body>
    
    <%@ include file="/WEB-INF/headerFooter/header.jsp"%>

    <main class="container-profilo">
        <%
            Utente professionista = (Utente) request.getAttribute("professionista");
            List<Veicolo> veicoli = (List<Veicolo>) request.getAttribute("veicoli");
            List<Professione> professioni = (List<Professione>) request.getAttribute("profeUtente");
            List<UtenteProfessione> utenteProf = (List<UtenteProfessione>) request.getAttribute("utenteProf");
            List<Immagine> galleria = (List<Immagine>) request.getAttribute("galleria");
            String proPic = (String) request.getAttribute("proPicProfilo");
        %>

        <section class="user-info-section">
            <div class="profile-main-data">
                <img id='propicprofilo' src='<%= proPic %>' alt="Foto Profilo">
                <div id="infoprofilo">
                    <h1 id="nomeprofilo"><%= professionista.getNome() + " " + professionista.getCognome() %></h1>
                    
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
                                        if(p.getId() == up.getIdProfessione()) {
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
            </div>
        </section>

        <hr>

        <section class="gallery-section">
            <h2>Portfolio Lavori</h2>
            <div id="imgs">
                <% if(galleria != null && !galleria.isEmpty()) { 
                    for(int i=0; i < galleria.size(); i++) { %>
                        <img id="img_lavoro_<%= i %>" class="imglavoro" src="<%= galleria.get(i).getPercorso() %>">
                <%  } 
                   } else { %> <p>Nessuna foto di lavoro disponibile.</p> <% } %>
            </div>
        </section>

        <hr>

        <section class="reviews-section">
            <h2>Cosa dicono i clienti</h2>
            <% 
                List<Recensione> recensioni = (List<Recensione>) request.getAttribute("recensioni"); 
                List<Utente> listaRecensori = (List<Utente>) request.getAttribute("listaRecensori"); 
                Map<Utente, String> mappaRecensori = (Map<Utente, String>) request.getAttribute("mappaRecensori");

                if(recensioni != null && !recensioni.isEmpty()) {
                    for(int i=0; i < recensioni.size(); i++) { 
                        Recensione r = recensioni.get(i);
                        Utente autore = listaRecensori.get(i);
                        String fotoAutore = mappaRecensori.get(autore);
            %>
            <div class="recensione-card">
                <div class="recensione-header">
                    <img class="propic-recensore" src="<%= fotoAutore %>">
                    <div class="recensore-meta">
                        <strong class="nomeutente"><%= autore.getNome() + " " + autore.getCognome() %></strong>
                        <p class="rating">Voto: <%= r.getVoto() %>/5 ⭐</p>
                    </div>
                    <p class="data-recensione"><%= r.getData().toString() %></p>
                </div>
                <div class="recensione-body">
                    <p class="descrizione"><%= r.getDescrizione() %></p>
                </div>
            </div>
            <%      } 
                } else { %> <p>Non ci sono ancora recensioni per questo professionista.</p> <% } %>
                
            <section class="contact-section">
            <h2>Vuoi contattare questo professionista?</h2>
            
          
            <form action="InoltroRichieste" method="POST">
            	<input type="hidden" name="id_professionista" value="<%= professionista.getId() %>">
            	<input type="submit" value="Contatta" class="contatta-class">
            </form>
            
        </section>
    </main>

    <%@ include file="/WEB-INF/headerFooter/footer.jsp"%>
    

</body>
</html>