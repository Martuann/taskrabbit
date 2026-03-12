<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - Nuova Professione</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/Registrazioni.css">
    <%@ include file="/WEB-INF/pagineAdmin/header.jsp"%>
</head>
<body>
    <div class="registrazione-container">
        <h1>Nuova professione</h1>
        <p>Aggiungi una nuova categoria lavorativa.</p>

        <%
            String msg = (String) request.getAttribute("messaggio");
            if(msg != null){
                String classeFeedback = msg.contains("Errore") ? "msg-errore" : "msg-successo";
        %>
            <div class="msg-feedback <%= classeFeedback %>">
                <%= msg %>
            </div>
        <% } %>

        <form action="<%= request.getContextPath() %>/AggiungiProfessione" method="POST">
            <div class="input-dati">
                <input type="text" name="nomeProfessione" placeholder="es. Falegname, Elettricista..." required>
            </div>
            <button type="submit" class="btn-submit">Salva nel Sistema</button>
        </form>
    </div>
</body>
</html>