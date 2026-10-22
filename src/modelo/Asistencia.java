/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;


import java.time.LocalDate;

public class Asistencia {
    private int idAsistencia=-1;
    private Socio socio;
    private ClaseGym clase;
    private LocalDate fechaAsistencia;

    public Asistencia() {
    }

    public Asistencia(Socio socio, ClaseGym clase, LocalDate fechaAsistencia) {
        this.socio = socio;
        this.clase = clase;
        this.fechaAsistencia = fechaAsistencia;
    }

    public Asistencia(int idAsistencia, Socio socio, ClaseGym clase, LocalDate fechaAsistencia) {
        this.idAsistencia = idAsistencia;
        this.socio = socio;
        this.clase = clase;
        this.fechaAsistencia = fechaAsistencia;
    }

    public int getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public ClaseGym getClase() {
        return clase;
    }

    public void setClase(ClaseGym clase) {
        this.clase = clase;
    }

    public LocalDate getFechaAsistencia() {
        return fechaAsistencia;
    }

    public void setFechaAsistencia(LocalDate fechaAsistencia) {
        this.fechaAsistencia = fechaAsistencia;
    }

 @Override
    public String toString() {
        return "Asistencia [ID=" + idAsistencia + ", Socio=" + socio.getApellido() + ", Clase=" + clase.getDiciplina() + ", Fecha=" + fechaAsistencia + "]";
    }
    
}