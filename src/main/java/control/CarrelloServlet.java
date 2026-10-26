package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.VehicleDAO;
import model.Carrello;
import model.Vehicle;

@WebServlet("/CarrelloServlet")
public class CarrelloServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        Carrello carrello =
                (Carrello) session.getAttribute("carrello");

        if (carrello == null) {

            carrello = new Carrello();

            session.setAttribute("carrello", carrello);
        }

        String azione = request.getParameter("azione");

        if (azione != null) {

            if (azione.equals("aggiungi")) {

                int idVeicolo =
                        Integer.parseInt(request.getParameter("id"));

                int giorni =
                        Integer.parseInt(request.getParameter("giorni"));

                if (giorni >= 1) {

                    VehicleDAO dao = new VehicleDAO();

                    Vehicle trovato =
                            dao.doRetrieveByKey(idVeicolo);

                    if (trovato != null) {
                        carrello.aggiungiElemento(trovato, giorni);
                    }
                }

            } else if (azione.equals("rimuovi")) {

                int idVeicolo =
                        Integer.parseInt(request.getParameter("id"));

                carrello.rimuoviElemento(idVeicolo);

            } else if (azione.equals("modifica")) {

                int idVeicolo =
                        Integer.parseInt(request.getParameter("id"));

                int giorni =
                        Integer.parseInt(request.getParameter("giorni"));

                carrello.modificaGiorni(idVeicolo, giorni);
            }
        }

        response.sendRedirect(
                request.getContextPath() + "/carrello.jsp"
        );
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}