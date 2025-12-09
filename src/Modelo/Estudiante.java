package Modelo;

import java.sql.Timestamp;

/**
 * Clase modelo para Estudiante
 */
public class Estudiante {
    
    private int idEstudiante;
    private String dni;
    private String nombres;
    private String apellidos;
    private String carrera;
    private String ciclo;
    private String turno;
    private String estado;
    private Timestamp fechaRegistro;
    
    // Constructor vacío
    public Estudiante() {
    }
    
    // Constructor con parámetros principales
    public Estudiante(String dni, String nombres, String apellidos, 
                     String carrera, String ciclo, String turno) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.carrera = carrera;
        this.ciclo = ciclo;
        this.turno = turno;
        this.estado = "Activo";
    }
    
    // Constructor completo
    public Estudiante(int idEstudiante, String dni, String nombres, String apellidos, 
                     String carrera, String ciclo, String turno, String estado) {
        this.idEstudiante = idEstudiante;
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.carrera = carrera;
        this.ciclo = ciclo;
        this.turno = turno;
        this.estado = estado;
    }
    
    // Getters y Setters
    public int getIdEstudiante() {
        return idEstudiante;
    }
    
    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
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
    
    public String getCarrera() {
        return carrera;
    }
    
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
    
    public String getCiclo() {
        return ciclo;
    }
    
    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }
    
    public String getTurno() {
        return turno;
    }
    
    public void setTurno(String turno) {
        this.turno = turno;
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
     * Obtiene el nombre completo del estudiante
     */
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
    
    @Override
    public String toString() {
        return "Estudiante{" +
                "id=" + idEstudiante +
                ", dni='" + dni + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", carrera='" + carrera + '\'' +
                ", ciclo='" + ciclo + '\'' +
                ", turno='" + turno + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}