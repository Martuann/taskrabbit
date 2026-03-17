<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Impostazioni Account</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/impostazioni.css">
</head>
<body>

<div id="toast-msg" class="toast-message"></div>

<div class="registrazione-container">
    <h1>Impostazioni Profilo</h1>
    
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

    <div class="account-closure-section">
        <div class="closure-content">
            <div class="closure-text">
                <h3>Chiusura Account</h3>
                <p>La disattivazione comporta la rimozione permanente di tutti i dati. L'operazione non è reversibile.</p>
            </div>
        </div>
        <div class="closure-footer">
            <form action="<%= request.getContextPath() %>/Impostazioni" method="POST" onsubmit="return confirm('Confermi la chiusura definitiva dell\'account?');">
                <input type="hidden" name="azione" value="eliminaAccount">
                <button type="submit" class="btn-action-terminate">Chiudi Account</button>
            </form>
        </div>
    </div>
</div>

<script>
    const urlParams = new URLSearchParams(window.location.search);
    const msg = document.getElementById('toast-msg');

    if (urlParams.get('success') === 'true') {
        msg.innerText = "✅ Credenziali aggiornate con successo";
        msg.classList.add('toast-animation');
    } else if (urlParams.get('errorPass') === 'true') {
        msg.innerText = "❌ Password attuale non corretta";
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