package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DriverManagerConnectionPool;
import model.Vehicle;

public class VehicleDAO {

    // 1. Recupera tutti i veicoli ATTIVI (per gli utenti)
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> veicoli = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "SELECT * FROM vehicles WHERE attivo = 1";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Vehicle v = new Vehicle();
                v.setId(rs.getInt("id"));
                v.setMarca(rs.getString("marca"));
                v.setModello(rs.getString("modello"));
                v.setPrezzoGiornaliero(rs.getDouble("prezzo_giornaliero"));
                v.setCitta(rs.getString("citta"));
                v.setIva(rs.getInt("iva"));
                v.setAttivo(rs.getBoolean("attivo"));
                veicoli.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return veicoli;
    }

    // 2. Filtra i veicoli per città
    public List<Vehicle> getVehiclesByCity(String city) {
        List<Vehicle> veicoli = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "SELECT * FROM vehicles WHERE citta = ? AND attivo = 1";
            ps = conn.prepareStatement(sql);
            ps.setString(1, city);
            rs = ps.executeQuery();

            while (rs.next()) {
                Vehicle v = new Vehicle();
                v.setId(rs.getInt("id"));
                v.setMarca(rs.getString("marca"));
                v.setModello(rs.getString("modello"));
                v.setPrezzoGiornaliero(rs.getDouble("prezzo_giornaliero"));
                v.setCitta(rs.getString("citta"));
                v.setIva(rs.getInt("iva"));
                v.setAttivo(rs.getBoolean("attivo"));
                veicoli.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return veicoli;
    }
    public List<Vehicle> searchVehicles(String citta, String marca, Double prezzoMax) {

        List<Vehicle> veicoli = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DriverManagerConnectionPool.getConnection();

            String sql = "SELECT * FROM vehicles " +
                         "WHERE attivo = 1 " +
                         "AND (? = '' OR citta = ?) " +
                         "AND (? = '' OR marca = ?) " +
                         "AND (? IS NULL OR prezzo_giornaliero <= ?)";

            ps = conn.prepareStatement(sql);

            ps.setString(1, citta);
            ps.setString(2, citta);

            ps.setString(3, marca);
            ps.setString(4, marca);

            if (prezzoMax == null) {
                ps.setNull(5, java.sql.Types.DOUBLE);
                ps.setNull(6, java.sql.Types.DOUBLE);
            } else {
                ps.setDouble(5, prezzoMax);
                ps.setDouble(6, prezzoMax);
            }

            rs = ps.executeQuery();

            while (rs.next()) {

                Vehicle v = new Vehicle();

                v.setId(rs.getInt("id"));
                v.setMarca(rs.getString("marca"));
                v.setModello(rs.getString("modello"));
                v.setPrezzoGiornaliero(rs.getDouble("prezzo_giornaliero"));
                v.setCitta(rs.getString("citta"));
                v.setIva(rs.getInt("iva"));
                v.setAttivo(rs.getBoolean("attivo"));

                veicoli.add(v);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return veicoli;
    }
    // 3. Cerca un singolo veicolo tramite ID
    public Vehicle doRetrieveByKey(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Vehicle v = null;

        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "SELECT * FROM vehicles WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                v = new Vehicle();
                v.setId(rs.getInt("id"));
                v.setMarca(rs.getString("marca"));
                v.setModello(rs.getString("modello"));
                v.setPrezzoGiornaliero(rs.getDouble("prezzo_giornaliero"));
                v.setCitta(rs.getString("citta"));
                v.setIva(rs.getInt("iva"));
                v.setAttivo(rs.getBoolean("attivo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return v;
    }

    // ==========================================
    //          METODI AGGIUNTI PER L'ADMIN
    // ==========================================

    // A. Recupera TUTTI i veicoli (sia attivi che disattivi) per la tabella di gestione admin
    public List<Vehicle> getAllVehiclesAdmin() {
        List<Vehicle> veicoli = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "SELECT * FROM vehicles"; // Nessun filtro su 'attivo'
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Vehicle v = new Vehicle();
                v.setId(rs.getInt("id"));
                v.setMarca(rs.getString("marca"));
                v.setModello(rs.getString("modello"));
                v.setPrezzoGiornaliero(rs.getDouble("prezzo_giornaliero"));
                v.setCitta(rs.getString("citta"));
                v.setIva(rs.getInt("iva"));
                v.setAttivo(rs.getBoolean("attivo"));
                veicoli.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return veicoli;
    }

    // B. Salva un nuovo veicolo nel database
    public boolean doSave(Vehicle v) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "INSERT INTO vehicles (marca, modello, prezzo_giornaliero, citta, iva, attivo) VALUES (?, ?, ?, ?, ?, 1)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, v.getMarca());
            ps.setString(2, v.getModello());
            ps.setDouble(3, v.getPrezzoGiornaliero());
            ps.setString(4, v.getCitta());
            ps.setInt(5, v.getIva());

            int rows = ps.executeUpdate();
            if (rows > 0) success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }
        return success;
    }
    public boolean doUpdate(Vehicle v) {

        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = DriverManagerConnectionPool.getConnection();

            String sql = "UPDATE vehicles " +
                         "SET marca = ?, modello = ?, prezzo_giornaliero = ?, " +
                         "citta = ?, iva = ? " +
                         "WHERE id = ?";

            ps = conn.prepareStatement(sql);

            ps.setString(1, v.getMarca());
            ps.setString(2, v.getModello());
            ps.setDouble(3, v.getPrezzoGiornaliero());
            ps.setString(4, v.getCitta());
            ps.setInt(5, v.getIva());
            ps.setInt(6, v.getId());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }

        return success;
    }

    // C. Soft Delete / Cambia stato di attivazione di un veicolo
    public boolean updateAttivo(int id, boolean attivo) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "UPDATE vehicles SET attivo = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setBoolean(1, attivo);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();
            if (rows > 0) success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }
        return success;
    }

    // Utility per chiudere le risorse JDBC ed evitare memory leak
    private static void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) DriverManagerConnectionPool.releaseConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<String> getSuggerimentiMarche(String testo) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<String> suggerimenti = new ArrayList<>();

        try {

            conn = DriverManagerConnectionPool.getConnection();

            String sql =
                    "SELECT DISTINCT marca " +
                    "FROM vehicles " +
                    "WHERE attivo = 1 " +
                    "AND marca LIKE ? " +
                    "ORDER BY marca " +
                    "LIMIT 5";

            ps = conn.prepareStatement(sql);

            ps.setString(1, testo + "%");

            rs = ps.executeQuery();

            while (rs.next()) {

                suggerimenti.add(
                        rs.getString("marca")
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            closeResources(conn, ps, rs);
        }

        return suggerimenti;
    }
}