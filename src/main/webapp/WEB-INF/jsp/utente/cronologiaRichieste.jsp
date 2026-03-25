<%@page import="org.elis.progetto.model.Immagine"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="org.elis.progetto.model.Richiesta" %>
<%@ page import="org.elis.progetto.model.StatoRichiesta" %>
<%@ page import="org.elis.progetto.model.Utente" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cronologia di <%= ((Utente) request.getSession().getAttribute("utenteLoggato")).getNome() %></title>
<link rel="stylesheet" href="css/cronologiaRichieste.css">
</head>
<body>
<%@ include file="/WEB-INF/headerFooter/header.jsp" %>
<div id="main-container">
    <h1>Cronologia Richieste:</h1>
    
   <% 
        List<Richiesta> richieste = (List<Richiesta>) request.getAttribute("richieste"); 
        Set<Long> giaRecensiti = (Set<Long>) request.getAttribute("giaRecensiti");
    %>

    <div class="container-richieste">
        <% if(richieste == null || richieste.isEmpty()) { %>
            <div id="empty-message1">
                <p>Nessuna richiesta effettuata.</p>
            </div>
   <% } else { 
            for(Richiesta r : richieste) { 
                Utente pro = r.getUtenteRichiesto(); 
                
                String colore = "#E4A11B"; 
                if(r.getStato() == StatoRichiesta.completato) colore = "#14A44D";
                if(r.getStato() == StatoRichiesta.rifiutato) colore = "#DC4C64";
        %>
        <div class="richiesta">
   <div class="titolo">
  
    
    
   
  <img src="<%= pro.getFotoProfiloPath(request.getContextPath()) %>" 
          style="width:50px; height:50px; border-radius:50%; object-fit: cover;" 
          onerror="this.src='<%= request.getContextPath() %>/immagini/default-avatar.png';">
          
    <p><%= pro.getNome() + " " + pro.getCognome() %></p>
</div>

      
            <p style="color: <%= colore %>">
                Stato richiesta: <%= r.getStato().toString().replace("_", " ") %>
            </p>

            <p>Task: <%= r.getProfessione().getNome() %></p>
            
            <p>In data: <%= r.getData() %></p>
            <p>Orario: <%= r.getOrarioInizio() %> - <%= r.getOrarioFine() %></p>
            <p>Indirizzo: <%= r.getIndirizzo() %></p>
            
             <% if(r.getStato() == StatoRichiesta.completato && !giaRecensiti.contains(pro.getId())) { %>
                <a href="ScriviRecensioneServlet?id=<%= pro.getId() %>" class="btn-link">Scrivi una Recensione</a>
            <% } %>

            <% if(r.getStato() == StatoRichiesta.in_corso) { %>
                <a href="AggiornaRichiesta?type=completato&id1=<%= r.getId() %>&redirect=CronologiaRichiesteServlet" class="btn-link">Segna come completata</a>
            <% } %>
        </div>
        <%      } 
           } 
        %>
    </div>
</div>
	<%@ include file="/WEB-INF/headerFooter/footer.jsp"%>
</body>
</html>