package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.DriverManagerConnectionPool;
import model.ItemOrdine;
import model.Ordine;
import model.Vehicle;

public class OrdineDAO {

    // =========================================================
    // SALVA UN ORDINE
    // =========================================================
    public boolean doSave(Ordine ordine) {

        Connection con = null;
        PreparedStatement psOrdine = null;
        PreparedStatement psDettaglio = null;
        ResultSet rs = null;

        String queryOrdine =
                "INSERT INTO ordine (id_utente, data_ordine) VALUES (?, ?)";

        String queryDettaglio =
                "INSERT INTO dettaglio_ordine " +
                "(id_ordine, id_veicolo, giorni, prezzo_storico, iva_storica) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {

            con = DriverManagerConnectionPool.getConnection();
            con.setAutoCommit(false);

            // Salviamo prima l'ordine
            psOrdine = con.prepareStatement(
                    queryOrdine,
                    Statement.RETURN_GENERATED_KEYS
            );

            psOrdine.setInt(1, ordine.getIdUtente());
            psOrdine.setTimestamp(2, ordine.getDataOrdine());

            int affectedRows = psOrdine.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException(
                        "La creazione dell'ordine è fallita."
                );
            }

            // Recuperiamo l'ID generato
            rs = psOrdine.getGeneratedKeys();

            int idOrdineGenerato;

            if (rs.next()) {
                idOrdineGenerato = rs.getInt(1);
            } else {
                throw new SQLException(
                        "Nessun ID ordine generato."
                );
            }

            // Salviamo i dettagli
            psDettaglio = con.prepareStatement(queryDettaglio);

            for (ItemOrdine item : ordine.getElementi()) {

                psDettaglio.setInt(1, idOrdineGenerato);

                if (item.getVeicolo() != null) {
                    psDettaglio.setInt(
                            2,
                            item.getVeicolo().getId()
                    );
                } else {
                    psDettaglio.setNull(
                            2,
                            java.sql.Types.INTEGER
                    );
                }

                psDettaglio.setInt(
                        3,
                        item.getGiorni()
                );

                psDettaglio.setDouble(
                        4,
                        item.getPrezzoStorico()
                );

                psDettaglio.setInt(
                        5,
                        item.getIvaStorica()
                );

                psDettaglio.addBatch();
            }

            psDettaglio.executeBatch();

            con.commit();

            return true;

        } catch (SQLException e) {

            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            e.printStackTrace();

            return false;

        } finally {

            try {

                if (rs != null)
                    rs.close();

                if (psOrdine != null)
                    psOrdine.close();

                if (psDettaglio != null)
                    psDettaglio.close();

                if (con != null)
                    DriverManagerConnectionPool.releaseConnection(con);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // =========================================================
    // RECUPERA ORDINI DI UN SINGOLO UTENTE
    // =========================================================
    public List<Ordine> doRetrieveByUtente(int idUtente) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Ordine> ordini = new ArrayList<>();

        String query =
                "SELECT o.id AS id_ordine, o.data_ordine, " +
                "d.giorni, d.prezzo_storico, d.iva_storica, " +
                "v.id AS id_veicolo, v.marca, v.modello, v.citta " +
                "FROM ordine o " +
                "JOIN dettaglio_ordine d ON o.id = d.id_ordine " +
                "LEFT JOIN vehicles v ON d.id_veicolo = v.id " +
                "WHERE o.id_utente = ? " +
                "ORDER BY o.data_ordine DESC";

        try {

            con = DriverManagerConnectionPool.getConnection();

            ps = con.prepareStatement(query);

            ps.setInt(1, idUtente);

            rs = ps.executeQuery();

            Ordine ordineCorrente = null;

            while (rs.next()) {

                int idOrdine =
                        rs.getInt("id_ordine");

                if (ordineCorrente == null ||
                        ordineCorrente.getId() != idOrdine) {

                    ordineCorrente = new Ordine();

                    ordineCorrente.setId(idOrdine);
                    ordineCorrente.setIdUtente(idUtente);

                    ordineCorrente.setDataOrdine(
                            rs.getTimestamp("data_ordine")
                    );

                    ordini.add(ordineCorrente);
                }

                Vehicle v = creaVeicolo(rs);

                ItemOrdine item = creaItemOrdine(rs, v);

                ordineCorrente.addElemento(item);
            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            chiudiRisorse(con, ps, rs);
        }

        return ordini;
    }


    // =========================================================
    // RECUPERA TUTTI GLI ORDINI - ADMIN
    // =========================================================
    public List<Ordine> doRetrieveAll() {

        return doRetrieveFiltered(null, null);
    }


    // =========================================================
    // FILTRA ORDINI PER CLIENTE E/O DATA - ADMIN
    // =========================================================
    public List<Ordine> doRetrieveFiltered(
            Integer idUtente,
            Date data) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Ordine> ordini = new ArrayList<>();


        String query =
                "SELECT o.id AS id_ordine, " +
                "o.id_utente, " +
                "o.data_ordine, " +
                "d.giorni, " +
                "d.prezzo_storico, " +
                "d.iva_storica, " +
                "v.id AS id_veicolo, " +
                "v.marca, " +
                "v.modello, " +
                "v.citta " +

                "FROM ordine o " +

                "JOIN dettaglio_ordine d " +
                "ON o.id = d.id_ordine " +

                "LEFT JOIN vehicles v " +
                "ON d.id_veicolo = v.id " +

                "WHERE (? IS NULL OR o.id_utente = ?) " +

                "AND (? IS NULL OR DATE(o.data_ordine) = ?) " +

                "ORDER BY o.data_ordine DESC";


        try {

            con =
                    DriverManagerConnectionPool.getConnection();

            ps =
                    con.prepareStatement(query);


            // -------------------------
            // FILTRO CLIENTE
            // -------------------------

            if (idUtente == null) {

                ps.setNull(
                        1,
                        java.sql.Types.INTEGER
                );

                ps.setNull(
                        2,
                        java.sql.Types.INTEGER
                );

            } else {

                ps.setInt(
                        1,
                        idUtente
                );

                ps.setInt(
                        2,
                        idUtente
                );
            }


            // -------------------------
            // FILTRO DATA
            // -------------------------

            if (data == null) {

                ps.setNull(
                        3,
                        java.sql.Types.DATE
                );

                ps.setNull(
                        4,
                        java.sql.Types.DATE
                );

            } else {

                ps.setDate(
                        3,
                        data
                );

                ps.setDate(
                        4,
                        data
                );
            }


            rs =
                    ps.executeQuery();


            Ordine ordineCorrente = null;


            while (rs.next()) {

                int idOrdine =
                        rs.getInt("id_ordine");


                if (ordineCorrente == null ||
                        ordineCorrente.getId() != idOrdine) {

                    ordineCorrente =
                            new Ordine();

                    ordineCorrente.setId(
                            idOrdine
                    );

                    ordineCorrente.setIdUtente(
                            rs.getInt("id_utente")
                    );

                    ordineCorrente.setDataOrdine(
                            rs.getTimestamp("data_ordine")
                    );

                    ordini.add(
                            ordineCorrente
                    );
                }


                Vehicle v =
                        creaVeicolo(rs);


                ItemOrdine item =
                        creaItemOrdine(rs, v);


                ordineCorrente.addElemento(
                        item
                );
            }


        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            chiudiRisorse(
                    con,
                    ps,
                    rs
            );
        }


        return ordini;
    }


    // =========================================================
    // CREA OGGETTO VEHICLE DAL RESULTSET
    // =========================================================
    private Vehicle creaVeicolo(ResultSet rs)
            throws SQLException {

        Vehicle v =
                new Vehicle();

        v.setId(
                rs.getInt("id_veicolo")
        );

        v.setMarca(
                rs.getString("marca") != null
                        ? rs.getString("marca")
                        : "Veicolo non più disponibile"
        );

        v.setModello(
                rs.getString("modello") != null
                        ? rs.getString("modello")
                        : ""
        );

        v.setCitta(
                rs.getString("citta")
        );

        return v;
    }


    // =========================================================
    // CREA ITEM ORDINE DAL RESULTSET
    // =========================================================
    private ItemOrdine creaItemOrdine(
            ResultSet rs,
            Vehicle v)
            throws SQLException {

        ItemOrdine item =
                new ItemOrdine();

        item.setVeicolo(v);

        item.setGiorni(
                rs.getInt("giorni")
        );

        item.setPrezzoStorico(
                rs.getDouble("prezzo_storico")
        );

        item.setIvaStorica(
                rs.getInt("iva_storica")
        );

        return item;
    }


    // =========================================================
    // CHIUSURA RISORSE JDBC
    // =========================================================
    private void chiudiRisorse(
            Connection con,
            PreparedStatement ps,
            ResultSet rs) {

        try {

            if (rs != null)
                rs.close();

            if (ps != null)
                ps.close();

            if (con != null)
                DriverManagerConnectionPool.releaseConnection(con);

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}