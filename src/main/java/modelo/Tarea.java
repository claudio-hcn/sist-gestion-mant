/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;


import java.text.SimpleDateFormat;

/**
 *
 * @author Claudio
 */
public class Tarea {
    private int id_tarea;
    private String maquina;
    private String nombreTarea;
    private String duracionEstimada;
    private String prioridad;
    private String tipoTarea;
    private String clasificacion1;
    private String clasificacion2;
    private String activador;
    private String fechaProgramada;

    public Tarea() {
    }
    
    public int getId_tarea() {
        return id_tarea;
    }

    public void setId_tarea(int id_tarea) {
        this.id_tarea = id_tarea;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    public String getDuracionEstimada() {
        return duracionEstimada;
    }

    public void setDuracionEstimada(String duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getTipoTarea() {
        return tipoTarea;
    }

    public void setTipoTarea(String tipoTarea) {
        this.tipoTarea = tipoTarea;
    }

    public String getClasificacion1() {
        return clasificacion1;
    }

    public void setClasificacion1(String clasificacion1) {
        this.clasificacion1 = clasificacion1;
    }

    public String getClasificacion2() {
        return clasificacion2;
    }

    public void setClasificacion2(String clasificacion2) {
        this.clasificacion2 = clasificacion2;
    }

    public String getActivador() {
        return activador;
    }

    public void setActivador(String activador) {
        this.activador = activador;
    }

    public String getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(String fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    
    


  

    @Override
    public String toString() {
        return "Tarea{" + "id_tarea=" + id_tarea + ", maquina=" + maquina + ", nombreTarea=" + nombreTarea + ", duracionEstimada=" + duracionEstimada + ", prioridad=" + prioridad + ", tipoTarea=" + tipoTarea + ", clasificacion1=" + clasificacion1 + ", clasificacion2=" + clasificacion2 + ", activador=" + activador + ", fechaProgramada=" + fechaProgramada + '}';
    }


    
    
    
    
    
    
    
    
}
