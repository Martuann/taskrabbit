<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Impostazioni Account</title>
    <link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/impostazioni.css?">
</head>
<body>
<%@ include file="/WEB-INF/headerFooter/header.jsp" %>

<div id="toast-msg" class="toast-message"></div>

<div class="registrazione-container">
    <h1>Impostazioni</h1>

    <form action="<%= request.getContextPath() %>/Impostazioni" method="POST">
        <input type="hidden" name="azione" value="cambiaPassword">
        <div class="input-dati">
            <label>Password Attuale</label>
            <input type="password" name="vecchiaPassword" required>
        </div>
        <div class="input-dati">
            <label>Nuova Password</label>
            <input type="password" name="nuovaPassword" required>
        </div>
        <button type="submit" class="btn-submit">Aggiorna Credenziali</button>
    </form>
</div>

<script>
    const urlParams = new URLSearchParams(window.location.search);
    const msg = document.getElementById('toast-msg');

    if (urlParams.get('success') === 'true') {
        msg.innerText = "✅ Modifiche salvate con successo";
        msg.classList.add('toast-animation');
    } else if (urlParams.get('errorPass') === 'true') {
        msg.innerText = "❌ Password attuale errata";
        msg.classList.add('toast-error', 'toast-animation');
    }

    if (msg.classList.contains('toast-animation')) {
        setTimeout(() => {
            msg.classList.remove('toast-animation');
            window.history.replaceState({}, document.title, window.location.pathname);
        }, 3000);
    }
</script>
</body>
</html>