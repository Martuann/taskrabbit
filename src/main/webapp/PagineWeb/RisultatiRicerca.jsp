<%@page import="org.elis.progetto.model.Utente"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Risultati per ${query}</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/Homepage.css">
</head>
<body>
    <nav class="navbar">
        <div class="logo">Taskly - Risultati</div>
        <a href="/HomepageServlet" style="color:white; margin-left:20px;">Torna alla Home</a>
    </nav>
	<%List<Utente> professionisti = (List<Utente>)request.getAttribute("professionisti"); %>
    <div class="services-section">
        <h2>Professionisti trovati per: "${query}"</h2>
        
        <div class="services-grid">
        <%for(Utente u : professionisti){ %>
                <div class="service-card">
                    <div class="card-text">
                        <h3><%=u.getNome() +" "+ u.getCognome()%></h3>
                        <p>Email: <%=u.getEmail()%></p>
      
                        <a href="<%=request.getContextPath()%>/ProfiloProfessionistaServlet?id1=<%=u.getId() %>" class="view-more">Mostra il profilo</a>
                        
                        
                    </div>
                </div>
        <%} %>
<%--             <c:forEach var="pro" items="${professionisti}">
                <div class="service-card">
                    <div class="card-text">
                        <h3>${pro.nome} ${pro.cognome}</h3>
                        <p>Email: ${pro.email}</p>
                        <a href="#" class="view-more">Contatta</a>
                    </div>
                </div>
            </c:forEach> --%>
        </div>
        
     <%if(professionisti == null||professionisti.isEmpty()){ %>

            <p style="text-align:center; margin-top:50px;">Nessun professionista trovato con questa specializzazione.</p>
        <% } %>
    </div>
</body>
</html>