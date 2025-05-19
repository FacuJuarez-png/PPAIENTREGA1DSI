
package gestor;

import modelo.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class gestorCierreOrdenInspeccion {
    private Sesion sesion;
    private OrdenDeInspeccion ordenSeleccionada;
    private String observacionCierre;
    private List<MotivoFueraDeServicio> motivosSeleccionados;

    public gestorCierreOrdenInspeccion(Sesion sesion) {
        this.sesion = sesion;
        this.motivosSeleccionados = new ArrayList<>();
    }

    public List<OrdenDeInspeccion> obtenerOrdenesCompletamenteRealizadas(List<OrdenDeInspeccion> todasLasOrdenes) {
        Empleado riLogueado = sesion.obtenerUsuario();
        List<OrdenDeInspeccion> ordenesValidas = new ArrayList<>();

        for (OrdenDeInspeccion orden : todasLasOrdenes) {
            if (orden.esMiRI(riLogueado) && orden.esCompletamenteRealizada()) {
                ordenesValidas.add(orden);
            }
        }
        return ordenesValidas;
    }

    public void seleccionarOrden(OrdenDeInspeccion orden) {
        this.ordenSeleccionada = orden;
    }

    public void tomarObservacionCierre(String observacion) {
        this.observacionCierre = observacion;
    }

    public void agregarMotivoFueraDeServicio(String comentario) {
        MotivoFueraDeServicio motivo = new MotivoFueraDeServicio(comentario);
        this.motivosSeleccionados.add(motivo);
    }

    public void confirmarCierre(Estado estadoCerrado, Estado estadoFueraServicio, List<Empleado> responsablesReparacion) {
        if (observacionCierre == null || observacionCierre.isEmpty()) {
            System.out.println("ERROR: Falta la observación de cierre.");
            return;
        }

        if (motivosSeleccionados.isEmpty()) {
            System.out.println("ERROR: Debe ingresar al menos un motivo.");
            return;
        }

        Date ahora = new Date();

        ordenSeleccionada.cerrar(ahora);
        ordenSeleccionada.setEstado(estadoCerrado);

        Sismografo sismografo = ordenSeleccionada.getEstacion().getSismografo();
        sismografo.finalizarEstadoActual(ahora);

        CambioEstado nuevoEstado = new CambioEstado(estadoFueraServicio, ahora);
        for (MotivoFueraDeServicio m : motivosSeleccionados) {
            nuevoEstado.registrarMotivo(m);
        }

        sismografo.cambiarEstado(nuevoEstado);

        System.out.println("Orden cerrada correctamente.");
        for (Empleado r : responsablesReparacion) {
            if (r.esResponsableDeReparacion()) {
                System.out.println("Notificación enviada a: " + r.getMail());
            }
        }
    }
}
