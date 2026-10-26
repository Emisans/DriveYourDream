package control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.VehicleDAO;

@WebServlet("/SuggerimentiServlet")
public class SuggerimentiServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private VehicleDAO vehicleDAO = new VehicleDAO();


    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        String testo =
                request.getParameter("testo");


        if (testo == null) {
            testo = "";
        }

        testo = testo.trim();


        List<String> suggerimenti =
                vehicleDAO.getSuggerimentiMarche(testo);


        // La risposta della Servlet sarà JSON
        response.setContentType(
                "application/json"
        );

        response.setCharacterEncoding(
                "UTF-8"
        );


        StringBuilder json =
                new StringBuilder();

        json.append("[");


        for (int i = 0;
             i < suggerimenti.size();
             i++) {

            if (i > 0) {
                json.append(",");
            }

            json.append("\"");

            json.append(
                    suggerimenti.get(i)
                            .replace("\\", "\\\\")
                            .replace("\"", "\\\"")
            );

            json.append("\"");
        }


        json.append("]");


        response.getWriter().write(
                json.toString()
        );
    }


    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}