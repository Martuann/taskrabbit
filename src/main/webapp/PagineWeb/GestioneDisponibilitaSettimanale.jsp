<%@page import="java.time.temporal.TemporalAdjusters"%>
<%@page import="java.time.DayOfWeek"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.format.TextStyle"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.List"%>
<%@page import="org.elis.progetto.model.Disponibilita"%>
<%@page import="org.elis.progetto.model.OrarioBase"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Taskly - Agenda Settimanale</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/styleGestioneEServizi.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/GestioneDisponibilita.css">
</head>
<body>
	<header>
		<% String context = request.getContextPath(); %>
		<a href="<%=context%>/HomeServlet"><img src="<%=context%>/img/taskly_logo.png" class="logo-img"></a>
		<nav>
			<a href="<%=context%>/HomeServlet">Home</a> > <a href="<%=context%>/GestioneServiziServlet">Gestione servizi</a> > <strong>Agenda</strong>
		</nav>
	</header>

	<div class="container agenda-layout">
		<% 
			int offSet = 0; 
			String offSetParam = request.getParameter("offSet");
			if (offSetParam != null) {
				offSet = Integer.parseInt(offSetParam);
			}
			
			LocalDate oggi = LocalDate.now();
			LocalDate lunedi = oggi.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).plusWeeks(offSet);
			List<Disponibilita> orariSettimana = (List<Disponibilita>) request.getAttribute("orarioSettimana");
			List<OrarioBase> orariStandard = (List<OrarioBase>) request.getAttribute("orarioStandard");
		%>

		<div class="navigazione-settimana">
			<% if (offSet > 0) { %>
				<a href="?offSet=<%=offSet-1%>" class="btn-nav">Settimana Precedente</a>
			<% } else { %>
				<span class="btn-nav disabilitato">Settimana Precedente</span>
			<% } %>
			
			<span class="testo-settimana">
				Settimana del <%= lunedi.getDayOfMonth() %> <%= lunedi.getMonth().getDisplayName(TextStyle.FULL, Locale.ITALIAN) %> <%= lunedi.getYear() %>
			</span>
			
			<a href="?offSet=<%=offSet+1%>" class="btn-nav">Settimana Successiva</a>
		</div>

		<form action="<%=request.getContextPath()%>/GestioneOrariDateSpecifiche" method="POST">
			<div class="calendario-grid">
				<% 
				for(int i = 0; i < 7; i++) {
					LocalDate dataCorrente = lunedi.plusDays(i);
					DayOfWeek giornoSettimana = dataCorrente.getDayOfWeek();
					boolean checkOggi = dataCorrente.equals(oggi);
					boolean isPassato = dataCorrente.isBefore(oggi);
					
					Disponibilita dispTrovata = null;
					if (orariSettimana != null) {
						for (Disponibilita d : orariSettimana) {
							if (d.getData() != null && d.getData().equals(dataCorrente)) {
								dispTrovata = d;
								break;
							}
						}
					}

					OrarioBase standardGiorno = null;
					if (dispTrovata == null && orariStandard != null) {
						for (OrarioBase ob : orariStandard) {
							if (ob.getGiornoSettimana() == giornoSettimana) {
								standardGiorno = ob;
								break;
							}
						}
					}

					boolean lavora = (dispTrovata != null || (dispTrovata == null && standardGiorno != null));
					String inizio = "09:00"; 
					String fine = "18:00";

					if (dispTrovata != null) {
						inizio = dispTrovata.getInizio().toString();
						fine = dispTrovata.getFine().toString();
					} else if (standardGiorno != null) {
						inizio = standardGiorno.getOraInizio().toString();
						fine = standardGiorno.getOraFine().toString();
					}
				%>
					<div class="quadrato-giorno <%= checkOggi ? "oggi" : "" %> <%= isPassato ? "storico" : "" %>">
						<div class="header-giorno">
							<span class="nome-giorno"><%= dataCorrente.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ITALIAN) %></span>
							<span class="data-num"><%= dataCorrente.getDayOfMonth() %></span>
							<input type="hidden" name="data_<%=i%>" value="<%=dataCorrente%>">
						</div>

						<div class="corpo-giorno">
							<% if (isPassato) { %>
								<% if (dispTrovata != null) { %>
									<div class="badge-storico badge-dispo">Disponibile</div>
									<div class="orari-passati"><%= dispTrovata.getInizio() %> - <%= dispTrovata.getFine() %></div>
								<% } else { %>
									<div class="badge-storico badge-null">Dato non registrato</div>
									<div class="testo-info-passato">Nessuna modifica salvata</div>
								<% } %>
							<% } else { %>
								<input type="checkbox" class="toggle-lavoro" name="lavora_<%=i%>" value="true" <%= lavora ? "checked" : "" %>>
								<div class="testi-dispo">
									<span class="testo-stato disponibile">Disponibile</span>
									<span class="testo-stato non-disponibile">Chiuso</span>
								</div>
								<div class="controlli-orario">
									<div class="input-group">
										<span>Dalle</span>
										<input type="time" name="oraInizio_<%=i%>" value="<%= inizio %>">
									</div>
									<div class="input-group">
										<span>Alle</span>
										<input type="time" name="oraFine_<%=i%>" value="<%= fine %>">
									</div>
								</div>
							<% } %>
						</div>
					</div>
				<% } %>
			</div>
			
			<div class="contenitore-bottone">
				<button type="submit" class="btn-salva">Salva Agenda Settimanale</button>
			</div>
		</form>
	</div>
<script>
    const valoriOriginali = new Map();

    const inputs = document.querySelectorAll('.agenda-layout input[type="checkbox"], .agenda-layout input[type="time"]');

    inputs.forEach((input, index) => {
        const valoreIniziale = input.type === 'checkbox' ? input.checked : input.value;
        valoriOriginali.set(input, valoreIniziale);
    });

    function isFormVeramenteModificato() {
        for (let [input, valoreIniziale] of valoriOriginali) {
            const valoreAttuale = input.type === 'checkbox' ? input.checked : input.value;
            if (valoreAttuale !== valoreIniziale) {
                return true;
            }
        }
        return false;
    }

    function confermaSpostamento(event) {
        if (isFormVeramenteModificato()) {
            const conferma = confirm("Hai modifiche non salvate che andranno perse. Vuoi continuare lo stesso?");
            if (!conferma) {
                event.preventDefault();
            }
        }
    }

    document.querySelectorAll('.btn-nav').forEach(bottone => {
        bottone.addEventListener('click', confermaSpostamento);
    });

    document.querySelector('form').addEventListener('submit', () => {
    });
</script>
</body>
</html>