package DAO;

import Conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsistenciaDAO {
    
    public boolean registrarAsistenciaPersonal(int idPersonal, Date fecha, String estado) {
        try (Connection conn = Conexion.getConexion()) {
            // Verificar si ya existe
            String sqlCheck = "SELECT id_asistencia FROM asistencia_personal WHERE id_personal=? AND fecha=?";
            PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setInt(1, idPersonal);
            psCheck.setDate(2, fecha);
            ResultSet rs = psCheck.executeQuery();
            
            PreparedStatement ps;
            if (rs.next()) {
                // Actualizar
                ps = conn.prepareStatement(
                    "UPDATE asistencia_personal SET estado_asistencia=?, hora_registro=? WHERE id_personal=? AND fecha=?");
                ps.setString(1, estado);
                ps.setTime(2, new Time(System.currentTimeMillis()));
                ps.setInt(3, idPersonal);
                ps.setDate(4, fecha);
            } else {
                // Insertar
                ps = conn.prepareStatement(
                    "INSERT INTO asistencia_personal (id_personal, fecha, estado_asistencia, hora_registro) VALUES (?, ?, ?, ?)");
                ps.setInt(1, idPersonal);
                ps.setDate(2, fecha);
                ps.setString(3, estado);
                ps.setTime(4, new Time(System.currentTimeMillis()));
            }
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }
    
    // SIN ORDENAMIENTO - listado simple
    public List<Map<String, Object>> obtenerPersonalConAsistencia(Date fecha) {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = "SELECT p.id_personal, p.dni, p.nombres, p.apellidos, p.cargo, " +
                    "COALESCE(a.estado_asistencia, 'Ausente') as estado " +
                    "FROM personal p " +
                    "LEFT JOIN asistencia_personal a ON p.id_personal = a.id_personal AND a.fecha = ? " +
                    "WHERE p.estado = 'Activo'";
        
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDate(1, fecha);
            ResultSet rs = ps.executeQuery();
            
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
            System.err.println("Error: " + e.getMessage());
        }
        return lista;
    }
    
    // BÚSQUEDA SIMPLE por DNI, nombre o apellido
    public List<Map<String, Object>> buscarPersonal(Date fecha, String busqueda) {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = "SELECT p.id_personal, p.dni, p.nombres, p.apellidos, p.cargo, " +
                    "COALESCE(a.estado_asistencia, 'Ausente') as estado " +
                    "FROM personal p " +
                    "LEFT JOIN asistencia_personal a ON p.id_personal = a.id_personal AND a.fecha = ? " +
                    "WHERE p.estado = 'Activo' " +
                    "AND (p.dni LIKE ? OR p.nombres LIKE ? OR p.apellidos LIKE ?)";
        
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String patron = "%" + busqueda + "%";
            ps.setDate(1, fecha);
            ps.setString(2, patron);
            ps.setString(3, patron);
            ps.setString(4, patron);
            
            ResultSet rs = ps.executeQuery();
            
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
            System.err.println("Error: " + e.getMessage());
        }
        return lista;
    }
}