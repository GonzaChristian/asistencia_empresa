package DAO;

import Conexion.Conexion;
import Modelo.Asistencia;
import Modelo.Personal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO para gestión de Asistencias (Estudiantes y Personal)
 */
public class AsistenciaDAO {
    
    // ========== ASISTENCIA DE PERSONAL ==========
    
    /**
     * Registra o actualiza asistencia de personal
     */
    public boolean registrarAsistenciaPersonal(int idPersonal, Date fecha, String estado) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = Conexion.getConexion();
            
            // Verificar si ya existe asistencia para ese día
            String sqlCheck = "SELECT id_asistencia FROM asistencia_personal WHERE id_personal=? AND fecha=?";
            ps = conn.prepareStatement(sqlCheck);
            ps.setInt(1, idPersonal);
            ps.setDate(2, fecha);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                // Ya existe, actualizar
                ps.close();
                String sqlUpdate = "UPDATE asistencia_personal SET estado_asistencia=?, hora_registro=? WHERE id_personal=? AND fecha=?";
                ps = conn.prepareStatement(sqlUpdate);
                ps.setString(1, estado);
                ps.setTime(2, new Time(System.currentTimeMillis()));
                ps.setInt(3, idPersonal);
                ps.setDate(4, fecha);
            } else {
                // No existe, insertar
                ps.close();
                String sqlInsert = "INSERT INTO asistencia_personal (id_personal, fecha, estado_asistencia, hora_registro) VALUES (?, ?, ?, ?)";
                ps = conn.prepareStatement(sqlInsert);
                ps.setInt(1, idPersonal);
                ps.setDate(2, fecha);
                ps.setString(3, estado);
                ps.setTime(4, new Time(System.currentTimeMillis()));
            }
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar asistencia personal: " + e.getMessage());
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
     * Obtiene personal con su estado de asistencia para una fecha específica
     */
    public List<Map<String, Object>> obtenerPersonalConAsistencia(Date fecha) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String, Object>> lista = new ArrayList<>();
        
        try {
            conn = Conexion.getConexion();
            
            String sql = "SELECT p.id_personal, p.dni, p.nombres, p.apellidos, p.cargo, " +
                        "COALESCE(a.estado_asistencia, 'Ausente') as estado " +
                        "FROM personal p " +
                        "LEFT JOIN asistencia_personal a ON p.id_personal = a.id_personal AND a.fecha = ? " +
                        "WHERE p.estado = 'Activo' " +
                        "ORDER BY p.apellidos, p.nombres";
            
            ps = conn.prepareStatement(sql);
            ps.setDate(1, fecha);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("id_personal", rs.getInt("id_personal"));
                fila.put("dni", rs.getString("dni"));
                fila.put("nombres", rs.getString("nombres"));
                fila.put("apellidos", rs.getString("apellidos"));
                fila.put("cargo", rs.getString("cargo"));
                fila.put("estado", rs.getString("estado"));
                lista.add(fila);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener personal con asistencia: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        
        return lista;
    }
}