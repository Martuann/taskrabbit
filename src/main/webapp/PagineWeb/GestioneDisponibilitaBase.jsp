<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Locale"%>
<%@page import="java.time.format.TextStyle"%>
<%@page import="java.time.DayOfWeek"%>
<%@page import="org.elis.progetto.model.OrarioBase"%>
<%@page import="org.elis.progetto.model.Utente"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Taskly - Imposta Orario Standard</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/styleGestioneEServizi.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/GestioneDisponibilita.css">
</head>
<body>
	<header>
		<% String context = request.getContextPath(); %>
		<a href="<%=context%>/HomeServlet"><img src="<%=context%>/img/taskly_logo.png" class="logo-img"></a>
		<nav>
			<a href="<%=context%>/HomeServlet">Home</a> > <a href="<%=context%>/GestioneServiziServlet">Gestione servizi</a> > <strong>Orario Standard</strong>
		</nav>
	</header>

	<div class="container">
		<% if("true".equals(request.getParameter("success"))) { %>
			<div class="alert-success">Orario standard aggiornato con successo!</div>
		<% } %>

		<% if(request.getParameter("error") != null) { %>
			<div class="alert-error">
				<% 
					String errorType = request.getParameter("error");
					if("loading".equals(errorType)) out.print("Errore nel caricamento dati.");
					else if("save".equals(errorType)) out.print("Errore durante il salvataggio.");
					else out.print("Si è verificato un errore.");
				%>
			</div>
		<% } %>

		<form action="<%=request.getContextPath()%>/GestioneOrariDefault" method="POST">
			<%
			List<OrarioBase> orariSalvati = (List<OrarioBase>) request.getAttribute("orarioDefault");  
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

			for(int i = 0; i < DayOfWeek.values().length; i++) { 
				DayOfWeek giornoCorrente = DayOfWeek.values()[i];
				int numeroGiorno = giornoCorrente.getValue();
				
				OrarioBase orarioTrovato = null;
				if (orariSalvati != null) {
					for (OrarioBase ob : orariSalvati) {
						if (ob.getGiornoSettimana() == giornoCorrente) {
							orarioTrovato = ob;
							break;
						}
					}
				}
				
				boolean lavoraOggi = (orarioTrovato != null);
				String inizio = lavoraOggi ? orarioTrovato.getOraInizio().format(formatter) : "09:00";
				String fine = lavoraOggi ? orarioTrovato.getOraFine().format(formatter) : "18:00";
			%>
				<div class="riga-giorno">
					<strong><%= giornoCorrente.getDisplayName(TextStyle.FULL, Locale.ITALIAN) %></strong>
					
					<input type="hidden" name="giorno_<%=i%>" value="<%= numeroGiorno %>">
					
					<input type="checkbox" class="toggle-lavoro" name="lavora_<%=i%>" value="true" <%= lavoraOggi ? "checked" : "" %>> 
					
					<span class="testo-stato disponibile">Disponibile</span>
					<span class="testo-stato non-disponibile">Non disponibile</span>

					<div class="controlli-orario">
						<label class="label-orario">
							Dalle: <input type="time" name="oraInizio_<%=i%>" value="<%= inizio %>">
						</label>
						<label class="label-orario">
							Alle: <input type="time" name="oraFine_<%=i%>" value="<%= fine %>">
						</label>
					</div>
				</div>
			<% } %>

			<button type="submit" class="btn-salva">Salva Orario</button>
		</form>
	</div>
</body>
</html>