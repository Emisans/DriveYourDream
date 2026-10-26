package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DriverManagerConnectionPool {

 
    private static List<Connection> freeDbConnections;

    static {
        freeDbConnections = new LinkedList<Connection>();
        try {
          
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver DB non trovato: " + e.getMessage());
        }
    }

   
    private static final String URL = environmentOrDefault("DB_URL", "jdbc:mysql://localhost:3306/progettotsw?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
    private static final String USER = environmentOrDefault("DB_USER", "driveyourdream");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    private static String environmentOrDefault(String name, String fallback) {
        String value = System.getenv(name);
        return value == null || value.isEmpty() ? fallback : value;
    }

   
    private static synchronized Connection createDBConnection() throws SQLException {
        if (PASSWORD == null) {
            throw new SQLException("Set the DB_PASSWORD environment variable before starting Tomcat.");
        }
        Connection newConnection = DriverManager.getConnection(URL, USER, PASSWORD);
        newConnection.setAutoCommit(true); 
        return newConnection;
    }

   
    public static synchronized Connection getConnection() throws SQLException {
        Connection connection;

        if (!freeDbConnections.isEmpty()) {
       
            connection = freeDbConnections.get(0);
            freeDbConnections.remove(0);

            try {
               
                if (connection.isClosed()) {
                    connection = getConnection();
                }
            } catch (SQLException e) {
                connection.close();
                connection = getConnection();
            }
        } else {
            
            connection = createDBConnection();
        }

        return connection;
    }

    
    public static synchronized void releaseConnection(Connection connection) throws SQLException {
        if (connection != null) {
         
            freeDbConnections.add(connection);
        }
    }
}
