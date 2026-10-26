package model;

import java.io.Serializable;

public class ItemOrdine implements Serializable {
    private static final long serialVersionUID = 1L;

    private Vehicle veicolo; // Mappa id_veicolo
    private int giorni;      // Mappa giorni
    private double prezzoStorico; // Mappa prezzo_storico (integrità storica)
    private int ivaStorica;       // Mappa iva_storica (integrità storica)

    public ItemOrdine() {}

    public Vehicle getVeicolo() { return veicolo; }
    public void setVeicolo(Vehicle veicolo) { this.veicolo = veicolo; }

    public int getGiorni() { return giorni; }
    public void setGiorni(int giorni) { this.giorni = giorni; }

    public double getPrezzoStorico() { return prezzoStorico; }
    public void setPrezzoStorico(double prezzoStorico) { this.prezzoStorico = prezzoStorico; }

    public int getIvaStorica() { return ivaStorica; }
    public void setIvaStorica(int ivaStorica) { this.ivaStorica = ivaStorica; }

    // Calcola il subtotale di questa singola riga d'ordine
    public double getSubtotale() {
        return this.prezzoStorico * this.giorni;
    }
}