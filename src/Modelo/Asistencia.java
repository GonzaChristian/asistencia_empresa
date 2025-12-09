package Modelo;

import java.sql.Date;
import java.sql.Time;

/**
 * Clase modelo para Asistencia (Personal y Estudiantes)
 */
public class Asistencia {
    
    private int idAsistencia;
    private int idPersona; 
    private Date fecha;
    private String estadoAsistencia;
    private Time horaRegistro;
    private String observaciones;
    
    // Constructor vacío
    public Asistencia() {
    }
    
    // Constructor para crear nueva asistencia
    public Asistencia(int idPersona, Date fecha, String estadoAsistencia) {
        this.idPersona = idPersona;
        this.fecha = fecha;
        this.estadoAsistencia = estadoAsistencia;
    }
    
    // Constructor completo
    public Asistencia(int idAsistencia, int idPersona, Date fecha, 
                     String estadoAsistencia, Time horaRegistro, String observaciones) {
        this.idAsistencia = idAsistencia;
        this.idPersona = idPersona;
        this.fecha = fecha;
        this.estadoAsistencia = estadoAsistencia;
        this.horaRegistro = horaRegistro;
        this.observaciones = observaciones;
    }
    
    // Getters y Setters
    public int getIdAsistencia() {
        return idAsistencia;
    }
    
    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }
    
    public int getIdPersona() {
        return idPersona;
    }
    
    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }
    
    public Date getFecha() {
        return fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public String getEstadoAsistencia() {
        return estadoAsistencia;
    }
    
    public void setEstadoAsistencia(String estadoAsistencia) {
        this.estadoAsistencia = estadoAsistencia;
    }
    
    public Time getHoraRegistro() {
        return horaRegistro;
    }
    
    public void setHoraRegistro(Time horaRegistro) {
        this.horaRegistro = horaRegistro;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    @Override
    public String toString() {
        return "Asistencia{" +
                "id=" + idAsistencia +
                ", idPersona=" + idPersona +
                ", fecha=" + fecha +
                ", estado='" + estadoAsistencia + '\'' +
                ", hora=" + horaRegistro +
                '}';
    }
}