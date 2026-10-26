package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import model.DriverManagerConnectionPool;
import model.Utente;

public class UtenteDAO {

    // Trasforma la password in hash SHA-256
    private String hashPassword(String password) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(
                password.getBytes(StandardCharsets.UTF_8)
            );

            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    // SALVATAGGIO NUOVO UTENTE
    public boolean doSave(Utente utente) {

        Connection conn = null;
        PreparedStatement ps = null;
        boolean saved = false;

        try {

            conn = DriverManagerConnectionPool.getConnection();

            String sql =
                "INSERT INTO utente " +
                "(nome, cognome, email, password, ruolo) " +
                "VALUES (?, ?, ?, ?, ?)";

            ps = conn.prepareStatement(sql);

            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getCognome());
            ps.setString(3, utente.getEmail());

            // NON salviamo la password in chiaro
            ps.setString(4, hashPassword(utente.getPassword()));

            ps.setString(5, utente.getRuolo());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                saved = true;
            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            try {

                if (ps != null) {
                    ps.close();
                }

                DriverManagerConnectionPool.releaseConnection(conn);

            } catch (SQLException e) {

                e.printStackTrace();
            }
        }

        return saved;
    }


    // LOGIN
    public Utente doRetrieveByLogin(String email, String password) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        Utente utente = null;

        try {

            conn = DriverManagerConnectionPool.getConnection();

            String sql =
                "SELECT * FROM utente WHERE email = ? AND password = ?";

            ps = conn.prepareStatement(sql);

            ps.setString(1, email);

            // Confrontiamo l'hash della password inserita
            // con quello memorizzato nel database
            ps.setString(2, hashPassword(password));

            rs = ps.executeQuery();

            if (rs.next()) {

                utente = new Utente();

                utente.setId(rs.getInt("id"));
                utente.setNome(rs.getString("nome"));
                utente.setCognome(rs.getString("cognome"));
                utente.setEmail(rs.getString("email"));
                utente.setRuolo(rs.getString("ruolo"));
            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            try {

                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }

                DriverManagerConnectionPool.releaseConnection(conn);

            } catch (SQLException e) {

                e.printStackTrace();
            }
        }

        return utente;
    }


    // CONTROLLO EMAIL ESISTENTE
    public boolean checkEmailExists(String email) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        boolean exists = false;

        try {

            conn = DriverManagerConnectionPool.getConnection();

            String sql =
                "SELECT id FROM utente WHERE email = ?";

            ps = conn.prepareStatement(sql);

            ps.setString(1, email);

            rs = ps.executeQuery();

            if (rs.next()) {
                exists = true;
            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            try {

                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }

                DriverManagerConnectionPool.releaseConnection(conn);

            } catch (SQLException e) {

                e.printStackTrace();
            }
        }

        return exists;
    }
}