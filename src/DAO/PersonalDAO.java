package DAO;

import Conexion.Conexion;
import Modelo.Personal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestión de Personal - CRUD Completo
 */
public class PersonalDAO {
    
    /**
     * Inserta un nuevo personal
     */
    public boolean insertar(Personal per) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = Conexion.getConexion();
            String sql = "INSERT INTO personal (dni, nombres, apellidos, correo, telefono, cargo, horario) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, per.getDni());
            ps.setString(2, per.getNombres());
            ps.setString(3, per.getApellidos());
            ps.setString(4, per.getCorreo());
            ps.setString(5, per.getTelefono());
            ps.setString(6, per.getCargo());
            ps.setString(7, per.getHorario());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al insertar personal: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
    
    /**
     * Actualiza los datos de un personal
     */
    public boolean actualizar(Personal per) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = Conexion.getConexion();
            String sql = "UPDATE personal SET dni=?, nombres=?, apellidos=?, correo=?, telefono=?, cargo=?, horario=? WHERE id_personal=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, per.getDni());
            ps.setString(2, per.getNombres());
            ps.setString(3, per.getApellidos());
            ps.setString(4, per.getCorreo());
            ps.setString(5, per.getTelefono());
            ps.setString(6, per.getCargo());
            ps.setString(7, per.getHorario());
            ps.setInt(8, per.getIdPersonal());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar personal: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
    
    /**
     * Elimina un personal
     */
    public boolean eliminar(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = Conexion.getConexion();
            String sql = "DELETE FROM personal WHERE id_personal=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar personal: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
    
    /**
     * Lista todo el personal
     */
    public List<Personal> listarTodos() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        List<Personal> lista = new ArrayList<>();
        
        try {
            conn = Conexion.getConexion();
            String sql = "SELECT * FROM personal WHERE estado='Activo' ORDER BY apellidos, nombres";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            
            while (rs.next()) {
                Personal per = new Personal();
                per.setIdPersonal(rs.getInt("id_personal"));
                per.setDni(rs.getString("dni"));
                per.setNombres(rs.getString("nombres"));
                per.setApellidos(rs.getString("apellidos"));
                per.setCorreo(rs.getString("correo"));
                per.setTelefono(rs.getString("telefono"));
                per.setCargo(rs.getString("cargo"));
                per.setHorario(rs.getString("horario"));
                per.setEstado(rs.getString("estado"));
                lista.add(per);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar personal: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        
        return lista;
    }
    
    /**
     * Busca personal por ID
     */
    public Personal buscarPorId(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Personal per = null;
        
        try {
            conn = Conexion.getConexion();
            String sql = "SELECT * FROM personal WHERE id_personal = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                per = new Personal();
                per.setIdPersonal(rs.getInt("id_personal"));
                per.setDni(rs.getString("dni"));
                per.setNombres(rs.getString("nombres"));
                per.setApellidos(rs.getString("apellidos"));
                per.setCorreo(rs.getString("correo"));
                per.setTelefono(rs.getString("telefono"));
                per.setCargo(rs.getString("cargo"));
                per.setHorario(rs.getString("horario"));
                per.setEstado(rs.getString("estado"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar personal: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        
        return per;
    }
}