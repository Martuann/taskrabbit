<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
   <title>Accedi - Taskly</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/headerFooter/header.jsp"%>

    <div class="login-wrapper">
        <div class="top-logo">Taskly</div>

        <div class="login-card">
            <h2>Accedi al tuo account</h2>
            <p class="subtitle">Bentornato! Inserisci i tuoi dati per continuare.</p>

            <form action="<%=request.getContextPath()%>/Login" method="post">
                <div class="input-group">
                    <input type="email" name="email" placeholder="Email" required>
                </div>
                <div class="input-group">
                    <input type="password" name="password" placeholder="Password" required>
                </div>

                <button type="submit" class="btn-primary">Accedi</button>
            </form>

            <div class="login-footer">
                <p>Non hai un account? <a href="<%= request.getContextPath() %>/RegistrazioneUtenteServlet">Registrati qui</a></p>
                <a href="<%= request.getContextPath() %>/HomepageServlet" class="back-home">Torna alla homepage</a>
            </div>
        </div>
    </div>

</body>
</html>