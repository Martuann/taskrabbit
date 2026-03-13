<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="org.elis.progetto.model.Richiesta" %>
<%@ page import="org.elis.progetto.model.StatoRichiesta" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pagina Gestione Richieste</title>
<link rel="stylesheet" href="css/gestioneRichieste.css">
</head>
<body>
	<div id="richieste_ricevute">
	<h1>Richieste Ricevute:</h1>
	<div id="empty-message1" style="display:<%= request.getAttribute("emptyMessage1") %>">
		<p>Nessuna richiesta ricevuta.</p>
	</div>
	<% List<Richiesta> richieste = (List<Richiesta>) request.getAttribute("richieste"); 
	for(int i=0;i<richieste.size();i++) { 
		if(richieste.get(i).getStato()==StatoRichiesta.in_attesa) { %>
			<div class="richiesta">
				<div class="titolo">
					<img src="#">
					<p><%= request.getAttribute("nomeutente"+i) %></p>
				</div>
				<p style="color:<%= request.getAttribute("coloreStato"+i) %>">
					Stato richiesta: <%= request.getAttribute("statoRichiesta"+i) %>
				</p>
				<p>Task: <%= request.getAttribute("task"+i) %></p>
				<p>In data: <%= request.getAttribute("data"+i) %></p>
				<p>Ore: <%= request.getAttribute("orario"+i) %></p>
				<p>Veicolo richiesto: <%= request.getAttribute("veicolo"+i) %></p>
				<p>Indirizzo: <%= request.getAttribute("indirizzo"+i) %></p>
				<p>Retribuzione: €<%= request.getAttribute("costoeffettivo"+i) %></p>
				<a href="AggiornaRichiesta?type=in_corso&id1=<%= request.getAttribute("idRichiesta"+i) %>&id2=<%= request.getParameter("id") %>">Accetta richiesta</a>
				<a href="AggiornaRichiesta?type=rifiutato&id1=<%= request.getAttribute("idRichiesta"+i) %>&id2=<%= request.getParameter("id") %>">Rifiuta richiesta</a>
			</div>
		<% } %>
	<% } %>
	</div>
	<div id="richieste_accettate">
	<h1>Cronologia Richieste:</h1>
	<% List<Richiesta> richiesteAccettate = (List<Richiesta>) request.getAttribute("richiesteAccettate"); 
	for(int i=0;i<richieste.size();i++) { 
		if(richieste.get(i).getStato()!=StatoRichiesta.in_attesa) { %>
			<div class="richiesta">
				<div class="titolo">
					<img src="#">
					<p><%= request.getAttribute("nomeutente"+i) %></p>
				</div>
				<p style="color:<%= request.getAttribute("coloreStato"+i) %>">
					Stato richiesta: <%= request.getAttribute("statoRichiesta"+i) %>
				</p>
				<p>Task: <%= request.getAttribute("task"+i) %></p>
				<p>In data: <%= request.getAttribute("data"+i) %></p>
				<p>Ore: <%= request.getAttribute("orario"+i) %></p>
				<p>Veicolo richiesto: <%= request.getAttribute("veicolo"+i) %></p>
				<p>Indirizzo: <%= request.getAttribute("indirizzo"+i) %></p>
				<p>Retribuzione: €<%= request.getAttribute("costoeffettivo"+i) %></p>
			</div>
		<% } %>
	<% } %>
	</div>
</body>
</html>