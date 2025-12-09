package Modelo;

import java.sql.Timestamp;

/**
 * Clase modelo para Personal
 */
public class Personal {
    
    private int idPersonal;
    private String dni;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private String cargo;
    private String horario;
    private String estado;
    private Timestamp fechaRegistro;
    
    // Constructor vacío
    public Personal() {
    }
    
    // Constructor con parámetros principales
    public Personal(String dni, String nombres, String apellidos, 
                   String correo, String telefono, String cargo, String horario) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.cargo = cargo;
        this.horario = horario;
        this.estado = "Activo";
    }
    
    // Constructor completo
    public Personal(int idPersonal, String dni, String nombres, String apellidos, 
                   String correo, String telefono, String cargo, String horario, String estado) {
        this.idPersonal = idPersonal;
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.cargo = cargo;
        this.horario = horario;
        this.estado = estado;
    }
    
    // Getters y Setters
    public int getIdPersonal() {
        return idPersonal;
    }
    
    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }
    
    public String getDni() {
        return dni;
    }
    
    public void setDni(String dni) {
        this.dni = dni;
    }
    
    public String getNombres() {
        return nombres;
    }
    
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    
    public String getApellidos() {
        return apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getCargo() {
        return cargo;
    }
    
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
    public String getHorario() {
        return horario;
    }
    
    public void setHorario(String horario) {
        this.horario = horario;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    /**
     * Obtiene el nombre completo del personal
     */
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
    
    @Override
    public String toString() {
        return "Personal{" +
                "id=" + idPersonal +
                ", dni='" + dni + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", cargo='" + cargo + '\'' +
                ", horario='" + horario + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}