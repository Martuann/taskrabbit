<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.elis.progetto.model.Utente"%>
<%@ page import="org.elis.progetto.model.Ruolo"%>


<head>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css?v=<%=System.currentTimeMillis()%>">
</head>
<header class="admin-header">
	<div class="header-inner">
		<div class="logo">
			<a href="<%=request.getContextPath()%>/HomepageServlet"> 
                <img src="<%=request.getContextPath()%>/immagini/logo.png" alt="Taskly Logo" class="logo-img">
			</a>
		</div>

		<nav class="nav-menu">
			<%
			Utente u = (Utente) session.getAttribute("utenteLoggato");
			if (u != null && u.getRuolo() == Ruolo.ADMIN) {
			%>
                <a href="#">Dashboard</a> 
                <a href="#">Utenti</a> 
                <a href="#">Report</a>
			<% } %>
		</nav>
        
		<div class="user-section">
			<%
			if (u != null) {
			%>
			<div class="links-section">
                
                <% if (u.getRuolo() == Ruolo.ADMIN) { %>
                    <a href="<%= request.getContextPath() %>/AggiungiProfessione">Gestione Professioni</a>
                    <a href="<%= request.getContextPath() %>/AggiungiCittaServlet">Gestione Città</a>
                    <a href="<%= request.getContextPath() %>/AggiungiVeicolo">Gestione Veicoli</a>
                <% } %>
                
<% if (u.getRuolo() == Ruolo.PROFESSIONISTA) { 
    Boolean notifica = (Boolean)session.getAttribute("alertTariffa");
%>
<% if (notifica != null && notifica) { %>
        <div class="notification-wrapper">
            <button id="noti-btn" class="noti-button" title="Ci sono avvisi">
                ⚠️
            </button>

            <div id="noti-dropdown" class="dropdown-content">
                <div class="dropdown-header">Notifiche</div>
                <ul class="noti-list">
                    <li>Attenzione: non hai impostato la tariffa per le tue professioni.</li> 
                </ul>
            </div>
        </div>
    <% } %>
    <a href="<%= request.getContextPath() %>/GestioneServiziServlet">Gestione Servizi</a>
<% } %>


                
                <% 
                if (u.getRuolo() != Ruolo.ADMIN) { 
                    String taskAnchor = (u.getRuolo() == Ruolo.UTENTE_BASE) ? request.getContextPath() + "/CronologiaRichiesteServlet" : "#";
				    taskAnchor = (u.getRuolo() == Ruolo.PROFESSIONISTA) ? request.getContextPath() + "/GestioneRichiesteServlet" : taskAnchor; 
                %>
				    <a href="<%= taskAnchor %>">Le mie task</a>
                <% } %>
			</div>
            
			<div class="area-riservata-container">
				<button class="area-riservata-btn" id="areaRiservataBtn">
					Bentornato, <strong><%=u.getNome()%></strong> ▼
				</button>

				<div class="area-riservata-menu" id="areaRiservataMenu">
					<a href="<%=request.getContextPath()%>/Profilo">Mio Profilo</a>
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
					<span class="slash">/</span> 
                    <a href="<%=request.getContextPath()%>/RegistrazioneUtenteServlet" class="registrati bold">Registrati</a>
				</div>
				<a href="<%=request.getContextPath()%>/RegistrazioneProfessionistaServlet" class="tasker-btn">Diventa un tasker</a>
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


			document.addEventListener('click', function(e) {

				if (!btn.contains(e.target) && !menu.contains(e.target)) {
					menu.classList.remove('show');
				}
			});
		}
	});
	
	
	
	
	
	
	const nnbtn = document.getElementById('noti-btn');
	const dropdown = document.getElementById('noti-dropdown');

	// 1. Mostra/Nascondi al click sul bottone
	nnbtn.addEventListener('click', (e) => {
	  e.stopPropagation(); // Evita che il click arrivi alla window
	  dropdown.classList.toggle('show');
	});

	// 2. Chiudi se l'utente clicca ovunque fuori dal menu
	window.addEventListener('click', () => {
	  if (dropdown.classList.contains('show')) {
	    dropdown.classList.remove('show');
	  }
	});

	// 3. Evita che il click dentro il menu lo chiuda per errore
	dropdown.addEventListener('click', (e) => {
	  e.stopPropagation();
	});
	
	
	
	
	
	
	
	
	
</script>