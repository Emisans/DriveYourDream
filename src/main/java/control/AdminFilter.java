package control;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utente;

@WebFilter(urlPatterns = {
        "/AdminServlet",
        "/admin/*"
})
public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {

        // Non serve inizializzare nulla
    }


    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {


        // Convertiamo request e response
        // nelle versioni HTTP
        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        HttpServletResponse httpResponse =
                (HttpServletResponse) response;


        // Recuperiamo la sessione SENZA crearne una nuova
        HttpSession session =
                httpRequest.getSession(false);


        // =====================================================
        // 1. UTENTE NON LOGGATO
        // =====================================================

        if (session == null ||
                session.getAttribute("utente") == null) {

            httpResponse.sendRedirect(
                    httpRequest.getContextPath()
                    + "/login.jsp"
            );

            return;
        }


        // =====================================================
        // 2. UTENTE LOGGATO
        // =====================================================

        Utente utente =
                (Utente) session.getAttribute("utente");


        // =====================================================
        // 3. CONTROLLO RUOLO ADMIN
        // =====================================================

        if (!"ADMIN".equalsIgnoreCase(
                utente.getRuolo())) {

            httpResponse.sendError(
                    HttpServletResponse.SC_FORBIDDEN
            );

            return;
        }


        // =====================================================
        // 4. ADMIN AUTORIZZATO
        // =====================================================

        chain.doFilter(
                request,
                response
        );
    }


    @Override
    public void destroy() {

        // Non ci sono risorse da chiudere
    }
}