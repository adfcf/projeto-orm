package io.github.adfcf.gestaocontratos.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexao {

    private static Connection connection;

    public static final String URL =
            "jdbc:mysql://db4free.net:3306/projeto_orm"
            + "?useUnicode=true"
            + "&useJDBCCompliantTimezoneShift=true"
            + "&serverTimezone=UTC"
            + "&autoReconnect=true";

    public static final String USER = "";
    public static final String PASSWORD = "";

    public static Connection obter() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException ex) {
                Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return connection;
    }

    private Conexao() {}

}
