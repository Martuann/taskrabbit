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
	   		int counter=0;
			for(Richiesta r : richieste) { 
			counter++;
	        Utente pro = r.getUtenteRichiesto(); 
	        String colore = "#E4A11B"; 
	        if(r.getStato() == StatoRichiesta.completato) colore = "#14A44D";
	        if(r.getStato() == StatoRichiesta.rifiutato) colore = "#DC4C64";
	  	%>
		<div class="richiesta">
			<div class="titolo">
				<img src="<%= request.getContextPath() %>/recuperaFoto?idUtenteRichiesto=<%= pro.getId() %>" 
     style="width:50px; height:50px; border-radius:50%; object-fit: cover;" 
     onerror="this.src='<%= request.getContextPath() %>/immagini/default-avatar.png';">
	          	<p><%= pro.getNome() + " " + pro.getCognome() %></p>
			</div>
			<p style="font-weight:bold; color: <%= colore %>">
				Stato richiesta: <%= r.getStato().toString().replace("_", " ") %>
		    </p>
			<p>Task: <%= r.getProfessione().getNome() %></p>
			<p>In data: <%= r.getData() %></p>
			<p>Orario: <%= r.getOrarioInizio() %> - <%= r.getOrarioFine() %></p>
			<p>Indirizzo: <%= r.getIndirizzo() %></p>
			<div class="description-container">
				<a href="" class="see-more">Vedi descrizione↓</a>
				<p class="description" style="display:none">
					<%= r.getDescrizione() %>
				</p>
			</div>
		    <% if(r.getStato() == StatoRichiesta.completato && !giaRecensiti.contains(r.getUtenteRichiesto().getId())) { %>
			    <form action="ScriviRecensioneServlet" method="POST">
					<input type="hidden" name="idProfessionista" value="<%= r.getUtenteRichiesto().getId() %>">
					<input type="submit" value="Scrivi una Recensione">
			    </form>
			<% } %>
			<% if(r.getStato() == StatoRichiesta.in_corso) { %>
				<form action="AggiornaRichiesta" method="POST">
					<input type="hidden" name="id1" value="<%= r.getId() %>">
					<input type="hidden" name="type" value="completato">
					<input type="submit" value="Segna come completata">
				</form>
			<% } %>
		</div>
		<% } } %>
	</div>
</div>
<script>
	let seeMoreSet = document.querySelectorAll('.see-more');
	seeMoreSet.forEach(function(link){
		link.addEventListener("click",function(e){
			e.preventDefault();
			let description = this.nextElementSibling;
		if(description.getAttribute("style") === "display:none"){
			description.setAttribute("style","display:block");
			this.innerText = "Vedi descrizione↑";
		} else{
			description.setAttribute("style","display:none");
			this.innerText = "Vedi descrizione↓";
		}
		});
	})
</script>
<%@ include file="/WEB-INF/headerFooter/footer.jsp"%>
</body>
</html>