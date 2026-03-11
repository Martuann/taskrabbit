<%@page import="java.time.temporal.TemporalAdjusters"%>
<%@page import="java.time.DayOfWeek"%>
<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Taskly - Gestione Disponibilità</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/styleGestioneEServizi.css">
	</head>
<body>
<header>
<%String context= request.getContextPath(); %>
		<a href="<%=context%>/HomeServlet"><img
			src="<%=context%>/img/taskly_logo.png" class="logo-img"></a>
		<nav>
			<a href="<%=context%>/HomeServlet">Home</a> > <a href="<%=context%>/GestioneServiziServlet">Gestione servizi</a> > <strong>Agenda</strong>
		</nav>
	</header>
	<% 
	int offSet = 0; 

	String offSetTestuale = request.getParameter("offSet");

	if (offSetTestuale != null) {
	    offSet = Integer.parseInt(offSetTestuale);
	    
	  
	    
	}
	  LocalDate giornoOdierno = LocalDate.now();
			LocalDate LunediDellaSettimana=	giornoOdierno
			        .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
			        .plusWeeks(offSet);
	%>
<div class="container">
		<div class="navigazione-settimana">
			<a href="?offSet=<%=offSet-1%>" class="btn-nav">⬅️ Settimana Precedente</a>
			<span class="testo-settimana">Settimana del <%= LunediDellaSettimana.getDayOfMonth() %></span>
			<a href="?offSet=<%=offSet+1%>" class="btn-nav">Settimana Successiva ➡️</a>
		</div>

		<form action="<%=request.getContextPath()%>/SalvaDisponibilitaServlet" method="POST">
			
			<div class="lista-giorni">
			<% 
			// Array per stampare i nomi dei giorni in italiano
			String[] nomiGiorni = {"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"};
			
			for(int i = 0; i < 7; i++) {
				LocalDate giornoCorrente = LunediDellaSettimana.plusDays(i);
			%>
				<div class="riga-giorno">
					
					<div class="info-giorno">
						<strong><%= nomiGiorni[i] %></strong><br>
						<span class="data-giorno"><%= giornoCorrente.getDayOfMonth() %>/<%= giornoCorrente.getMonthValue() %>/<%= giornoCorrente.getYear() %></span>
						<input type="hidden" name="data_<%=i%>" value="<%=giornoCorrente%>">
					</div>

					<div class="input-orari">
						<label class="label-checkbox">
							<input type="checkbox" name="lavora_<%=i%>" value="true" checked> Disponibile
						</label>
						
						<label class="label-orario">Dalle: 
							<input type="time" name="oraInizio_<%=i%>" value="09:00" required>
						</label>
						
						<label class="label-orario">Alle: 
							<input type="time" name="oraFine_<%=i%>" value="18:00" required>
						</label>
					</div>
				</div>
			<% } %>
			</div>
			
			<div class="contenitore-bottone">
				<button type="submit" class="btn-submit btn-salva">Salva Orari Settimana</button>
			</div>
		</form>
	</div>
	
	
	
	
	
	
	
	
</body>
</html>