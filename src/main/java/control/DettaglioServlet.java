package control;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.VehicleDAO;
import model.Vehicle;

@WebServlet("/DettaglioServlet")
public class DettaglioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        // 1. Controllo di sicurezza sul parametro ID
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("index.jsp"); // Se manca l'ID, torna alla home
            return;
        }

        try {
            int idVeicolo = Integer.parseInt(idParam);

            // 2. Recupero del veicolo dal database tramite il DAO (metodo ottimizzato)
            VehicleDAO dao = new VehicleDAO();
            Vehicle veicolo = dao.doRetrieveByKey(idVeicolo);

            if (veicolo != null) {
                // 3. Se il veicolo esiste, lo passiamo alla pagina di dettaglio
                request.setAttribute("veicolo", veicolo);
                RequestDispatcher dispatcher = request.getRequestDispatcher("dettaglio.jsp");
                dispatcher.forward(request, response);
            } else {
                // Se non esiste alcun veicolo con quell'ID, rimandiamo alla home
                response.sendRedirect("index.jsp");
            }

        } catch (NumberFormatException e) {
            // Gestione dell'errore nel caso in cui l'ID non sia un numero valido (es. lettere nell'URL)
            response.sendRedirect("index.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}