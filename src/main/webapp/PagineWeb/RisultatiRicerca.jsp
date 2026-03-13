<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Risultati per ${query}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Homepage.css">
</head>
<body>
    <nav class="navbar">
        <div class="logo">Taskly - Risultati</div>
        <a href="Homepage.jsp" style="color:white; margin-left:20px;">Torna alla Home</a>
    </nav>

    <div class="services-section">
        <h2>Professionisti trovati per: "${query}"</h2>
        
        <div class="services-grid">
            <c:forEach var="pro" items="${professionisti}">
                <div class="service-card">
                    <div class="card-text">
                        <h3>${pro.nome} ${pro.cognome}</h3>
                        <p>Email: ${pro.email}</p>
                        <a href="#" class="view-more">Contatta</a>
                    </div>
                </div>
            </c:forEach>
        </div>

        <c:if test="${empty professionisti}">
            <p style="text-align:center; margin-top:50px;">Nessun professionista trovato con questa specializzazione.</p>
        </c:if>
    </div>
</body>
</html>