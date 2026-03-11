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
	<div><a href="?offSet=<%=offSet-1%>">Indietro</a><a href="?offSet=<%=offSet+1%>">Avanti</a></div>
	
	
	<div class="Giorno">Lunedì
	<%=LunediDellaSettimana.getDayOfMonth() %>
	</div>
		<div class="Giorno">Martedì
		<%=LunediDellaSettimana.plusDays(1).getDayOfMonth() %></div>
		<div class="Giorno">Mercoledì
		<%=LunediDellaSettimana.plusDays(2).getDayOfMonth() %></div>
		<div class="Giorno">Giovedì
		<%=LunediDellaSettimana.plusDays(3).getDayOfMonth() %></div>
		<div class="Giorno">Venerdì
		<%=LunediDellaSettimana.plusDays(4).getDayOfMonth() %></div>
		<div class="Giorno">Sabato
		<%=LunediDellaSettimana.plusDays(5).getDayOfMonth() %></div>
		<div class="Giorno">Domenica
		<%=LunediDellaSettimana.plusDays(6).getDayOfMonth() %></div>
	
	</div>
	
	
	
	
	
	
	
	
	
	
</body>
</html>