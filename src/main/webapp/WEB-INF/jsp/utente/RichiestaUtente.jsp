<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Invia Richiesta - Taskly</title>
    <link rel="stylesheet" href="../css/RichiestaUtente.css">
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
            <h2>Nuova Richiesta</h2>
            <p class="subtitle">Compila i dettagli per assumere un professionista.</p>

            <form action="/taskrabbit/inviaRichiesta" method="post">
                
                <div class="input-group">
                    <label>Per quale professione vuoi assumere questo professionista?</label>
                    <select name="professione" required>
                        <option value="" disabled selected>Seleziona professione</option>
                        <option value="pittore">Pittore</option>
                        <option value="idraulico">Idraulico / Riparazioni</option>
                        <option value="pulizie">Addetto Pulizie</option>
                        <option value="informatico">Tecnico Informatico</option>
                    </select>
                </div>

             
                <div class="input-group">
                    <label>Descrizione della Task </label>
                    <textarea name="descrizione" placeholder="Es: Ho bisogno di imbiancare una stanza di 20mq..." rows="4" required></textarea>
                </div>

               
                <div class="input-group">
                    <label>Indirizzo di esecuzione</label>
                    <input type="text" name="indirizzo" placeholder="Via e numero civico, Città" required>
                </div>
                <div class="input-group">
                    <label>Data di esecuzione </label>
                    <input type="date" name="data_esecuzione" min="<%= LocalDate.now() %> " max="<%=LocalDate.now().plusMonths(1) %>" required>
                </div>

               
                <div class="input-group">
                    <label>Veicolo necessario </label>
                    <select name="veicolo" required>
                        <option value="" disabled selected>Seleziona tipo di veicolo</option>
                        <option value="nessuno">Nessuno (A piedi)</option>
                        <option value="auto">Auto</option>
                        <option value="moto">Moto/Scooter</option>
                        <option value="furgone-grande">Furgone Grande</option>
                        <option value="furgone-piccolo">Furgone Piccolo </option>
                        <option value="bicicletta">Bicicletta </option>
                       
                    </select>
                </div>
				
                
             <div class="input-group">
                    <label>Durata stimata dell'intervento (ore)</label>
                    <input type="number" name="ore" value="1" min="1" max="8" required 
                           style="width: 100%; padding: 12px; border: 1px solid #ccc; border-radius: 8px;">
                </div>
                <div class="price-info-box">
                    <div class="price-display">
                        <span>Tariffa base:</span>
                        <strong>&#8364 50.00 / ora</strong>
                    </div>
                    <p class="price-note">*Il totale verrà confermato dal professionista.</p>
                </div>
                
                <button type="submit" class="btn-submit">Invia Richiesta</button>
            </form>

            <div class="request-footer">
                <a href="taskrabbit/HomepageServlet">Annulla e torna alla Home</a>
            </div>
        </div>
   </div>

</body>
</html>