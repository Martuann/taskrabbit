package org.elis.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.elis.progetto.model.Ruolo;
import org.elis.progetto.model.Utente;

/**
 * Servlet Filter implementation class AdminFilter
 */
@WebFilter(urlPatterns = {
	    "/AggiungiProfessione",
		"/AggiungiVeicoloServlet",
		"/AggiungiCittaServlet"
		
	})
public class AdminFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public AdminFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest=(HttpServletRequest) request;
		HttpServletResponse httpResponse=(HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        
        Utente utenteLoggato = (Utente)session.getAttribute("utenteLoggato");
        if(utenteLoggato == null ||!utenteLoggato.getRuolo().equals(Ruolo.ADMIN)) {
        	httpResponse.sendRedirect(httpRequest.getContextPath()+"/HomepageServlet");
        	return;
        }
        
		chain.doFilter(request, response);
	}
	

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
