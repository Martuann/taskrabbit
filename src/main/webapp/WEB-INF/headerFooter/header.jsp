<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.elis.progetto.model.Utente"%>
<%@ page import="org.elis.progetto.model.Ruolo"%>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css?v=<%=System.currentTimeMillis()%>">

<header class="admin-header">
	<div class="header-inner">
		<div class="logo">
			<a href="<%=request.getContextPath()%>/HomepageServlet">
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
			<a href="<%=request.getContextPath()%>/HomepageServlet">Servizi</a>
			<%
			}
			%>
		</nav>
		<div class="user-section">
			<%
			if (u != null) {
			%>
			<div class="prof-only-section">
				<a style="display:<%= (u.getRuolo()==Ruolo.PROFESSIONISTA) ? "inline-block" : "none" %>" 
				   href="<%= request.getContextPath() %>/GestioneServiziServlet"
				   class="servizi">
				   Gestione Servizi
				</a>
			</div>
			<div class="task-section">
				<% String taskAnchor = (u.getRuolo()==Ruolo.UTENTE_BASE) ? request.getContextPath()+"/CronologiaRichiesteServlet" : "#";
				taskAnchor = (u.getRuolo()==Ruolo.PROFESSIONISTA) ? request.getContextPath()+"/GestioneRichiesteServlet" : taskAnchor; %>
				<a style="display:<%= (u.getRuolo()!=Ruolo.ADMIN) ? "inline-block" : "none" %>" 
				   href="<%= taskAnchor %>"
				   class="tasks">
				   Le mie task
				</a>
			</div>
			<div class="area-riservata-container">
				<button class="area-riservata-btn" id="areaRiservataBtn">
					Bentornato, <strong><%=u.getNome()%></strong> ▼
				</button>

				<div class="area-riservata-menu" id="areaRiservataMenu">
					<a href="<%=request.getContextPath()%>/Profilo">Mio Profilo</a> <a
						href="<%=request.getContextPath()%>/Impostazioni">Impostazioni</a>
					<div class="divider"></div>
					<a href="<%=request.getContextPath()%>/Logout" class="logout">Esci</a>
				</div>
			</div>
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

<script>
	document.addEventListener('DOMContentLoaded', function() {
		const btn = document.getElementById('areaRiservataBtn');
		const menu = document.getElementById('areaRiservataMenu');

		if (btn && menu) {
			btn.addEventListener('click', function(e) {
				e.stopPropagation();
				menu.classList.toggle('show');
			});

			// Chiude il menu se clicchi fuori
			document.addEventListener('click', function(e) {
				if (!btn.contains(e.target)) {
					menu.classList.remove('show');
				}
			});
		}
	});
</script>