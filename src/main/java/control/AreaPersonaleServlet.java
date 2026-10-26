package control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.OrdineDAO;
import model.Ordine;
import model.Utente;

@WebServlet("/AreaPersonaleServlet")
public class AreaPersonaleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrdineDAO ordineDAO = new OrdineDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        // 1. Controllo di sicurezza: l'utente deve essere loggato
        if (session == null || session.getAttribute("utente") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Utente utente = (Utente) session.getAttribute("utente");

        // 2. Recuperiamo gli ordini dell'utente dal database utilizzando il DAO
        List<Ordine> storicoOrdini = ordineDAO.doRetrieveByUtente(utente.getId());

        // 3. Salviamo la lista nella request per passarla alla JSP
        request.setAttribute("storicoOrdini", storicoOrdini);

        // 4. Inoltriamo la richiesta ad area-personale.jsp
        request.getRequestDispatcher("area-personale.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}