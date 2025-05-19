
package modelo;

import java.util.Date;

public class OrdenDeInspeccion {
    private String numeroOrden;
    private Date fechaHoraFinalizacion;
    private Date fechaHoraCierre;
    private Estado estadoActual;
    private Empleado responsable;
    private EstacionSismologica estacion;

    public OrdenDeInspeccion(String numeroOrden, Date fechaHoraFinalizacion, Estado estadoActual, Empleado responsable, EstacionSismologica estacion) {
        this.numeroOrden = numeroOrden;
        this.fechaHoraFinalizacion = fechaHoraFinalizacion;
        this.estadoActual = estadoActual;
        this.responsable = responsable;
        this.estacion = estacion;
    }

    public boolean esMiRI(Empleado e) {
        return responsable.equals(e);
    }

    public boolean esCompletamenteRealizada() {
        return estadoActual.esCompletamenteRealizada();
    }

    public void setEstado(Estado nuevoEstado) {
        this.estadoActual = nuevoEstado;
    }

    public void cerrar(Date fechaCierre) {
        this.fechaHoraCierre = fechaCierre;
    }

    public EstacionSismologica getEstacion() {
        return estacion;
    }

    public String mostrarDatos() {
        return "Orden: " + numeroOrden + ", Finalizada: " + fechaHoraFinalizacion;
    }
}
