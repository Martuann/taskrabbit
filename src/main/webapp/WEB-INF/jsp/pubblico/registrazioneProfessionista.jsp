<%@page import="org.elis.progetto.model.Citta"%>
<%@page import="org.elis.progetto.model.Professione"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Registrazione Professionista - Taskly</title>
<link
	href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css"
	rel="stylesheet" />

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/Registrazioni.css?">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/footer.css?">
<%@ include file="/WEB-INF/headerFooter/header.jsp"%>

</head>
<body class="site-wrapper">

	<main class="content-wrapper">

	<div class="registrazione-container">
		<h1>Crea il tuo account Professionista</h1>
		<p>Unisciti alla nostra rete di esperti.</p>


		<%
		List<String> listaErrori = (List<String>) request.getAttribute("listaErrori");
		if (listaErrori != null && !listaErrori.isEmpty()) {
		%>
		<div class="boxErrori">
			<strong class="titoloErrore">Attenzione, controlla questi
				campi:</strong>
			<ul class="ListaErrori">
				<%
				for (String err : listaErrori) {
				%>
				<li><%=err%></li>
				<%
				}
				%>
			</ul>
		</div>
		<%
		}
		%>

		<form
			action="<%=request.getContextPath()%>/RegistrazioneProfessionistaServlet"
			method="POST">
			<input type="hidden" name="tipoProfessionista" value="PROFESSIONISTA">

			<div class="input-dati">
				<input type="text" id="nome" name="nome" placeholder="Nome"
					minlength="2" required>
			</div>

			<div class="input-dati">
				<input type="text" id="cognome" name="cognome" placeholder="Cognome"
					minlength="2" required>
			</div>

			<div class="input-dati">
				<label for="dataNascita"
					style="font-size: 12px; color: #666; margin-left: 5px; margin-bottom: 5px;">Data
					di Nascita:</label> <input type="date" id="dataNascita" name="dataNascita"
					required>
			</div>
			<div class="input-dati">
				<input type="text" id="codiceFiscale" name="codiceFiscale"
					placeholder="Codice Fiscale" minlength="16" maxlength="16"
					style="text-transform: uppercase;" required>
			</div>

			<div class="input-dati">

				<div class="input-dati">
    <label style="font-size: 12px; color: #666; margin-left: 5px; margin-bottom: 5px;"></label>
    <select class="selettoreprofessioni" name="professione" multiple="multiple" style="width: 100%;">
        </select>
</div>
					<%
					List<Professione> listaProfessioni = (List<Professione>) request.getAttribute("listaProfessioni");
					List<Citta> listaCitta = (List<Citta>) request.getAttribute("listaCitta");

					if (listaProfessioni != null && listaProfessioni.size() > 0) {
						for (int i = 0; i < listaProfessioni.size(); i++) {
					%>
					<option value="<%=listaProfessioni.get(i).getId()%>">
						<%=listaProfessioni.get(i).getNome()%></option>




					<%
					}
					}
					%>
				</select>






			</div>

			<div class="input-dati">
				<input type="tel" id="telefono" name="telefono"
					placeholder="Numero di Telefono" pattern="[0-9]{8,15}" required>
			</div>

			<div class="input-dati">
				<input type="email" id="email" name="email"
					placeholder="Email (esempio@mail.com)" required>
			</div>

			<div class="input-dati">
				<input type="password" id="password" name="password"
					placeholder="Password (min. 8 caratteri)" minlength="8" required>
			</div>


			<div class="input-row">
			<div class="input-citta-select">
    <label for="id_citta"></label>
    <select name="id_citta" id="id_citta" required>
        <option value="" disabled selected>Scegli una città...</option>
        
        <% 
            
            if (listaCitta != null) {
                for (Citta c : listaCitta) {
        %>
            <option value="<%= c.getId() %>">
                <%= c.getNome() %> (<%= c.getProvincia() %>)
            </option>
        <% 
                } 
            } 
        %>
    </select>
</div>
			</div>

			<button type="submit" class="btn-submit">Registrati</button>
		</form>

		<hr>

		<div class="collegamenti-links">
			<p>
					Hai già un account? <a href="<%=request.getContextPath()%>/Login">Accedi
						qui</a>
				</p>
			<p>
					Sei un Utente? <a
						href="<%=request.getContextPath()%>/RegistrazioneUtenteServlet">Registrati
						come Utente</a>
				</p>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

	<script
		src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

	<script>
		$(document).ready(function() {
			$('.selettoreprofessioni').select2({
				placeholder : "Seleziona le tue specializzazioni",
				allowClear : true
			});
		});
	</script>
	</main>
	<%@ include file="/WEB-INF/headerFooter/footer.jsp"%>
</body>
</html>