package DAO;

import Conexion.Conexion;
import Modelo.Estudiante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestión de Estudiantes - CRUD Completo
 */
public class EstudianteDAO {
    
    /**
     * Inserta un nuevo estudiante
     */
    public boolean insertar(Estudiante est) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = Conexion.getConexion();
            String sql = "INSERT INTO estudiantes (dni, nombres, apellidos, carrera, ciclo, turno) VALUES (?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, est.getDni());
            ps.setString(2, est.getNombres());
            ps.setString(3, est.getApellidos());
            ps.setString(4, est.getCarrera());
            ps.setString(5, est.getCiclo());
            ps.setString(6, est.getTurno());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al insertar estudiante: " + e.getMessage());
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
     * Actualiza los datos de un estudiante
     */
    public boolean actualizar(Estudiante est) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = Conexion.getConexion();
            String sql = "UPDATE estudiantes SET dni=?, nombres=?, apellidos=?, carrera=?, ciclo=?, turno=? WHERE id_estudiante=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, est.getDni());
            ps.setString(2, est.getNombres());
            ps.setString(3, est.getApellidos());
            ps.setString(4, est.getCarrera());
            ps.setString(5, est.getCiclo());
            ps.setString(6, est.getTurno());
            ps.setInt(7, est.getIdEstudiante());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar estudiante: " + e.getMessage());
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
     * Elimina un estudiante
     */
    public boolean eliminar(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = Conexion.getConexion();
            String sql = "DELETE FROM estudiantes WHERE id_estudiante=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar estudiante: " + e.getMessage());
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
     * Lista todos los estudiantes
     */
    public List<Estudiante> listarTodos() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        List<Estudiante> lista = new ArrayList<>();
        
        try {
            conn = Conexion.getConexion();
            String sql = "SELECT * FROM estudiantes WHERE estado='Activo' ORDER BY apellidos, nombres";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            
            while (rs.next()) {
                Estudiante est = new Estudiante();
                est.setIdEstudiante(rs.getInt("id_estudiante"));
                est.setDni(rs.getString("dni"));
                est.setNombres(rs.getString("nombres"));
                est.setApellidos(rs.getString("apellidos"));
                est.setCarrera(rs.getString("carrera"));
                est.setCiclo(rs.getString("ciclo"));
                est.setTurno(rs.getString("turno"));
                est.setEstado(rs.getString("estado"));
                lista.add(est);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar estudiantes: " + e.getMessage());
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
     * Filtra estudiantes por carrera, ciclo y turno
     */
    public List<Estudiante> filtrarPor(String carrera, String ciclo, String turno) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Estudiante> lista = new ArrayList<>();
        
        try {
            conn = Conexion.getConexion();
            
            // Construir consulta dinámica según filtros seleccionados
            StringBuilder sql = new StringBuilder("SELECT * FROM estudiantes WHERE estado='Activo'");
            
            if (carrera != null && !carrera.isEmpty() && !carrera.equals("Todos")) {
                sql.append(" AND carrera = ?");
            }
            if (ciclo != null && !ciclo.isEmpty() && !ciclo.equals("Todos")) {
                sql.append(" AND ciclo = ?");
            }
            if (turno != null && !turno.isEmpty() && !turno.equals("Todos")) {
                sql.append(" AND turno = ?");
            }
            
            sql.append(" ORDER BY apellidos, nombres");
            
            ps = conn.prepareStatement(sql.toString());
            
            // Asignar parámetros
            int paramIndex = 1;
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
                Estudiante est = new Estudiante();
                est.setIdEstudiante(rs.getInt("id_estudiante"));
                est.setDni(rs.getString("dni"));
                est.setNombres(rs.getString("nombres"));
                est.setApellidos(rs.getString("apellidos"));
                est.setCarrera(rs.getString("carrera"));
                est.setCiclo(rs.getString("ciclo"));
                est.setTurno(rs.getString("turno"));
                est.setEstado(rs.getString("estado"));
                lista.add(est);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al filtrar estudiantes: " + e.getMessage());
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
    
    /**
     * Busca un estudiante por ID
     */
    public Estudiante buscarPorId(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Estudiante est = null;
        
        try {
            conn = Conexion.getConexion();
            String sql = "SELECT * FROM estudiantes WHERE id_estudiante = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                est = new Estudiante();
                est.setIdEstudiante(rs.getInt("id_estudiante"));
                est.setDni(rs.getString("dni"));
                est.setNombres(rs.getString("nombres"));
                est.setApellidos(rs.getString("apellidos"));
                est.setCarrera(rs.getString("carrera"));
                est.setCiclo(rs.getString("ciclo"));
                est.setTurno(rs.getString("turno"));
                est.setEstado(rs.getString("estado"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar estudiante: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        
        return est;
    }
}