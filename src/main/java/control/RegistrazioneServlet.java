package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UtenteDAO;
import model.Utente;

@WebServlet("/RegistrazioneServlet")
public class RegistrazioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Recupero parametri dal form HTML
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 2. Popolamento del Bean Utente
        Utente nuovoUtente = new Utente();
        nuovoUtente.setNome(nome);
        nuovoUtente.setCognome(cognome);
        nuovoUtente.setEmail(email);
        nuovoUtente.setPassword(password); 
        nuovoUtente.setRuolo("cliente"); // Default esame

        // 3. Chiamata al DAO
        UtenteDAO dao = new UtenteDAO();
        boolean salvato = dao.doSave(nuovoUtente);

        if (salvato) {
            // Registrazione riuscita -> vai al Login passando il parametro di successo nell'URL
            response.sendRedirect("login.jsp?successo=true");
        } else {
            // Qualcosa è andato storto (es. errore DB nascosto) -> torna indietro con errore
            request.setAttribute("errore", "Impossibile completare la registrazione. Riprova.");
            request.getRequestDispatcher("registrazione.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}