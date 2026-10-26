package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UtenteDAO;
import model.Utente;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {

            request.setAttribute("errore", "Tutti i campi sono obbligatori.");

            request.getRequestDispatcher("login.jsp")
                   .forward(request, response);

            return;
        }

        UtenteDAO utenteDAO = new UtenteDAO();

        Utente utente =
                utenteDAO.doRetrieveByLogin(email, password);

        if (utente != null) {

            HttpSession session = request.getSession(true);

            session.setAttribute("utente", utente);

            if ("ADMIN".equalsIgnoreCase(utente.getRuolo())) {

                response.sendRedirect(
                    request.getContextPath() + "/AdminServlet"
                );

            } else {

                response.sendRedirect(
                    request.getContextPath() + "/search"
                );
            }

        } else {

            request.setAttribute(
                "errore",
                "Email o Password errate."
            );

            request.getRequestDispatcher("login.jsp")
                   .forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect(
            request.getContextPath() + "/login.jsp"
        );
    }
}