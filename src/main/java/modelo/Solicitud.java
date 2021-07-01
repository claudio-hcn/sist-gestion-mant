package modelo;


public class Solicitud {
    private int idSolicitud;
    private String maquina;
    private String descripcion;
    private String observacion;
    private String solicitante;
    private String horaSolicitud;
    private String estado;
    private String motivoRechazo;
    private String notaSolicitud;
    
    public Solicitud(){};

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
    

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getHoraSolicitud() {
        return horaSolicitud;
    }

    public void setHoraSolicitud(String horaSolicitud) {
        this.horaSolicitud = horaSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }
    
    public String getNotaSolicitud() {
        return notaSolicitud;
    }

    public void setNotaSolicitud(String notaSolicitud) {
        this.notaSolicitud = notaSolicitud;
    }
    

    @Override
    public String toString() {
        return "Solicitud{" + "idSolicitud=" + idSolicitud + ", maquina=" + maquina + ", descripcion=" + descripcion + ", observacion=" + observacion + ", solicitante=" + solicitante + ", horaSolicitud=" + horaSolicitud + ", estado=" + estado + '}';
    }

   
    
            
    
}
