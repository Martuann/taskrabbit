<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Seleziona Giorni - Taskly</title>
    <link rel="stylesheet" href="../css/CalendarioRichiestaUtente.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
</head>
<body>

    <div class="top-logo">
        <a href="Homepage.jsp">
            <img src="../img/taskly_logo.png" alt="Taskly Logo">
        </a>
    </div>

    <div class="request-container">
        <div class="request-card">
            <h2>Quando ti serve aiuto?</h2>
            <p class="subtitle">Seleziona la data e l'orario preferito per la tua task.</p>

            <form action="/taskrabbit/salvaData" method="post">
                
                <div class="input-group">
                    <label>Data della Task</label>
                    <input type="date" name="dataTask" >
                </div>

                <div class="input-group">
                    <label>Orario preferito</label>
                    <select name="fasciaOraria" required>
                        <option value="" disabled selected>Scegli una fascia oraria</option>
                        <option value="mattina">Mattina </option>
                        <option value="pomeriggio">Pomeriggio </option>
                        <option value="sera">Sera</option>
                    </select>
                </div>

                <div class="input-group">
                    <label>Sei flessibile con i giorni?</label>
                    <div class="flexibility-options">
                        <label class="option-check">
                            <input type="checkbox" name="flessibile" value="si"> 
                            <span>Sì, posso cambiare giorno se necessario</span>
                        </label>
                    </div>
                </div>

            

            </form>

            <div class="request-footer">
                <a href="RichiestaUtente.jsp">Indietro ai dettagli della task</a>
            </div>
        </div>
    </div>

</body>
</html>