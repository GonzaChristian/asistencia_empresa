package DAO;

import Conexion.Conexion;
import Modelo.Personal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonalDAO {
    
    public boolean insertar(Personal per) {
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(
                 "INSERT INTO personal (dni, nombres, apellidos, correo, telefono, cargo, horario) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            
            ps.setString(1, per.getDni());
            ps.setString(2, per.getNombres());
            ps.setString(3, per.getApellidos());
            ps.setString(4, per.getCorreo());
            ps.setString(5, per.getTelefono());
            ps.setString(6, per.getCargo());
            ps.setString(7, per.getHorario());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }
    
    public boolean actualizar(Personal per) {
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(
                 "UPDATE personal SET dni=?, nombres=?, apellidos=?, correo=?, telefono=?, cargo=?, horario=? WHERE id_personal=?")) {
            
            ps.setString(1, per.getDni());
            ps.setString(2, per.getNombres());
            ps.setString(3, per.getApellidos());
            ps.setString(4, per.getCorreo());
            ps.setString(5, per.getTelefono());
            ps.setString(6, per.getCargo());
            ps.setString(7, per.getHorario());
            ps.setInt(8, per.getIdPersonal());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }
    
    public boolean eliminar(int id) {
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM personal WHERE id_personal=?")) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }
    
    // SIN ORDENAMIENTO - listado simple
    public List<Personal> listarTodos() {
        List<Personal> lista = new ArrayList<>();
        try (Connection conn = Conexion.getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM personal WHERE estado='Activo'")) {
            
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
            System.err.println("Error: " + e.getMessage());
        }
        return lista;
    }
    
    public Personal buscarPorId(int id) {
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM personal WHERE id_personal = ?")) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
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
                return per;
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }
}