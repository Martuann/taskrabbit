<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="org.elis.progetto.model.Recensione" %>
<%@ page import="org.elis.progetto.model.Utente" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pagina Profilo Professionista</title>
</head>
<body>
	<header>
		<img id='propicprofilo' src='<%= request.getAttribute("propicprofilo") %>'>
		<strong id="nomecognome"><%= request.getAttribute("nomecognome") %></strong>
		<p id="veicoli">Veicoli: <%= request.getAttribute("veicoli") %></p>
		<p id="tariffa">Tariffa: <%= request.getAttribute("tariffa") %> €/h</p>
	</header>
	<section>
		<h2>Foto di lavoro: </h2>
		<div id="imgs">
			<img id="img1" class="imglavoro" src="<%= request.getAttribute("img1") %>">
			<img id="img2" class="imglavoro" src="<%= request.getAttribute("img2") %>">
			<img id="img3" class="imglavoro" src="<%= request.getAttribute("img3") %>">
			<img id="img4" class="imglavoro" src="<%= request.getAttribute("img4") %>">
		</div>
	</section>
	<footer>
		<h2>Recensioni per <%= request.getAttribute("task") %>: </h2>
		<% 
		List<Recensione> recensioni = (List<Recensione>) request.getAttribute("recensioni"); 
		List<Utente> recensori = (List<Utente>) request.getAttribute("recensori"); 
		for(int i=0; i<recensioni.size(); i++) { %>
		<div class="recensione">
			<div class="top">
				<img id="propic" src="<%= request.getAttribute("propic"+i) %>">
				<strong id="nomecognome"><%= request.getAttribute("nomecognome"+i) %></strong>
				<p id="rating">Rating: <%= request.getAttribute("rating"+i) %>/5</p>
			</div>
			<div class="center">
				<p id="task">Task: <%= request.getAttribute("task"+i) %></p>
				<p id="data">Data: <%= request.getAttribute("data"+i) %></p>
			</div>
			<div class="bottom">
				<p id="descrizione"><%= request.getAttribute("descrizione"+i) %></p>
			</div>
		</div>
		<% } %>
	</footer>
</body>
</html>