<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="org.elis.progetto.model.Richiesta" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pagina Cronologia Richieste</title>
<link rel="stylesheet" href="css/gestioneRichieste.css">
</head>
<body>
	<h1>Cronologia Richieste:</h1>
	<div id="empty-message1" style="display:<%= request.getAttribute("emptyMessage1") %>">
		<p>Nessuna richiesta ricevuta.</p>
	</div>
	<% List<Richiesta> richieste = (List<Richiesta>) request.getAttribute("richieste");
	int counter=0;
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
		</div>
	<% counter++;
	} %>
</body>
</html>