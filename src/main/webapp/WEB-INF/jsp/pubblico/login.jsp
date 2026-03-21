<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Accedi - Taskly</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/login.css">
<link
	href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap"
	rel="stylesheet">
</head>
<body>
	<%@ include file="/WEB-INF/headerFooter/header.jsp"%>

	<div class="login-wrapper">
		<div class="top-logo">Taskly</div>

		<div class="login-card">
			<h2>Accedi al tuo account</h2>
			<p class="subtitle">Bentornato! Inserisci i tuoi dati per
				continuare.</p>

			<div id="alert-registrazione">✅ Registrazione completata! Ora
				puoi effettuare il login.</div>

			<div id="alert-errore">❌ Email o
				Password non corretti. Riprova.</div>

			<form action="<%=request.getContextPath()%>/Login" method="post">
				<div class="input-group">
					<input type="email" name="email" placeholder="Email" required>
				</div>
				<div class="input-group">
					<input type="password" name="password" placeholder="Password"
						required>
				</div>

				<button type="submit" class="btn-primary">Accedi</button>
			</form>

			<div class="login-footer">
				<p>
					Non hai un account? <a
						href="<%=request.getContextPath()%>/RegistrazioneUtenteServlet">Registrati
						qui</a>
				</p>
				<a href="<%=request.getContextPath()%>/HomepageServlet"
					class="back-home">Torna alla homepage</a>
			</div>
		</div>
	</div>
	<script>

        const params = new URLSearchParams(window.location.search);
        const alertSuccess = document.getElementById('alert-registrazione');
        const alertError = document.getElementById('alert-errore');

        if (params.get('registrato') === 'true') {
            if (alertSuccess) {
                alertSuccess.style.display = 'block';
                alertSuccess.style.opacity = "1";

                setTimeout(() => {
                    alertSuccess.style.transition = "opacity 0.5s ease";
                    alertSuccess.style.opacity = "0";
                    setTimeout(() => {
                        alertSuccess.style.display = "none";
                    }, 500);
                }, 4000);
            }
        }
        else if (params.get('errore') === '1') {
            if (alertError) {
                alertError.style.display = 'block';
            }
        }

        if (params.has('registrato') || params.has('errore')) {
            window.history.replaceState({}, document.title, window.location.pathname);
        }

    </script>
</body>
</html>