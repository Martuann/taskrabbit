<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="org.elis.progetto.model.Utente"%>
<%@ page import="org.elis.progetto.model.Veicolo"%>
<%@ page import="org.elis.progetto.model.UtenteVeicolo"%>
<%@ page import="org.elis.progetto.model.Professione"%>
<%@ page import="org.elis.progetto.model.UtenteProfessione"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Taskly - Gestione Servizi</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/styleGestioneEServizi.css">

</head>

<body>
	<%@ include file="/WEB-INF/headerFooter/header.jsp" %>
	<%
    Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");
    List<Professione> catalogoProfessioni = (List<Professione>) request.getAttribute("catalogoProfessioni");
    List<UtenteProfessione> professioniUtente = (List<UtenteProfessione>) request.getAttribute("professioniUtente");
    List<Veicolo> catalogoVeicoli = (List<Veicolo>) request.getAttribute("catalogoVeicoli");
    List<UtenteVeicolo> veicoliUtente = (List<UtenteVeicolo>) request.getAttribute("veicoliUtente");
     List<Professione> professioniDisponibili = (List<Professione>) request.getAttribute("professioniDisponibili"); 
     List<Veicolo> veicoliDisponibili =(List<Veicolo>) request.getAttribute("veicoliDisponibili");
    String context = request.getContextPath();
    String err = request.getParameter("errore");
%>
		<div class="link-container">
			<a href="<%= request.getContextPath() %>/GestioneOrariDefault">
			   Imposta disponibilità settimanale --->
			</a>
		</div>
	<div class="container">
		<div class="welcome-box">
			<h1>Area Professionista</h1>

		<% if("input".equals(err)) { %>
    <div class="error-msg"> Errore: Controlla i dati inseriti (assicurati che i prezzi siano numeri validi).</div>
<% } else if("duplicato".equals(err)) { %>
    <div class="error-msg" style="background-color: #fff3cd; color: #856404; border-left-color: #ffeeba;">
         Attenzione: Hai già associato questa competenza o veicolo al tuo profilo!
    </div>
<% } else if("1".equals(err)) { %>
    <div class="error-msg"> Si è verificato un errore di sistema durante il salvataggio.</div>
<% } %>

			<p>
				Benvenuto, <strong><%= (utenteLoggato != null) ? utenteLoggato.getNome() + " " + utenteLoggato.getCognome() : "Ospite" %></strong>.
				Gestisci qui le tue tariffe.
			</p>
		</div>

		<div class="sezione">
			<h2>Le Mie Competenze</h2>
			<table>
				<thead>
					<tr>
						<th>Professione</th>
						<th>Tariffa Oraria (€/h)</th>
						<th>Azioni</th>
					</tr>
				</thead>
				<tbody>
					<%
                if (professioniUtente != null && !professioniUtente.isEmpty()) {
                    for (UtenteProfessione up : professioniUtente) {
                        String nomeProf = "Sconosciuta";
                        if(catalogoProfessioni != null) {
                            for (Professione p : catalogoProfessioni) {
                                if (p.getId().equals(up.getIdProfessione())) { nomeProf = p.getNome(); break; }
                            }
                        }
            %>
					<tr>
						<td><strong><%= nomeProf %></strong></td>
						<td>
							<form action="<%=context%>/GestioneServiziServlet" method="post"
								class="form-inline">
								<input type="hidden" name="azione" value="modifica_tariffa">
								<input type="hidden" name="idAssociazione"
									value="<%= up.getId() %>"> <input type="hidden"
									name="idProfessione" value="<%= up.getIdProfessione() %>">

								<input type="number" name="nuovaTariffa" step="0.01"
									value="<%= up.getTariffaH() %>" required>
								<button type="submit" class="btn-update">Aggiorna</button>
							</form>
						</td>
						<td>
							<form action="<%=context%>/GestioneServiziServlet" method="post">
								<input type="hidden" name="azione" value="rimuovi_professione">
								<input type="hidden" name="idProfessioneDaRimuovere"
									value="<%= up.getId() %>">
								<button type="submit" class="btn-delete"
									onclick="return confirm('Rimuovere questa competenza?')">Rimuovi</button>
							</form>
						</td>
					</tr>
					<% } } else { %>
					<tr>
						<td colspan="3" style="text-align: center;">Nessuna
							competenza configurata.</td>
					</tr>
					<% } %>
				</tbody>
			</table>

			<div class="add-box">
				<h3>Aggiungi una nuova competenza</h3>
				<form action="<%=context%>/GestioneServiziServlet" method="post">
					<input type="hidden" name="azione" value="aggiungi_professione">
					<select name="idProfessioneScelta" required>
						<option value="">-- Seleziona Professione --</option>
						<% if (professioniDisponibili != null) {
                        for (Professione pCat : professioniDisponibili) { %>
						<option value="<%= pCat.getId() %>"><%= pCat.getNome() %></option>
						<% } } %>
					</select> <input type="number" step="0.01" name="tariffaOraria"
						placeholder="Prezzo all'ora" required>
					<button type="submit" class="btn-add">Aggiungi Competenza</button>
				</form>
			</div>
		</div>

		<div class="sezione">
			<h2>Mezzi di Trasporto</h2>
			<table>
				<thead>
					<tr>
						<th>Categoria Veicolo</th>
						<th>Sovrapprezzo Trasporto (€)</th>
						<th>Azioni</th>
					</tr>
				</thead>
				<tbody>
					<%
            if (veicoliUtente != null && !veicoliUtente.isEmpty()) {
                for (UtenteVeicolo uv : veicoliUtente) {
                    String nomeVeicolo = "Sconosciuto";
                    if(catalogoVeicoli != null) {
                        for (Veicolo v : catalogoVeicoli) {
                            if (v.getId().equals(uv.getIdVeicolo())) { 
                                nomeVeicolo = v.getCategoria(); 
                                break; 
                            }
                        }
                    }
        %>
					<tr>
						<td><strong><%= nomeVeicolo %></strong></td>
						<td>
							<form action="<%=context%>/GestioneServiziServlet" method="post"
								class="form-inline">
								<input type="hidden" name="azione" value="modifica_sovrapprezzo">
								<input type="hidden" name="idVeicolo"
									value="<%= uv.getIdVeicolo() %>"> <input type="number"
									name="nuovoPrezzo" step="0.01"
									value="<%= uv.getAggiuntaServizio() %>" required
									style="width: 80px;">
								<button type="submit" class="btn-update">Aggiorna</button>
							</form>
						</td>
						<td>
							<form action="<%=context%>/GestioneServiziServlet" method="post"
								style="margin: 0;">
								<input type="hidden" name="azione" value="rimuovi_veicolo">
								<input type="hidden" name="idVeicoloDaRimuovere"
									value="<%= uv.getIdVeicolo() %>">
								<button type="submit" class="btn-delete"
									onclick="return confirm('Vuoi eliminare questo veicolo?')">Elimina</button>
							</form>
						</td>
					</tr>
					<% 
                } 
            } else { 
        %>
					<tr>
						<td colspan="3" style="text-align: center;">Nessun veicolo
							associato al profilo.</td>
					</tr>
					<% } %>
				</tbody>
			</table>

			<div class="add-box">
				<h3>Associa un nuovo mezzo</h3>
				<form action="<%=context%>/GestioneServiziServlet" method="post">
					<input type="hidden" name="azione" value="aggiungi_veicolo">
					<select name="idVeicoloScelto" required>
						<option value="">-- Seleziona Mezzo --</option>
						<% if (veicoliDisponibili != null) {
                        for (Veicolo vCat : veicoliDisponibili) { %>
						<option value="<%= vCat.getId() %>"><%= vCat.getCategoria() %></option>
						<% } } %>
					</select> <input type="number" step="0.01" name="prezzoAggiuntivo"
						placeholder="Sovrapprezzo" required>
					<button type="submit" class="btn-add">Salva Mezzo</button>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/headerFooter/footer.jsp"%>
</body>
</html>