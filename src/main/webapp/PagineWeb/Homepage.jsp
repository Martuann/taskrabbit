<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Taskly - Servizi per la casa</title>
    <link rel="stylesheet" href="../css/Homepage.css">

</head>
<body>

    <nav class="navbar">
        <div class="logo">Taskly</div>
        <div class="auth-buttons">
            <a href="#">Home</a>
            <a href="#">Servizi</a>
            <a href="login.html" class="btn-login">Accedi</a>
            <a href="registrazioneUtente.jsp" class="btn-register">Registrati</a>
        </div>
    </nav>

  <header class="hero">
        <div class="hero-content">
            <h1>Benvenuto su Taskly</h1>
            <p>I tuoi progetti di casa resi semplici. Trova esperti per ogni necessità.</p>
            <div class="search-container">
                <form action="${pageContext.request.contextPath}/RicercaProfessionistiServlet" method="GET" style="display: flex; width: 100%;">
                    <input type="text" name="professione" placeholder="Di cosa hai bisogno? (es. Pittura, Riparazioni)" required>
                    <button type="submit">Cerca</button>
                </form>
            </div>
        </div>
    </header>

    <section class="services-section">
        <h2>Esplora i nostri servizi</h2>
        <div class="services-grid">
            
            <div class="service-card">
                <img src="immagini/Riparazioni.jpg" alt="Riparazioni">
                <div class="card-text">
                    <h3>Riparazioni</h3>
                    <p>Montaggio mobili e riparazioni elettriche.</p>
                    <a href="#" class="view-more">Vedi Tasker</a>
                </div>
            </div>

            <div class="service-card pittura-card">
                <img src="immagini/Imbiancatura.jpg" alt="Pittura">
                <div class="card-text">
                    <h3>Pittura</h3>
                    <p>Rinfresca le pareti della tua casa.</p>
                    <a href="#" class="view-more">Vedi Tasker</a>
                </div>
            </div>

            <div class="service-card">
                <img src="immagini/Pulizia.jpg" alt="Pulizia">
                <div class="card-text">
                    <h3>Pulizia</h3>
                    <p>Servizi di pulizia profonda per ogni ambiente.</p>
                    <a href="#" class="view-more">Vedi Tasker</a>
                </div>
            </div>

            <div class="service-card">
                <img src="immagini/Aiuto informatico.jpg" alt="Informatica">
                <div class="card-text">
                    <h3>Aiuto Informatico</h3>
                    <p>Riparazione PC e installazione software.</p>
                    <a href="#" class="view-more">Vedi Tasker</a>
                </div>
            </div>

        </div>
    </section>

    <footer>
        <p>Â© 2026 Taskly | Privacy | Termini</p>
    </footer>

</body>
</html>