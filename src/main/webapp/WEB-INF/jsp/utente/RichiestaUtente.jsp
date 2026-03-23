<%@page import="org.elis.progetto.model.Richiesta"%>
<%@page import="org.elis.progetto.model.UtenteProfessione"%>
<%@page import="org.elis.progetto.model.Professione"%>
<%@page import="org.elis.progetto.model.UtenteVeicolo"%>
<%@page import="org.elis.progetto.model.OrarioBase"%>
<%@page import="org.elis.progetto.model.Disponibilita"%>
<%@page import="org.elis.progetto.model.Veicolo"%>
<%@page import="java.util.List"%>
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Invia Richiesta - Taskly</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/RichiestaUtente.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">

</head>
<body>
    <%@ include file="/WEB-INF/headerFooter/header.jsp"%>
    <% 
        Utente professionista = (Utente)request.getAttribute("professionista");
        List<Veicolo> veicolo =  (List<Veicolo>) request.getAttribute("veicoli");
        List<UtenteVeicolo> utenteVeicolo = (List<UtenteVeicolo>)request.getAttribute("utenteVeicolo");
        List<Professione> professioni =  (List<Professione>)request.getAttribute("ListaProf");
        List<UtenteProfessione> utenteProfessioni =  (List<UtenteProfessione>) request.getAttribute("utenteProf");
        List<Disponibilita> dispProssimeDueSettimane =  (List<Disponibilita>)request.getAttribute("dispProssimeDueSettimane");
    %>
    <div class="request-container">
        <div class="request-card">
            <h2>Nuova Richiesta</h2>
            <form action="<%=request.getContextPath()%>/InoltroRichieste" method="post">
                <input type="hidden" name="id_professionista" value="<%= (professionista != null) ? professionista.getId() : "" %>">
                
        <div class="input-group">
                    <label>Professione</label>
                    <select name="id_professione" required>
                        <option value="" data-prezzo="0" disabled selected>Seleziona professione</option>
                        <% for(Professione p : professioni){ 
                             for(UtenteProfessione up : utenteProfessioni){
                                if(up.getIdProfessione().equals(p.getId())){ %>
                                    <option value="<%=p.getId()%>" data-prezzo="<%=up.getTariffaH()%>"><%=p.getNome()%> - <%=up.getTariffaH()%>&euro;/H</option>
                        <% break; } } } %>
                    </select>
                </div>

             

                <div class="input-group">
                    <label>Descrizione della Task</label>
                    <textarea name="descrizione" placeholder="Dettagli..." rows="4" required></textarea>
                </div>

                <div class="input-group">
                    <label>Indirizzo di esecuzione</label>
                    <input type="text" name="indirizzo" placeholder="Via..." required>
                </div>

      <div class="input-group">
                    <label>Veicolo necessario</label>
                    <select name="id_veicolo">
                        <option value="" data-prezzo="0">Nessun veicolo</option>
                        <% for(Veicolo v : veicolo){ 
                             for(UtenteVeicolo uv : utenteVeicolo){
                                if(v.getId().equals(uv.getIdVeicolo())){ %>
                                    <option value="<%=v.getId()%>" data-prezzo="<%=uv.getAggiuntaServizio()%>"><%=v.getCategoria()%> (+<%=uv.getAggiuntaServizio()%>&euro;)</option>
                        <% break; } } } %>
                    </select>
                </div>


<div class="task-options">
    <label class="section-title">Quanto è grande la tua task?</label>
    <div class="duration-picker">
        <label class="radio-card">
            <input type="radio" name="durata_ore" value="1" checked onchange="aggiornaDati()">
            <span>1 ora</span>
        </label>
        <label class="radio-card">
            <input type="radio" name="durata_ore" value="2" onchange="aggiornaDati()">
            <span>2 ore</span>
        </label>
        <label class="radio-card">
            <input type="radio" name="durata_ore" value="3" onchange="aggiornaDati()">
            <span>3 ore</span>
        </label>
        <label class="radio-card">
            <input type="radio" name="durata_ore" value="4" onchange="aggiornaDati()">
            <span>4+ ore</span>
        </label>
    </div>
</div>

<div class="calendar-section">
    <div id="calendar-header-title" style="font-weight: bold; margin-bottom: 10px; color: #333;"></div>
    <div id="visual-calendar" class="visual-calendar">
        </div>
</div>

<div class="time-section" style="margin-top: 20px;">
    <label class="section-title">Orario di inizio</label>
    <select name="ora_inizio" id="ora_inizio_select" class="custom-select" required>
        <option value="">Seleziona prima una data</option>
    </select>
</div>

<input type="hidden" name="data_scelta" id="data_scelta_hidden">
<div class="price-summary" style="margin: 20px 0; padding: 15px; background-color: #f0fdf4; border: 1px solid #bbf7d0; border-radius: 8px; text-align: center; font-size: 1.2em; color: #166534;">
    <strong>Costo Stimato: </strong> <span id="prezzo_totale">0.00</span> &euro;
</div>


                <button type="submit" class="btn-submit">Invia Richiesta</button>
            </form>
        </div>
    </div>
    
    
    
    

    
    
    
    
<script>
window.blocchiGrezzi = [
    <% if(dispProssimeDueSettimane != null) { 
        for(Disponibilita d : dispProssimeDueSettimane) { %>
        { 
            data: "<%= d.getData() %>", 
            inizio: "<%= d.getInizio() %>", 
            fine: "<%= d.getFine() %>" 
        },
    <% } } %>
];
</script>


<script src="<%=request.getContextPath()%>/js/InoltroRichieste.js"></script>



</body>
</html>