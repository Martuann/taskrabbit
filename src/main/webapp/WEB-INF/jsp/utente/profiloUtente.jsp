<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="org.elis.progetto.model.Utente, org.elis.progetto.model.Citta"%>

<%@ page import="java.util.List, java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>Mio Profilo - Taskly</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/profiloUtente.css?v=<%=System.currentTimeMillis()%>">
</head>
<body>
	<%@ include file="/WEB-INF/headerFooter/header.jsp"%>

	<%
	Utente utenteProfilo = (Utente) session.getAttribute("utenteLoggato");
	Citta cittaUtente = (Citta) request.getAttribute("cittaDati");

	if (utenteProfilo == null) {
		response.sendRedirect(request.getContextPath() + "/Login");
		return;
	}
	%>

	<div class="containter">

		<div class="back-navigation">
			<a href="javascript:void(0);" onclick="window.history.back();"
				class="back-link"> <span class="arrow">&larr;</span> Indietro
			</a>
		</div>

	<div class="profile-header">
			<div class="avatar-wrapper">
				<img id="avatarImg" class="avatar-circle-img" 
			
					 src="<%= request.getContextPath() %>/recuperaFoto" 
					 style="width: 120px; height: 120px; border-radius: 50%; object-fit: cover;"
					 onerror="this.src='<%= request.getContextPath() %>/immagini/default-avatar.png';"
					 alt="Avatar">
				
				<form action="<%=request.getContextPath()%>/CaricaFotoProfilo" method="post" enctype="multipart/form-data" class="upload-avatar-form">
					<label for="fileFoto" class="btn-cambia-foto">Cambia Foto</label>
					<input type="file" id="fileFoto" name="foto" accept="image/*" style="display:none;" onchange="this.form.submit()">
				</form>
				</div>
				</div>
				<form id="profileForm"
		action="<%=request.getContextPath()%>/AggiornaProfilo" method="post"
		class="profilo-container">
			
			<h1>I miei Dati</h1>

		<div class="info-grid">
			<div class="info-box">
				<label>Nome</label> <input type="text" name="nome"
					value="<%=utenteProfilo.getNome()%>" class="view-mode" readonly>
			</div>
			<div class="info-box">
				<label>Cognome</label> <input type="text" name="cognome"
					value="<%=utenteProfilo.getCognome()%>" class="view-mode" readonly>
			</div>
			<div class="info-box full-width">
				<label>Email</label> <input type="email" name="email"
					value="<%=utenteProfilo.getEmail()%>" class="view-mode" readonly>
			</div>
			<div class="info-box">
				<label>Data di Nascita</label> <input type="date" name="ddn"
					value="<%=utenteProfilo.getDdn()%>" class="view-mode" readonly>
			</div>
			<div class="info-box">
				<label>Telefono</label> <input type="tel" name="telefono"
					value="<%=(utenteProfilo.getTelefono() != null) ? utenteProfilo.getTelefono() : ""%>"
					class="view-mode" readonly>
			</div>
			<div class="info-box full-width">
				<label>Residenza (Città e Provincia)</label> <select name="idCitta"
					id="cittaSelect" class="view-mode" disabled>
					<%

            List<Citta> listaCitta = (List<Citta>) request.getAttribute("listaCitta");


            if (listaCitta != null) {
                for (Citta c : listaCitta) {

                	String selected = (utenteProfilo.getCitta() != null && 
                            utenteProfilo.getCitta().getId().equals(c.getId())) ? "selected" : "";
        %>
					<option value="<%= c.getId() %>" <%= selected %>>
						<%= c.getNome() %> (<%= c.getProvincia() %>)
					</option>
					<%
                }
            } else {
        %>
					<option value="">Nessuna città caricata</option>
					<% } %>
				</select>
			</div>
			<div class="info-box full-width">
				<label>Codice Fiscale</label>
				<p class="cf-text" style="text-transform: uppercase;"><%=utenteProfilo.getCf()%></p>
			</div>
		</div>

		<div class="password-wrapper">
			<button type="button" id="showPwBtn" class="btn-password-toggle"
				onclick="togglePasswordSection()">Modifica Password</button>

			<div id="passwordSection" style="display: none; margin-top: 20px;">
				<div class="info-grid">
					<div class="info-box">
						<label>Nuova Password</label> <input type="password"
							name="nuovaPassword" id="nuovaPw" class="edit-mode">
					</div>
					<div class="info-box">
						<label>Conferma Password</label> <input type="password"
							name="confermaPassword" id="confermaPw" class="edit-mode">
					</div>
				</div>
				<p id="pwError"
					style="color: #d63333; font-size: 13px; display: none; margin-top: 10px; font-weight: bold;">Le
					password non coincidono!</p>
			</div>
		</div>

		<div class="actions">
			<button type="button" id="editBtn" class="btn-edit"
				onclick="enableEdit()">Modifica Profilo</button>
			<div id="saveActions" style="display: none;">
				<button type="submit" class="btn-save">Salva Modifiche</button>
				<button type="button" class="btn-cancel"
					onclick="window.location.reload()">Annulla</button>
			</div>
		</div>
	</form> </div>

	<script>
	function togglePasswordSection() {
		const section = document.getElementById('passwordSection');
		const btn = document.getElementById('showPwBtn');
		const editBtn = document.getElementById('editBtn');

		if (section.style.display === 'none') {
			section.style.display = 'block';
			btn.innerText = 'Annulla cambio password';
			editBtn.style.display = 'none'; // Nasconde modifica dati se si cambia pw
			document.getElementById('saveActions').style.display = 'block';
		} else {
			section.style.display = 'none';
			btn.innerText = 'Modifica Password';
			editBtn.style.display = 'inline-block';
			document.getElementById('saveActions').style.display = 'none';
			document.getElementById('nuovaPw').value = '';
			document.getElementById('confermaPw').value = '';
			document.getElementById('pwError').style.display = 'none';
		}
	}

	function enableEdit() {
	    document.getElementById('nuovaPw').value = '';
	    document.getElementById('confermaPw').value = '';
	    document.getElementById('pwError').style.display = 'none'; 
	    
	    document.querySelector('.back-navigation').style.visibility = 'hidden';
	    document.querySelector('.password-wrapper').style.display = 'none';

	    const selectCitta = document.getElementById('cittaSelect');
	    selectCitta.removeAttribute('disabled');
	    selectCitta.classList.remove('view-mode');
	    selectCitta.classList.add('edit-mode');

	    const inputs = document.querySelectorAll('#profileForm input:not([type="hidden"]):not([type="password"])');
	    inputs.forEach(input => {
	        input.removeAttribute('readonly');
	        input.classList.remove('view-mode');
	        input.classList.add('edit-mode');
	    });
	    
	    document.getElementById('editBtn').style.display = 'none';
	    document.getElementById('saveActions').style.display = 'block';
	}

	document.getElementById('profileForm').onsubmit = function(e) {
	    console.log("Form in invio...");
	    
	    const passInput = document.getElementById('nuovaPw');
	    const confInput = document.getElementById('confermaPw');

	    if (!passInput || !confInput) {
	        console.error("Errore: Campi password non trovati!");
	        return; 
	    }

	    const pass = passInput.value;
	    const conf = confInput.value;

	    console.log("Password:", pass, "Conferma:", conf);

	    if (pass !== "" || conf !== "") {
	        if (pass !== conf) {
	            console.warn("Le password non coincidono!");
	            e.preventDefault();
	            document.getElementById('pwError').style.display = 'block';
	            return false;
	        }
	    }
	    console.log("Validazione superata, invio al server...");
	};
	</script>
</body>
</html>