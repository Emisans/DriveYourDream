package control;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.VehicleDAO;
import model.Vehicle;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String citta = request.getParameter("citta");
        String marca = request.getParameter("marca");
        String prezzoMaxString = request.getParameter("prezzoMax");

        if (citta == null) {
            citta = "";
        } else {
            citta = citta.trim();
        }

        if (marca == null) {
            marca = "";
        } else {
            marca = marca.trim();
        }

        Double prezzoMax = null;

        if (prezzoMaxString != null && !prezzoMaxString.trim().isEmpty()) {
            try {
                prezzoMax = Double.parseDouble(prezzoMaxString);
            } catch (NumberFormatException e) {
                prezzoMax = null;
            }
        }

        VehicleDAO dao = new VehicleDAO();

        List<Vehicle> listaVeicoli =
                dao.searchVehicles(citta, marca, prezzoMax);

        request.setAttribute("risultati", listaVeicoli);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("index.jsp");

        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}