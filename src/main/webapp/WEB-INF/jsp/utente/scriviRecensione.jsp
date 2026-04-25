<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    Long idAttr = (Long)request.getAttribute("idProfessionista");
    String idVal = (idAttr != null) ? idAttr.toString() : "";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lascia una recensione</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/scriviRecensione.css">
</head>
<body>
	<h1>Lascia una recensione:</h1>
	<form action="ScriviRecensioneServlet" method="POST">
		<label for="voto">Voto:</label>
		<select name="voto">
			<option value="1">1 stella</option>
			<option value="2">2 stelle</option>
			<option value="3">3 stelle</option>
			<option value="4">4 stelle</option>
			<option value="5">5 stelle</option>
		</select><br>
		<label for="descrizione">Descrizione</label><br>
		<textarea name="descrizione" required></textarea><br>
		
		<input type="hidden" name="id" value="<%= idVal %>">    	
		<input type="submit" value="Conferma">
	</form>
</body>
</html>