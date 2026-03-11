<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Registrazione Utente - Taskly</title>
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/css/Registrazioni.css?v=1.1">
</head>
<body>
	<img src="<%= request.getContextPath() %>/PagineWeb/immagini/logo.png"
		alt="Taskly Logo" class="logo-top-left">

	<div class="registrazione-container">
		<h1>Crea il tuo account Utente</h1>
		<p>Unisciti alla nostra community per accedere ai servizi.</p>

<% 
		    List<String> listaErrori = (List<String>) request.getAttribute("listaErrori");
		    if (listaErrori != null && !listaErrori.isEmpty()) { 
		%>
		    <div class="boxErrori">
		        <strong class="titoloErrore">Attenzione, controlla questi campi:</strong>
		        <ul class="ListaErrori">
		            <% for (String err : listaErrori) { %>
		                <li><%= err %></li>
		            <% } %>
		        </ul>
		    </div>
		<% 
		    } 
		%>


		<form action="<%= request.getContextPath() %>/RegistrazioneUtenteServlet" method="POST">
			<input type="hidden" name="tipoUtente" value="CLIENTE">

			<div class="input-dati">
				<input type="text" id="Nome" name="nome" placeholder="Nome"
					minlength="2" required>
			</div>

			<div class="input-dati">
				<input type="text" id="Cognome" name="cognome" placeholder="Cognome"
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
				<input type="tel" id="telefono" name="telefono"
					placeholder="Numero di Telefono" pattern="[0-9]{8,15}" required>
			</div>

			<div class="input-dati">
				<input type="email" id="email" name="email"
					placeholder="Email (esempio@mail.com)" required>
			</div>

			<div class="input-dati">
				<input type="password" id="password" name="password"
					placeholder="Password (min. 8 caratteri)" minlength="8" required
					title="La password deve  essere almeno di 8 caratteri">
			</div>

			<div class="input-row">
				<div class="input-citta">
					<input type="text" id="citta" name="citta" placeholder="Città"
						required>
				</div>
				<div class="input-province">
					<input type="text" id="provincia" name="provincia" placeholder="PR"
						maxlength="2" required>
				</div>
			</div>

			<button type="submit" class="btn-submit">Registrati</button>
		</form>

		<hr>

		<div class="collegamenti-links">
		<p>
    Hai già un account? <a href="<%= request.getContextPath() %>/loginUtente.jsp">Accedi qui</a>
	</p>
	<p>
    Sei un professionista? <a href="<%= request.getContextPath() %>/PagineWeb/registrazioneProfessionista.jsp">Registrati come Professionista</a>
	</p>
		</div>
	</div>
</body>
</html>