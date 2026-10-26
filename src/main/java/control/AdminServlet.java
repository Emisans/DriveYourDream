package control;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.OrdineDAO;
import dao.VehicleDAO;
import model.Ordine;
import model.Utente;
import model.Vehicle;


@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    private VehicleDAO vehicleDAO =
            new VehicleDAO();


    private OrdineDAO ordineDAO =
            new OrdineDAO();



    // =========================================================
    // GET
    // =========================================================

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        HttpSession session =
                request.getSession(false);



        // =====================================================
        // CONTROLLO LOGIN
        // =====================================================

        if (session == null ||
                session.getAttribute("utente") == null) {


            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );


            return;
        }



        // =====================================================
        // CONTROLLO RUOLO ADMIN
        // =====================================================

        Utente u =
                (Utente) session.getAttribute("utente");


        if (!"ADMIN".equalsIgnoreCase(
                u.getRuolo())) {


            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN
            );


            return;
        }



        // Recuperiamo l'azione dalla richiesta

        String azione =
                request.getParameter("azione");



        // =====================================================
        // ATTIVA / DISATTIVA VEICOLO
        // =====================================================

        if ("toggleAttivo".equals(azione)) {


            int id =
                    Integer.parseInt(
                            request.getParameter("id")
                    );


            boolean nuovoStato =
                    Boolean.parseBoolean(
                            request.getParameter("stato")
                    );


            vehicleDAO.updateAttivo(
                    id,
                    nuovoStato
            );


            // Dopo l'operazione torniamo alla dashboard
            // mostrando un messaggio di conferma

            response.sendRedirect(
                    request.getContextPath()
                    + "/AdminServlet?messaggio="
                    + (nuovoStato
                        ? "attivato"
                        : "disattivato")
            );


            return;
        }



        // =====================================================
        // PREPARA MODIFICA VEICOLO
        // =====================================================

        else if ("modifica".equals(azione)) {


            int id =
                    Integer.parseInt(
                            request.getParameter("id")
                    );


            Vehicle veicoloDaModificare =
                    vehicleDAO.doRetrieveByKey(id);


            request.setAttribute(
                    "veicoloDaModificare",
                    veicoloDaModificare
            );
        }



        // =====================================================
        // RECUPERA TUTTI I VEICOLI
        // =====================================================

        List<Vehicle> tuttiIVeicoli =
                vehicleDAO.getAllVehiclesAdmin();


        request.setAttribute(
                "listaVeicoliAdmin",
                tuttiIVeicoli
        );



        // =====================================================
        // FILTRI ORDINI
        // =====================================================

        String clienteParam =
                request.getParameter("cliente");


        String dataParam =
                request.getParameter("data");


        Integer idCliente = null;

        Date data = null;



        // =====================================================
        // CONVERSIONE ID CLIENTE
        // =====================================================

        if (clienteParam != null &&
                !clienteParam.trim().isEmpty()) {


            try {


                idCliente =
                        Integer.parseInt(
                                clienteParam
                        );


            } catch (NumberFormatException e) {


                request.setAttribute(
                        "erroreFiltro",
                        "L'ID cliente deve essere un numero."
                );

            }
        }



        // =====================================================
        // CONVERSIONE DATA
        // =====================================================

        if (dataParam != null &&
                !dataParam.trim().isEmpty()) {


            try {


                data =
                        Date.valueOf(
                                dataParam
                        );


            } catch (IllegalArgumentException e) {


                request.setAttribute(
                        "erroreFiltro",
                        "Data non valida."
                );

            }
        }



        // =====================================================
        // RECUPERA ORDINI
        // =====================================================

        List<Ordine> ordini =
                ordineDAO.doRetrieveFiltered(
                        idCliente,
                        data
                );


        request.setAttribute(
                "listaOrdiniAdmin",
                ordini
        );



        // =====================================================
        // MANTIENE I VALORI DEI FILTRI NEL FORM
        // =====================================================

        request.setAttribute(
                "clienteFiltro",
                clienteParam
        );


        request.setAttribute(
                "dataFiltro",
                dataParam
        );



        // =====================================================
        // FORWARD ALLA DASHBOARD
        // =====================================================

        request.getRequestDispatcher(
                "/admin/dashboard.jsp"
        ).forward(
                request,
                response
        );
    }



    // =========================================================
    // POST
    // =========================================================

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        HttpSession session =
                request.getSession(false);



        // =====================================================
        // CONTROLLO LOGIN
        // =====================================================

        if (session == null ||
                session.getAttribute("utente") == null) {


            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );


            return;
        }



        // =====================================================
        // CONTROLLO RUOLO ADMIN
        // =====================================================

        Utente u =
                (Utente) session.getAttribute("utente");


        if (!"ADMIN".equalsIgnoreCase(
                u.getRuolo())) {


            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN
            );


            return;
        }



        // Recuperiamo l'azione

        String azione =
                request.getParameter("azione");



        // =====================================================
        // AGGIUNGI VEICOLO
        // =====================================================

        if ("aggiungi".equals(azione)) {


            String marca =
                    request.getParameter("marca");


            String modello =
                    request.getParameter("modello");


            double prezzo =
                    Double.parseDouble(
                            request.getParameter("prezzo")
                    );


            String citta =
                    request.getParameter("citta");


            int iva =
                    Integer.parseInt(
                            request.getParameter("iva")
                    );



            Vehicle nuovo =
                    new Vehicle();


            nuovo.setMarca(marca);

            nuovo.setModello(modello);

            nuovo.setPrezzoGiornaliero(prezzo);

            nuovo.setCitta(citta);

            nuovo.setIva(iva);



            // Salvataggio nel database

            vehicleDAO.doSave(nuovo);



            // Torniamo alla dashboard con
            // messaggio di conferma

            response.sendRedirect(
                    request.getContextPath()
                    + "/AdminServlet?messaggio=aggiunto"
            );


            return;
        }



        // =====================================================
        // MODIFICA VEICOLO
        // =====================================================

        else if ("modifica".equals(azione)) {


            int id =
                    Integer.parseInt(
                            request.getParameter("id")
                    );


            String marca =
                    request.getParameter("marca");


            String modello =
                    request.getParameter("modello");


            double prezzo =
                    Double.parseDouble(
                            request.getParameter("prezzo")
                    );


            String citta =
                    request.getParameter("citta");


            int iva =
                    Integer.parseInt(
                            request.getParameter("iva")
                    );



            Vehicle veicolo =
                    new Vehicle();


            veicolo.setId(id);

            veicolo.setMarca(marca);

            veicolo.setModello(modello);

            veicolo.setPrezzoGiornaliero(prezzo);

            veicolo.setCitta(citta);

            veicolo.setIva(iva);



            // Aggiornamento database

            vehicleDAO.doUpdate(
                    veicolo
            );



            // Torniamo alla dashboard con
            // messaggio di conferma

            response.sendRedirect(
                    request.getContextPath()
                    + "/AdminServlet?messaggio=modificato"
            );


            return;
        }



        // Se arriva un'azione non riconosciuta

        response.sendRedirect(
                request.getContextPath()
                + "/AdminServlet"
        );
    }
}