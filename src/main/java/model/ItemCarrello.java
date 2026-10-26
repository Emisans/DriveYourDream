package model;

import java.io.Serializable;

// Implementa Serializable perché questo oggetto andrà a finire nella Sessione, 
// e Tomcat deve poterlo salvare in memoria se necessario.
public class ItemCarrello implements Serializable {
    private static final long serialVersionUID = 1L;

    private Vehicle veicolo; // L'oggetto veicolo reale preso dal DB
    private int giorni;      // La quantità (quanti giorni di noleggio)

    public ItemCarrello(Vehicle veicolo, int giorni) {
        this.veicolo = veicolo;
        this.giorni = giorni;
    }

    public Vehicle getVeicolo() {
        return veicolo;
    }

    public void setVeicolo(Vehicle veicolo) {
        this.veicolo = veicolo;
    }

    public int getGiorni() {
        return giorni;
    }

    public void setGiorni(int giorni) {
        this.giorni = giorni;
    }

    // Domanda d'esame: "Come calcoli il prezzo di questo singolo elemento?"
    // Risposta: Moltiplico il prezzo giornaliero dell'auto per i giorni di noleggio
    public double getPrezzoTotale() {
        return this.veicolo.getPrezzoGiornaliero() * this.giorni;
    }
}