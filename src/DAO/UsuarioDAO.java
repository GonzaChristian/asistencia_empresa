package DAO;

import Conexion.Conexion;
import Modelo.Usuario;
import java.sql.*;

/**
 * DAO para gestión de Usuarios
 */
public class UsuarioDAO {
    
    /**
     * Valida el login del usuario
     * @param usuario Usuario a validar
     * @return Usuario si las credenciales son correctas, null si no
     */
    public Usuario validarLogin(String usuario, String contrasena) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Usuario usuarioValidado = null;
        
        try {
            conn = Conexion.getConexion();
            String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                usuarioValidado = new Usuario();
                usuarioValidado.setIdUsuario(rs.getInt("id_usuario"));
                usuarioValidado.setUsuario(rs.getString("usuario"));
                usuarioValidado.setContrasena(rs.getString("contrasena"));
                usuarioValidado.setFechaRegistro(rs.getTimestamp("fecha_registro"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al validar login: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        
        return usuarioValidado;
    }
}