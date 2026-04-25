<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="shortcut icon" href="favicon.png" type="image/x-icon">
  <!-- font -->
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,400;0,700;1,400&display=swap" rel="stylesheet">
  <!-- font ends here -->
  <link rel="stylesheet" href="./css/aboutUs.css">
  <title>Taskly - Chi siamo</title>
</head>
<body>
<%@ include file="/WEB-INF/headerFooter/header.jsp" %>

  <section class="banner">
    <div class="container">
      <h1>About us</h1>
      <p>Questo progetto nasce come applicazione Java Web Full-Stack, sviluppata per dimostrare l'integrazione tra logica di business lato server e interfacce utente dinamiche. Il cuore del sistema è basato su Servlet che gestiscono il flusso dei dati, garantendo una comunicazione fluida tra l'utente e il server</p>
    </div>     <!-- .container -->
  </section>  <!-- .banner -->
  <section class="first">
    <div class="container">
      <p></p>
    </div>
  </section>
  <section class="second">
    <div class="container">
      <div class="left-img">
        <img src="<%=request.getContextPath()%>/immagini/elis.png" alt="Elis">
      </div>
      <div class="right-content">
        <h2>Elis</h2>
        <p>Il progetto Taskly è stato ideato e sviluppato durante il master dell'ELIS. Dal 1965, l'ELIS rappresenta un punto di riferimento per la formazione tecnologica, ponendo al centro la crescita della persona e il servizio alla società attraverso il lavoro professionale</p>
        <p>
        <a class="cta" href="https://www.elis.org/">Learn more »</a>
      </div>
    </div>
  </section>
  <section class="third">
    <div class="container">
      <div class="left-content">
       <h2>Lo Stack Tecnologico</h2>
  <p>Taskly &egrave; costruita su fondamenta robuste. Abbiamo utilizzato <strong>Java EE</strong> per gestire la logica di business, garantendo sicurezza e scalabilit&agrave;. Il cuore dell'applicazione risiede nelle <strong>Servlet</strong>, che fungono da controller per smistare le richieste e fornire risposte dinamiche in tempo reale.</p>
  <p>L'interfaccia utente, sviluppata con <strong>JSP (JavaServer Pages)</strong>, comunica costantemente con il backend per offrire un'esperienza fluida, separando nettamente la presentazione dalla gestione dei dati (Pattern MVC).</p>
      </div>
      <div class="right-img">
        
      </div>
    </div>    <!-- .container -->
  </section>  <!-- .third -->
  <section class="four">
    <div class="container">
      <div class="member">
        <img src="<%=request.getContextPath()%>/immagini/Martin.png" alt="photo">
        <h3>Martin Ilardi</h3>
        <p>Addetto al testing e al debugging. Si è assicurato che ogni funzionalità rispondesse correttamente ai requisiti del progetto.</p>
        <div class="social">
          <a title="LinkedIn" href="https://www.linkedin.com/in/martin-ilardi-272338386/"><img src="<%=request.getContextPath()%>/immagini/linkedin.png" alt="linkedin"></a>
        
        </div>
      </div>  <!-- .member -->
      <div class="member">
        <img src="<%=request.getContextPath()%>/immagini/Giacomo.jpg" alt="photo">
        <h3>Giacomo Rizzo</h3>
        <p>Frontend Specialist e UX Designer. Si è occupato della resa visiva e dell'usabilità delle pagine JSP.</p>
        <div class="social">
          <a title="LinkedIn" href="https://www.linkedin.com/in/giacomo-rizzo-20a451159/"><img src="<%=request.getContextPath()%>/immagini/linkedin.png" alt="linkedin"></a>
      
        </div> <!-- .social -->
      </div>  <!-- .member -->
      <div class="member">
        <img src="<%=request.getContextPath()%>/immagini/Gabri.png" alt="photo">
        <h3>Gabriele Satta</h3>
        <p>Responsabile dell'architettura Backend e della gestione dei dati. Ha curato l'integrazione delle Servlet e la logica core dell'applicazione.</p>
        <div class="social">
          <a title="LinkedIn" href="https://www.linkedin.com/in/gabrielesatta99/"><img src="<%=request.getContextPath()%>/immagini/linkedin.png" alt="linkedin"></a>
         
        </div> <!-- .social -->
      </div>  <!-- .member -->
       <div class="member">
        <img src="<%=request.getContextPath()%>/immagini/Vincenzo.jpg" alt="photo">
        <h3>Vincenzo Cammarella</h3>
        <p>Responsabile della documentazione tecnica e della gestione dei contenuti, garantendo coerenza tra codice e interfaccia.</p>
        <div class="social">
          <a title="LinkedIn" href="https://www.linkedin.com/in/vincenzo-cammarella/"><img src="<%=request.getContextPath()%>/immagini/linkedin.png" alt="linkedin"></a>
       
        </div> <!-- .social -->
      </div>  <!-- .member -->
    </div>    <!-- .container -->
  </section>  <!-- .four -->
  <section class="five">
    <div class="container">
   
    </div>    <!-- .container -->
  </section>  <!-- .five -->
  <section class="six">
    <div class="container">
    <h4>Oltre il Codice: Il metodo ELIS</h4>
    <p>Sviluppare Taskly non &egrave; stato solo un esercizio di programmazione, ma una sfida di Project Management. Seguendo i valori dell'ELIS, abbiamo lavorato con l'obiettivo di "imparare per servire", cercando di creare uno strumento che sia realmente utile nella gestione quotidiana delle attivit&agrave;.</p>
    <p><i>"La tecnologia &egrave; nulla senza le persone. Il nostro impegno &egrave; stato quello di mettere la potenza di Java al servizio dell'organizzazione umana."</i></p>
    </div>
  </section>
  <footer>
    <div class="container">
    </div>
  </footer>
  
</body>
</html>