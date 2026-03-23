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
        Map<Long, Utente> mappaPro = (Map<Long, Utente>) request.getAttribute("mappaProfessionisti");
        Map<Long, String> mappaTask = (Map<Long, String>) request.getAttribute("mappaTask");
        Set<Long> giaRecensiti = (Set<Long>) request.getAttribute("giaRecensiti");
        Map<Long, String> mappaFoto=(Map<Long, String>) request.getAttribute("mappaFoto");
    %>

    <div class="container-richieste">
        <% if(richieste == null || richieste.isEmpty()) { %>
            <div id="empty-message1">
                <p>Nessuna richiesta effettuata.</p>
            </div>
        <% } else { 
            for(Richiesta r : richieste) { 
                Utente pro = mappaPro.get(r.getIdUtenteRichiesto());
                
                String colore = "#E4A11B"; 
                if(r.getStato() == StatoRichiesta.completato) colore = "#14A44D";
                if(r.getStato() == StatoRichiesta.rifiutato) colore = "#DC4C64";
        %>
        <div class="richiesta">
   <div class="titolo">
    <% 
        String pathFoto = mappaFoto.get(r.getIdUtenteRichiesto());
   
        if(pathFoto == null || pathFoto.trim().isEmpty()) {
            pathFoto = request.getContextPath() + "/immagini/default-avatar.png";
        } else {
         
            if(!pathFoto.startsWith("http") && !pathFoto.startsWith("/")) {
                pathFoto = request.getContextPath() + "/" + pathFoto;
            }
        }
    %>
    <img src="<%= pathFoto %>" style="width:50px; height:50px; border-radius:50%; object-fit: cover;" onerror="this.src='<%= request.getContextPath() %>/immagini/default-avatar.png';">
    <p><%= pro.getNome() + " " + pro.getCognome() %></p>
</div>

                <p style="color: <%= colore %>">
                    Stato richiesta: <%= r.getStato().toString().replace("_", " ") %>
                </p>

                <p>Task: <%= mappaTask.get(r.getIdProfessione()) %></p>
                <p>In data: <%= r.getData() %></p>
                <p>Orario: <%= r.getOrarioInizio() %> - <%= r.getOrarioFine() %></p>
                <p>Indirizzo: <%= r.getIndirizzo() %></p>
                
                <% if(r.getStato() == StatoRichiesta.completato && !giaRecensiti.contains(r.getIdUtenteRichiesto())) { %>
                	<form action="ScriviRecensioneServlet" method="POST">
                		<input type="hidden" name="idProfessionista" value="<%= r.getIdUtenteRichiesto() %>">
                    	<input type="submit" value="Scrivi una Recensione">
                	</form>
                <% } %>

                <% if(r.getStato() == StatoRichiesta.in_corso) { %>
				<form action="AggiornaRichiesta" method="POST">
					<input type="hidden" name="idRichiesta" value="<%= r.getId() %>">
					<input type="hidden" name="nuovoStato" value="completato">
					<input type="submit" value="Segna come completata">
				</form>
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