package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recuperiamo la sessione corrente, senza creame una nuova se non esiste (false)
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Invalida e distrugge la sessione (cancella utente loggato e carrello)
        }
        // Reindirizziamo l'utente alla home, che ora tornerà a mostrare "Login" e "Registrazione"
        response.sendRedirect("index.jsp"); 
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}