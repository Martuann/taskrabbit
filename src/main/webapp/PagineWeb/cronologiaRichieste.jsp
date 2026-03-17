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
<title>Cronologia di <%= ((Utente)request.getAttribute("utenteLoggato")).getNome() %></title>
<link rel="stylesheet" href="css/cronologiaRichieste.css">
</head>
<body>
	<h1>Cronologia Richieste:</h1>
	<% List<Richiesta> richieste = (List<Richiesta>) request.getAttribute("richieste"); %>
	<div id="empty-message1" style="display:<%= (richieste.isEmpty()) ? "block":"none" %>">
		<p>Nessuna richiesta ricevuta.</p>
	</div>
	<div class="container-richieste">
	<% int counter=0;
	for(Richiesta r : richieste) { %>
		<div class="richiesta">
			<div class="titolo">
				<img src="#">
				<p><%= request.getAttribute("nomeutente"+counter) %></p>
			</div>
			<p style="color:<%= request.getAttribute("coloreStato"+counter) %>">
				Stato richiesta: <%= request.getAttribute("statoRichiesta"+counter) %>
			</p>
			<p>Task: <%= request.getAttribute("task"+counter) %></p>
			<p>In data: <%= request.getAttribute("data"+counter) %></p>
			<p>Ore: <%= request.getAttribute("orario"+counter) %></p>
			<p>Veicolo richiesto: <%= request.getAttribute("veicolo"+counter) %></p>
			<p>Indirizzo: <%= request.getAttribute("indirizzo"+counter) %></p>
			<a href="ScriviRecensioneServlet?id1=<%= request.getParameter("id") %>&id2=<%= request.getAttribute("idProfessionista"+counter) %>" style="display:<%= request.getAttribute("recensioneFlag"+counter) %>">Scrivi una Recensione</a>
			<a href="AggiornaRichiesta?type=completato&id1=<%= r.getId() %>&redirect=CronologiaRichiesteServlet&id2=<%= request.getParameter("id") %>" style="display:<%= (r.getStato()==StatoRichiesta.in_attesa) ? "inline-block":"none" %>">Segna richiesta come completata</a>
		</div>
	<% counter++;
	} %>
	</div>
</body>
</html>