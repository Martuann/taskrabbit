<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.elis.progetto.model.Utente"%>
<%@ page import="org.elis.progetto.model.Ruolo"%>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css?v=<%=System.currentTimeMillis()%>">

<header class="admin-header">
	<div class="header-inner">
		<div class="logo">
			<a href="<%=request.getContextPath()%>/PagineWeb/Homepage.jsp">
				<img
				src="<%=request.getContextPath()%>/PagineWeb/immagini/logo.png"
				alt="Taskly Logo" class="logo-img">
			</a>
		</div>

		<nav class="nav-menu">
			<%
			Utente u = (Utente) session.getAttribute("utenteLoggato");
			if (u != null && u.getRuolo() == Ruolo.ADMIN) {
			%>
			<a href="<%=request.getContextPath()%>/Dashboard">Dashboard</a> <a
				href="#">Utenti</a> <a href="#">Report</a>
			<%
			} else {
			%>
			<a href="<%=request.getContextPath()%>/Homepage">Servizi</a>
			<%
			}
			%>
		</nav>

		<div class="user-section">
			<%
			if (u != null) {
			%>
			<span class="welcome-msg">Bentornato, <strong><%=u.getNome()%></strong></span>
			<a href="<%=request.getContextPath()%>/Logout" class="logout-link">Disconnetti</a>
			<%
			} else {
			%>
			<div class="links-container">
				<div class="links-group">
					<a href="<%=request.getContextPath()%>/Login" class="login bold">Accedi</a>
					<span class="slash">/</span> <a
						href="<%=request.getContextPath()%>/RegistrazioneUtenteServlet"
						class="registrati bold">Registrati</a>
				</div>
				<a
					href="<%=request.getContextPath()%>/RegistrazioneProfessionistaServlet"
					class="tasker-btn">Diventa un tasker</a>
			</div>
			<%
			}
			%>
		</div>
	</div>

</header>

