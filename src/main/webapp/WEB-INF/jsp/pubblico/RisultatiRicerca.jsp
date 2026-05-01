<%@page import="java.util.Map"%>
<%@page import="org.elis.progetto.model.Utente"%>
<%@page import="org.elis.progetto.model.Citta"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Risultati per ${query}</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/Homepage.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/risultati.css">
</head>
<body>
	<%@ include file="/WEB-INF/headerFooter/header.jsp"%>

	<%
        List<Utente> professionisti = (List<Utente>) request.getAttribute("professionisti");
        List<Citta> listaCitta = (List<Citta>) request.getAttribute("listaCitta");
        Map<Long,Double> media = (Map<Long,Double>) request.getAttribute("mediaProfessionista");
        String query = (String) request.getAttribute("query");
        String msg = (String) request.getAttribute("messaggioFiltro");
        if (msg == null) msg = "in tutta Italia";
    %>

	<div class="results-container">
		<div class="results-header">
			<h2>
				<% if (msg.equals("in tutta Italia")) { %>
				Ci sono <strong><%= (professionisti != null) ? professionisti.size()+" risultati di ricerca per" : 0+"  risultati di ricerca per" %></strong>
				<span><%= (query != null) ? query : "" %></span> in tutta Italia
				<% } else { %>
				Ci sono <strong><%= (professionisti != null) ? professionisti.size()+" risultati di ricerca per" : 0+"  risultati di ricerca per" %></strong>
				<span><%= (query != null) ?  query : "" %></span>
				<%= msg %>
				<% } %>
			</h2>


			<p class="search-context">
				<% if (msg != null && !msg.equals("in tutta Italia")) { %>
				Risultati in base alla tua posizione o alla zona scelta.
				<% } else { %>
				<% } %>
			</p>
		</div>
		<div class="refinement-bar">
			<form
				action="<%=request.getContextPath()%>/RicercaProfessionistiServlet"
				method="GET" class="filter-form">
				<input type="hidden" name="professione"
					value="<%= (query != null) ? query : "" %>"> <label
					for="comuneScelto">Cambia zona di ricerca:</label> <input
					type="text" name="nomeCittaCambiata" id="comuneScelto"
					list="suggerimentiCitta" placeholder="Inserisci un comune"
					class="input-comune">

				<datalist id="suggerimentiCitta">
					<% if(listaCitta != null) {
        for(Citta c : listaCitta) { %>
					<option value="<%= c.getNome() %> (<%= c.getProvincia() %>)"></option>
					<%  }
    } %>
				</datalist>
				<button type="submit" class="btn-update">Aggiorna</button>
				<% if (msg != null && !msg.equals("in tutta Italia")) { %>
				<a
					href="<%=request.getContextPath()%>/RicercaProfessionistiServlet?professione=<%= (query != null) ? query : "" %>&action=reset"
					class="btn-reset">Ricerca in Tutta Italia</a>
				<% } %>
			</form>
		</div>
		<div class="services-section">
			<div class="services-grid">
				<%
                if (professionisti != null && !professionisti.isEmpty()) {
                    for (Utente utente : professionisti) {
                %>
				<div class="service-card">
					<div class="card-text">
						<h3><%= utente.getNome() + " " + utente.getCognome() %></h3>
						<%if (media.get(utente.getId()) != null) { %><p>Media valutazioni: <%= media.get(utente.getId()) %> ⭐</p><% }else{ %>
						<p>Nessuna valutazione</p><% } %>
						<% if (utente.getCitta() != null && utente.getCitta().getNome() != null && 
							       !utente.getCitta().getNome().isEmpty()) { %>
						<p class="loc-tag">
							📍
							<%= utente.getCitta().getNome() %></p>
						<% } %>

						<p>
							Email:
							<%= utente.getEmail() %></p>
						<form action="<%=request.getContextPath()%>/ProfiloProfessionistaServlet" method="GET">
							<input type="hidden" name="idProfessionista" value="<%= utente.getId() %>">
							<input type="submit" value="Mostra il profilo" class="view-more">
						</form>
					</div>
				</div>
				<%
                    }
                }
                %>

				<%--             <c:forEach var="pro" items="${professionisti}">
                <div class="service-card">
                    <div class="card-text">
                        <h3>${pro.nome} ${pro.cognome}</h3>
                        <p>Email: ${pro.email}</p>
                        <a href="#" class="view-more">Contatta</a>
                    </div>
                </div>
            </c:forEach> --%>
			</div>

			<%if(professionisti == null||professionisti.isEmpty()){ %>

			<p style="text-align: center; margin-top: 50px;">Nessun
				professionista trovato con questa specializzazione.</p>
			<% } %>
		</div>
	</div>
	<%@ include file="/WEB-INF/headerFooter/footer.jsp"%>
</body>
</html>