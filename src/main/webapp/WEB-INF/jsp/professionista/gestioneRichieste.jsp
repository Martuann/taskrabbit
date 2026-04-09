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
			    <img src="<%= request.getContextPath() %>/recuperaFoto?idUtenteRichiesto=<%= richieste.get(i).getUtenteRichiedente().getId() %>" 
     style="width:50px; height:50px; border-radius:50%; object-fit: cover;"
     onerror="this.src='<%= request.getContextPath() %>/immagini/default-avatar.png';"
     alt="Avatar">
			    <p class="nome-utente"><%= request.getAttribute("nomeutente"+i) %></p>
			</div>
			<p style="font-weight:bold; color:<%= request.getAttribute("coloreStato"+i) %>">
				Stato richiesta: <%= request.getAttribute("statoRichiesta"+i) %>
			</p>
			<p>Task: <%= request.getAttribute("task"+i) %></p>
			<p>In data: <%= request.getAttribute("data"+i) %></p>
			<p>Ore: <%= request.getAttribute("orario"+i) %></p>
			<p>Veicolo richiesto: <%= request.getAttribute("veicolo"+i) %></p>
			<p>Indirizzo: <%= request.getAttribute("indirizzo"+i) %></p>
			<p>Retribuzione: €<%= request.getAttribute("costoeffettivo"+i) %></p>
			<div class="description-container">
				<a href="" class="see-more">Vedi descrizione↓</a>
				<p class="description" style="display:none">
					<%= richieste.get(i).getDescrizione() %>
				</p>
			</div>
			<div class="buttons-container">
				<form action="AggiornaRichiesta" method="POST">
					<input type="hidden" name="idRichiesta" value="<%= richieste.get(i).getId() %>">
					<input type="hidden" name="nuovoStato" value="in_corso">
					<input type="submit" value="Accetta richiesta">
				</form>
				<form action="AggiornaRichiesta" method="POST">
					<input type="hidden" name="idRichiesta" value="<%= richieste.get(i).getId() %>">
					<input type="hidden" name="nuovoStato" value="rifiutato">
					<input type="submit" value="Rifiuta richiesta">
				</form>
			</div>
	</div>
	<% } } %>
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
					<img src="<%= request.getContextPath() %>/recuperaFoto?idUtenteRichiesto=<%= richieste.get(i).getUtenteRichiedente().getId() %>" 
     style="width:50px; height:50px; border-radius:50%; object-fit: cover;"
     onerror="this.src='<%= request.getContextPath() %>/immagini/default-avatar.png';"
     alt="Avatar">
					    <p class="nome-utente"><%= request.getAttribute("nomeutente"+i) %></p>
					</div>
					<p style="font-weight:bold; color:<%= request.getAttribute("coloreStato"+i) %>">
						Stato richiesta: <%= request.getAttribute("statoRichiesta"+i) %>
					</p>
					<p>Task: <%= request.getAttribute("task"+i) %></p>
					<p>In data: <%= request.getAttribute("data"+i) %></p>
					<p>Ore: <%= request.getAttribute("orario"+i) %></p>
					<p>Veicolo richiesto: <%= request.getAttribute("veicolo"+i) %></p>
					<p>Indirizzo: <%= request.getAttribute("indirizzo"+i) %></p>
					<p>Retribuzione: €<%= request.getAttribute("costoeffettivo"+i) %></p>
					<div class="description-container">
						<a href="" class="see-more">Vedi descrizione↓</a>
						<p class="description" style="display:none">
							<%= richieste.get(i).getDescrizione() %>
						</p>
					</div>
				</div>
			<% }
			} %>
		<div style="display:<%= displayValue2 %>">
			<p>Nessuna richiesta completata.</p>
		</div>
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