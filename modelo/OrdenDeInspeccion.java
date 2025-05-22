package modelo;

import java.util.Date;
import java.util.List;

public class OrdenDeInspeccion {
    private String numero;
    private Date fechaHoraCierre;
    private Date fechaHoraFinalizacion;
    private Estado estado;
    private Empleado responsable;
    private EstacionSismologica estacion;
    private String observacionCierre;
    private List<MotivoFueraDeServicio> motivosCierre;

    public OrdenDeInspeccion(String numero, Date fechaHoraFinalizacion, Estado estado, Empleado responsable, EstacionSismologica estacion) {
        this.numero = numero;
        this.fechaHoraFinalizacion = fechaHoraFinalizacion;
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
        return estado.esCompletamenteRealizada();
    }

    public EstacionSismologica obtenerEstacion() {
        return estacion;
    }

    public String mostrarDatos() {
        return numero + " - " + fechaHoraFinalizacion;
    }

    public String getNumero() {
        return numero;
    }

    public Date getFechaHoraFinalizacion() {
        return fechaHoraFinalizacion;
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

    public void setFechaHoraFinalizacion(Date fechaHoraFinalizacion) {
        this.fechaHoraFinalizacion = fechaHoraFinalizacion;
    }

    public void cerrar(Date fecha, Estado estadoCerrado) {
        this.fechaHoraCierre = fecha;
        this.estado = estadoCerrado;
    }

    public void setObservacionCierre(String obs) {
        this.observacionCierre = obs;
    }

    public void setMotivosCierre(List<MotivoFueraDeServicio> motivos) {
        this.motivosCierre = motivos;
    }

    public String getObservacionCierre() {
        return observacionCierre;
    }

    public List<MotivoFueraDeServicio> getMotivosCierre() {
        return motivosCierre;
    }
}