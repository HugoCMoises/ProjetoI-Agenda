package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/agenda_telefonica";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "123456";

    private DatabaseConnection() {
    }

    public static Connection conectar() throws SQLException {
        // Metodo usado pelas classes DAO para abrir a conexao com o PostgreSQL.
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
