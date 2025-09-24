package br.com.anhembi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // --- INFORMAÇÕES DE CONEXÃO ---
    // Altere os valores abaixo para os do seu banco de dados
    private static final String URL = "jdbc:postgresql://localhost:5432/team_management_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    /**
     * Obtém uma NOVA conexão com o banco de dados a cada chamada.
     * @return um novo objeto Connection.
     * @throws SQLException se a conexão falhar (por exemplo, se o banco estiver offline ou as credenciais estiverem erradas).
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}