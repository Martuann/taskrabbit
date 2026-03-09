<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Registrazione Professionista - Taskly</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/PagineWeb/css/Style.css">
</head>
<body>
	<img src="${pageContext.request.contextPath}/PagineWeb/immaginig/logo.png"
		alt="Taskly Logo" class="logo-top-left">

	<div class="registrazione-container">
		<h1>Crea il tuo account Professionista</h1>
		<p>Unisciti alla nostra rete di esperti.</p>

		<form action="RegistrazioneProfessionistaServlet" method="POST">
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
				<select name="professione" required class="custom-select">
					<option value="" disabled selected>Seleziona la tua
						specializzazione</option>
					<option value="IDRAULICO">Idraulico</option>
					<option value="ELETTRICISTA">Elettricista</option>
					<option value="MURATORE">Muratore</option>
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
				Hai già un account? <a href="loginUtente.jsp">Accedi qui</a>
			</p>
			<p>
				Sei un Utente? <a href="registrazioneUtente.jsp">Registrati come
					Utente</a>
			</p>
		</div>
	</div>
</body>
</html>