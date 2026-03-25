<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/footer.css">

<footer class="main-footer">
    <div class="footer-inner">
        <div class="footer-col">
            <img src="<%= request.getContextPath() %>/immagini/logo.png" alt="Taskly Logo" class="footer-logo">
            <div class="contact-info">
                <p><strong>Sede:</strong> Via Roma 123, 00100 Roma (RM)</p>
                <p><strong>Tel:</strong> +39 06 1234567</p>
                <p><strong>Email:</strong> info@taskly.it</p>
                <p><strong>P. IVA:</strong> IT12345678901</p>
            </div>
        </div>

        <div class="footer-col">
            <h4>Navigazione</h4>
            <ul>
                <li><a href="<%= request.getContextPath() %>/HomepageServlet">Chi Siamo</a></li>
                <li><a href="<%= request.getContextPath() %>/HomepageServlet>Servizi"></a></li>
                <li><a href="<%= request.getContextPath() %>/PagineWeb/registrazioneProfessionista.jsp">Lavora con noi</a></li>
            </ul>
        </div>

        <div class="footer-col">
            <h4>Note Legali</h4>
            <ul>
                <li><a href="#">Privacy Policy</a></li>
                <li><a href="#">Cookie Policy</a></li>
                <li><a href="#">Termini e Condizioni</a></li>
            </ul>
            <div class="social-icons">
                <a href="#"><i class="fab fa-facebook-f"></i></a>
                <a href="#"><i class="fab fa-instagram"></i></a>
                <a href="#"><i class="fab fa-linkedin-in"></i></a>
                <a href="#"><i class="fab fa-twitter"></i></a>
            </div>
        </div>

        <div class="footer-col">
            <h4>Newsletter</h4>
            <p>Rimani aggiornato sulle novità di Taskly.</p>
            <form action="#" method="POST" class="newsletter-form">
                <input type="email" name="email" placeholder="La tua email" required>
                <button type="submit" class="tasker-btn">Iscriviti</button>
            </form>
        </div>
    </div>

    <div class="footer-bottom">
        <p>&copy; <%= new java.util.GregorianCalendar().get(java.util.Calendar.YEAR) %> <strong>Taskly</strong>. Tutti i diritti riservati.</p>
    </div>
</footer>