package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UtenteDAO;

@WebServlet("/CheckEmailServlet")
public class CheckEmailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        boolean esiste = false;

        if (email != null && !email.trim().isEmpty()) {
            UtenteDAO dao = new UtenteDAO();
            esiste = dao.checkEmailExists(email);
        }

       
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        
        response.getWriter().write("{\"esiste\": " + esiste + "}");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}