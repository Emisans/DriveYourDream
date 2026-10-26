package control;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.OrdineDAO;
import model.Carrello;
import model.ItemCarrello;
import model.ItemOrdine;
import model.Ordine;
import model.Utente;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
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
        Carrello carrello = (Carrello) session.getAttribute("carrello");

        // 2. Controllo carrello: deve esistere e non deve essere vuoto
        if (carrello == null || carrello.getElementi().isEmpty()) {
            response.sendRedirect("carrello.jsp");
            return;
        }

        // 3. Creazione dell'oggetto Ordine reale da salvare nel DB
        Ordine ordine = new Ordine();
        ordine.setIdUtente(utente.getId());
        ordine.setDataOrdine(new Timestamp(System.currentTimeMillis())); // Data e ora correnti

        // 4. Copiamo gli elementi dal Carrello del modello temporaneo all'Ordine storico
        List<ItemOrdine> elementiOrdine = new ArrayList<>();
        for (ItemCarrello itemCarrello : carrello.getElementi()) {
            ItemOrdine itemOrdine = new ItemOrdine();
            itemOrdine.setVeicolo(itemCarrello.getVeicolo());
            itemOrdine.setGiorni(itemCarrello.getGiorni());
            
            // Salviamo prezzo e IVA del momento per l'integrità storica!
            itemOrdine.setPrezzoStorico(itemCarrello.getVeicolo().getPrezzoGiornaliero());
            itemOrdine.setIvaStorica(itemCarrello.getVeicolo().getIva()); // Assicurati che Vehicle abbia il metodo getIva()

            elementiOrdine.add(itemOrdine);
        }
        ordine.setElementi(elementiOrdine);

        // 5. Salvataggio tramite DAO con transazione JDBC
        boolean successo = ordineDAO.doSave(ordine);

        if (successo) {
            // Se l'ordine è salvato con successo sul DB, svuotiamo il carrello dalla sessione
            session.removeAttribute("carrello");
            
            // Reindirizziamo alla pagina di successo passando un parametro di conferma
            response.sendRedirect("conferma-ordine.jsp?successo=true");
        } else {
            // Se qualcosa va storto, rimandiamo al carrello con un messaggio di errore
            request.setAttribute("erroreCheckout", "Si è verificato un errore durante il salvataggio della prenotazione. Riprova più tardi.");
            request.getRequestDispatcher("carrello.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}