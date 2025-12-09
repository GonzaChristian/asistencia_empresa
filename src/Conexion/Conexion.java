package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    public static Connection getConexion() {
        Connection conex = null;
        try {
            String url = "jdbc:mysql://localhost:3306/sistema_asistencia";
            String user = "root";
            String pass = "123456";

            conex = DriverManager.getConnection(url, user, pass);
            System.out.println("Conectado a MySQL");
        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
        return conex;
    }
}
