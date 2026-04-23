<%@ page isErrorPage="true" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Errore</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/errorstyle.css">
</head>
<body class="error-page-body">

    <%@ include file="/WEB-INF/headerFooter/header.jsp" %>

    <div class="error-overlay">
        <div class="error-card-new">
            <h1 class="error-status">
                <%= (request.getAttribute("jakarta.servlet.error.status_code") != null) ? request.getAttribute("jakarta.servlet.error.status_code") : "!" %>
            </h1>
            <h2>Oops! Qualcosa è andato storto</h2>
            <p>
                <%= (request.getAttribute("jakarta.servlet.error.message") != null) ? request.getAttribute("jakarta.servlet.error.message") : "Non siamo riusciti a trovare la pagina o si è verificato un errore." %>
            </p>
            <div class="error-actions">
                <a href="<%= request.getContextPath() %>/" class="btn-home-error">Torna alla Home</a>
            </div>
        </div>
    </div>

    <%@ include file="/WEB-INF/headerFooter/footer.jsp" %>

</body>
</html>