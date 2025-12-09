package DAO;

import Conexion.Conexion;
import Modelo.Asistencia;
import Modelo.Estudiante;
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
    
    // ========== ASISTENCIA DE ESTUDIANTES ==========
    
    /**
     * Registra o actualiza asistencia de un estudiante
     */
    public boolean registrarAsistenciaEstudiante(int idEstudiante, Date fecha, String estado) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = Conexion.getConexion();
            
            // Verificar si ya existe asistencia para ese día
            String sqlCheck = "SELECT id_asistencia FROM asistencia_estudiantes WHERE id_estudiante=? AND fecha=?";
            ps = conn.prepareStatement(sqlCheck);
            ps.setInt(1, idEstudiante);
            ps.setDate(2, fecha);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                // Ya existe, actualizar
                ps.close();
                String sqlUpdate = "UPDATE asistencia_estudiantes SET estado_asistencia=?, hora_registro=? WHERE id_estudiante=? AND fecha=?";
                ps = conn.prepareStatement(sqlUpdate);
                ps.setString(1, estado);
                ps.setTime(2, new Time(System.currentTimeMillis()));
                ps.setInt(3, idEstudiante);
                ps.setDate(4, fecha);
            } else {
                // No existe, insertar
                ps.close();
                String sqlInsert = "INSERT INTO asistencia_estudiantes (id_estudiante, fecha, estado_asistencia, hora_registro) VALUES (?, ?, ?, ?)";
                ps = conn.prepareStatement(sqlInsert);
                ps.setInt(1, idEstudiante);
                ps.setDate(2, fecha);
                ps.setString(3, estado);
                ps.setTime(4, new Time(System.currentTimeMillis()));
            }
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar asistencia estudiante: " + e.getMessage());
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
     * Obtiene estudiantes con su estado de asistencia para una fecha específica
     * Filtra por carrera, ciclo y turno
     */
    public List<Map<String, Object>> obtenerEstudiantesConAsistencia(Date fecha, String carrera, String ciclo, String turno) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String, Object>> lista = new ArrayList<>();
        
        try {
            conn = Conexion.getConexion();
            
            // Construir consulta con LEFT JOIN
            StringBuilder sql = new StringBuilder(
                "SELECT e.id_estudiante, e.dni, e.nombres, e.apellidos, e.carrera, e.ciclo, e.turno, " +
                "COALESCE(a.estado_asistencia, 'Ausente') as estado " +
                "FROM estudiantes e " +
                "LEFT JOIN asistencia_estudiantes a ON e.id_estudiante = a.id_estudiante AND a.fecha = ? " +
                "WHERE e.estado = 'Activo'"
            );
            
            if (carrera != null && !carrera.isEmpty() && !carrera.equals("Todos")) {
                sql.append(" AND e.carrera = ?");
            }
            if (ciclo != null && !ciclo.isEmpty() && !ciclo.equals("Todos")) {
                sql.append(" AND e.ciclo = ?");
            }
            if (turno != null && !turno.isEmpty() && !turno.equals("Todos")) {
                sql.append(" AND e.turno = ?");
            }
            
            sql.append(" ORDER BY e.apellidos, e.nombres");
            
            ps = conn.prepareStatement(sql.toString());
            ps.setDate(1, fecha);
            
            // Asignar parámetros
            int paramIndex = 2;
            if (carrera != null && !carrera.isEmpty() && !carrera.equals("Todos")) {
                ps.setString(paramIndex++, carrera);
            }
            if (ciclo != null && !ciclo.isEmpty() && !ciclo.equals("Todos")) {
                ps.setString(paramIndex++, ciclo);
            }
            if (turno != null && !turno.isEmpty() && !turno.equals("Todos")) {
                ps.setString(paramIndex++, turno);
            }
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("id_estudiante", rs.getInt("id_estudiante"));
                fila.put("dni", rs.getString("dni"));
                fila.put("nombres", rs.getString("nombres"));
                fila.put("apellidos", rs.getString("apellidos"));
                fila.put("carrera", rs.getString("carrera"));
                fila.put("ciclo", rs.getString("ciclo"));
                fila.put("turno", rs.getString("turno"));
                fila.put("estado", rs.getString("estado"));
                lista.add(fila);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener estudiantes con asistencia: " + e.getMessage());
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