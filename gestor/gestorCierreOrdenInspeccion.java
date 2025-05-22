
package gestor;

import modelo.*;

import boundary.InterfazNotificacion;
import boundary.MonitorCCRS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class gestorCierreOrdenInspeccion {
    private Sesion sesion;
    private OrdenDeInspeccion ordenSeleccionada;
    private String observacionCierre;
    private List<MotivoFueraDeServicio> motivosSeleccionados;
    private InterfazNotificacion interfazNotificacion;
    private List<MonitorCCRS> monitorCCRS;

    public gestorCierreOrdenInspeccion(Sesion sesion) {
        this.sesion = sesion;
        this.motivosSeleccionados = new ArrayList<>();
        this.interfazNotificacion = new InterfazNotificacion();
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

        //prueba para imprimir en consola
        System.out.println("Orden cerrada correctamente.");
        for (Empleado r : responsablesReparacion) {
            if (r.esResponsableDeReparacion()) {
                System.out.println("Notificación enviada a: " + r.getMail());
            }
        }

        // Armar cuerpo del mensaje

        // Armar lista de mails y cuerpo
        List<String> mailsResponsables = new ArrayList<>();
        String cuerpo = "Se ha cerrado la orden: " + ordenSeleccionada.getNumeroOrden() +
                        "\nCon observación: " + observacionCierre +
                        "\nMotivos:\n";
        
        for (MotivoFueraDeServicio m : motivosSeleccionados) {
            cuerpo += "- " + m.getComentario() + "\n";
        }

        for (Empleado r : responsablesReparacion) {
            if (r.esResponsableDeReparacion()) {
                mailsResponsables.add(r.getMail());
            }
        }

        // Llamar a notificación
        interfazNotificacion.notificarResponsablesDeReparacion(mailsResponsables, cuerpo);
    }
    

    public void publicarEnMonitoresCCRS(String cuerpoNotificacion) {
        for (MonitorCCRS pantalla : monitorCCRS) {
            pantalla.publicar(cuerpoNotificacion);
        }
    }
}
