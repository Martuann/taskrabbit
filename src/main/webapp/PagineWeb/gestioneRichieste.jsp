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
<% Utente utenteLoggato = (Utente) request.getSession().getAttribute("utenteLoggato"); %>
<title>Richieste di <%= utenteLoggato.getNome() %></title>
<link rel="stylesheet" href="css/gestioneRichieste.css">
</head>
<body>
<%@ include file="/WEB-INF/headerFooter/header.jsp" %>
	<div id="main-container">
	<h1>Richieste Ricevute:</h1>
	<% List<Richiesta> richieste = (List<Richiesta>) request.getAttribute("richieste");
	String displayValue1 = "block";
	String displayValue2 = "block"; %>
	<div class="container-richieste">
	<% for(int i=0;i<richieste.size();i++) { 
		if(richieste.get(i).getStato()==StatoRichiesta.in_attesa) { 
			displayValue1 = "none"; %>
			<div class="richiesta">
				<div class="titolo">
					<img src="#">
					<p class="nome-utente"><%= request.getAttribute("nomeutente"+i) %></p>
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
	<% }
	} %>
	<div style="display:<%= displayValue1 %>">
		<p>Nessuna richiesta ricevuta.</p>
	</div>
	</div>
	<h1>Cronologia Richieste:</h1>
	<div class="container-richieste">
	<% for(int i=0;i<richieste.size();i++) { 
		if(richieste.get(i).getStato()!=StatoRichiesta.in_attesa) {
			displayValue2 = "none"; %>
			<div class="richiesta">
				<div class="titolo">
					<img src="#">
					<p class="nome-utente"><%= request.getAttribute("nomeutente"+i) %></p>
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
	<% }
	} %>
	<div style="display:<%= displayValue2 %>">
		<p>Nessuna richiesta completata.</p>
	</div>
	</div>
	</div>
	<%@ include file="/WEB-INF/headerFooter/footer.jsp"%>
</body>
</html>