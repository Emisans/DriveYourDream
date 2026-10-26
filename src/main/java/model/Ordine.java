package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Ordine implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;                 // Mappa id
    private int idUtente;           // Mappa id_utente
    private Timestamp dataOrdine;   // Mappa data_ordine (DATETIME nel DB)
    private List<ItemOrdine> elementi; // Lista dei dettagli associati

    public Ordine() {
        this.elementi = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdUtente() { return idUtente; }
    public void setIdUtente(int idUtente) { this.idUtente = idUtente; }

    public Timestamp getDataOrdine() { return dataOrdine; }
    public void setDataOrdine(Timestamp dataOrdine) { this.dataOrdine = dataOrdine; }

    public List<ItemOrdine> getElementi() { return elementi; }
    public void setElementi(List<ItemOrdine> elementi) { this.elementi = elementi; }

    public void addElemento(ItemOrdine item) {
        this.elementi.add(item);
    }

    // Calcola al volo il totale complessivo sommando i subtotali di tutti i dettagli
    public double getTotaleComplessivo() {
        double tot = 0;
        for (ItemOrdine item : elementi) {
            tot += item.getSubtotale();
        }
        return tot;
    }
}