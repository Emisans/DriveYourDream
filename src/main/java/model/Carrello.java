package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Carrello implements Serializable {
    private static final long serialVersionUID = 1L;

    // Usiamo una List per mantenere gli elementi nel carrello
    private List<ItemCarrello> elementi;

    public Carrello() {
        this.elementi = new ArrayList<>();
    }

    public List<ItemCarrello> getElementi() {
        return elementi;
    }

    /**
     * Metodo per aggiungere un veicolo al carrello.
     * Se il veicolo è già presente, aumenta semplicemente i giorni! (Evita duplicati brutti)
     */
    public void aggiungiElemento(Vehicle veicolo, int giorni) {
        // Controlliamo se questa auto è già nel carrello
        for (ItemCarrello item : elementi) {
            if (item.getVeicolo().getId() == veicolo.getId()) {
                // Se c'è già, sommiamo i nuovi giorni a quelli vecchi
                item.setGiorni(item.getGiorni() + giorni);
                return; // Esce dal metodo senza aggiungere una nuova riga
            }
        }
        // Se non c'era, creiamo un nuovo Item e lo aggiungiamo alla lista
        elementi.add(new ItemCarrello(veicolo, giorni));
    }

    /**
     * Metodo per rimuovere un veicolo dal carrello in base al suo ID
     */
    public void rimuoviElemento(int idVeicolo) {
        elementi.removeIf(item -> item.getVeicolo().getId() == idVeicolo);
    }
    /**
     * Modifica il numero di giorni di un veicolo già presente nel carrello.
     */
    public void modificaGiorni(int idVeicolo, int nuoviGiorni) {

        if (nuoviGiorni < 1) {
            return;
        }

        for (ItemCarrello item : elementi) {

            if (item.getVeicolo().getId() == idVeicolo) {
                item.setGiorni(nuoviGiorni);
                return;
            }
        }
    }

    /**
     * Calcola il prezzo totale di tutto il carrello (Somma di tutti gli elementi)
     */
    public double getTotaleComplessivo() {
        double totale = 0;
        for (ItemCarrello item : elementi) {
            totale += item.getPrezzoTotale();
        }
        return totale;
    }
}