<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="org.elis.progetto.model.Professione"%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Taskly - Servizi per la casa</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/Homepage.css">

</head>
<body>
	<%@ include file="/WEB-INF/headerFooter/header.jsp"%>
	<% List<Professione> professioni = (List<Professione>) request.getAttribute("professioni"); %>

	<header class="hero">
		<div class="hero-content">
			<h1>Benvenuto su Taskly</h1>
			<p>I tuoi progetti di casa resi semplici. Trova esperti per ogni
				necessità.</p>
			<div class="search-container">
				<form
					action="<%=request.getContextPath()%>/RicercaProfessionistiServlet"
					method="GET" style="display: flex; width: 100%;">
					<input type="text" list="listaProfessioni" name="professione"
						placeholder="Di cosa hai bisogno? (es. Imbiancatura, Riparazioni)"
						required>
					<datalist id="listaProfessioni">
						<%
						if (professioni != null) {
                            for(Professione p : professioni) {
                        %>
						<option value="<%= p.getNome() %>">
							<%
                            }
                        }
                        %>

					</datalist>
					<button type="submit">Cerca</button>
				</form>
			</div>
		</div>
	</header>

	<section class="services-section">
		<h2>Esplora i nostri servizi</h2>
		<div class="services-grid">

			<div class="service-card">
				<img src="<%=request.getContextPath()%>/immagini/Elettricista.png"
					alt="Elettronica">
				<div class="card-text">
					<h3>Elettronica</h3>
					<p>La tua casa, alla massima potenza.</p>
					<form action="<%=request.getContextPath()%>/RicercaProfessionistiServlet" method="GET">
						<button type="submit" class="view-more" name="professione" value="Elettricista">Vedi Tasker</button>
					</form>
				</div>
			</div>

			<div class="service-card pittura-card">
				<img src="<%=request.getContextPath()%>/immagini/Idraulico.png"
					alt="Idraulica">
				<div class="card-text">
					<h3>Idraulica</h3>
					<p>Idraulica perfetta, zero pensieri.</p>
					<form action="<%=request.getContextPath()%>/RicercaProfessionistiServlet" method="GET">
						<button type="submit" class="view-more" name="professione" value="Idraulico">Vedi Tasker</button>
					</form>
				</div>
			</div>

			<div class="service-card">
				<img src="<%=request.getContextPath()%>/immagini/Imbianchino.png"
					alt="imbianchino">
				<div class="card-text">
					<h3>Imbiancatura</h3>
					<p>Rinfresca le pareti della tua casa.</p>
					<form action="<%=request.getContextPath()%>/RicercaProfessionistiServlet" method="GET">
						<button type="submit" class="view-more" name="professione" value="Imbiancatura">Vedi Tasker</button>
					</form>
				</div>
			</div>

			<div class="service-card">
				<img
					src="<%=request.getContextPath()%>/immagini/AssemblaggioPc.png"
					alt="AssemblaggioPc">
				<div class="card-text">
					<h3>Aiuto Informatico</h3>
					<p>Riparazione PC e installazione software.</p>
					<form action="<%=request.getContextPath()%>/RicercaProfessionistiServlet" method="GET">
						<button type="submit" class="view-more" name="professione" value="Assemblaggio PC"   >Vedi Tasker</button>
					</form>
				</div>
			</div>
		</div>
	</section>
	<%@ include file="/WEB-INF/headerFooter/footer.jsp"%>

</body>
</html>