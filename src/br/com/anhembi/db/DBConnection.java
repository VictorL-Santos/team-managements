package br.com.anhembi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // --- INFORMAÇÕES DE CONEXÃO ---
    // Altere os valores abaixo para os do seu banco de dados
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    private static Connection conn = null;

    /**
     * Obtém uma conexão com o banco de dados.
     * Se uma conexão já existir, ela será reutilizada.
     * @return um objeto Connection ou null se a conexão falhar.
     */
    public static Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            } catch (SQLException e) {
                System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
                return null;
            }
        }
        return conn;
    }

    /**
     * Fecha a conexão com o banco de dados.
     */
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                conn = null; // Garante que na próxima chamada de getConnection() uma nova conexão seja criada.
                System.out.println("Conexão com o banco de dados fechada com sucesso.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}
