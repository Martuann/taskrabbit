
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin - Nuovo Veicolo</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/Registrazioni.css">
</head>
<body class="site-wrapper">
<%@ include file="/WEB-INF/headerFooter/header.jsp"%>

    <main class="content-wrapper">
        <div class="registrazione-container">
            <h1>Nuovo Veicolo</h1>
            <p>Aggiungi una nuova categoria di veicolo al sistema.</p>

            <%
                String msg = (String) request.getAttribute("messaggio");
                if(msg != null){
                    String classeFeedback = msg.contains("Errore") ? "msg-errore" : "msg-successo";
            %>
                <div class="msg-feedback <%= classeFeedback %>">
                    <%= msg %>
                </div>
            <% } %>

            <form action="<%= request.getContextPath() %>/AggiungiVeicolo" method="POST">
                <div class="input-dati">
                    <input type="text" name="tipoVeicolo" placeholder="es. Furgone, Moto, Auto..." required>
                </div>
                <button type="submit" class="btn-submit">Salva nel Sistema</button>
            </form>
        </div>
    </main>

    <%@ include file="/WEB-INF/headerFooter/footer.jsp"%>
</body>
</html>
