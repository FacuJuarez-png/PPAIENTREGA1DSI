
package modelo;

import java.util.Date;

public class OrdenDeInspeccion {
    private String numero;
    private Date fechaHoraFin;
    private Estado estado;
    private Empleado responsable;
    private EstacionSismologica estacion;

public OrdenDeInspeccion(String numero, Date fechaHoraFin, Estado estado, Empleado responsable, EstacionSismologica estacion) {
    this.numero = numero;
    this.fechaHoraFin = fechaHoraFin;
    this.estado = estado;
    this.responsable = responsable;
    this.estacion = estacion;
}
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public boolean esMiRI(Empleado e) {
        return this.responsable.equals(e);
    }

    public boolean esCompletamenteRealizada() {
        return estado.getNombre().equalsIgnoreCase("CompletamenteRealizada");
    }

    public EstacionSismologica obtenerEstacion() {
        return estacion;
    }

    public String mostrarDatos() {
        return numero + " - " + fechaHoraFin;
    }

    public String getNumero() {
        return numero;
    }

    public Date getFechaHoraFin() {
        return fechaHoraFin;
    }

    public Estado obtenerEstado() {
        return estado;
    }

    

    public Empleado obtenerResponsable() {
        return responsable;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setFechaHoraFin(Date fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }
    public EstacionSismologica getEstacion() { return estacion; }

    public void cerrar(Date fechaHoraFin, Estado estadoCerrado) {
    this.fechaHoraFin = fechaHoraFin;
    this.estado = estadoCerrado;
}
}
