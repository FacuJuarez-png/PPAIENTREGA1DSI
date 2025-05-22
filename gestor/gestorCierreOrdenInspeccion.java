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

    public void nuevoCierreOrdenInspeccion() {
        System.out.println("Gestor: nuevo cierre iniciado.");
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

public void tomarSelecOrdenInspeccion(String numeroOrden) {
    System.out.println("Gestor: orden seleccionada -> " + numeroOrden);
    // Buscamos entre las ordenes (si las tenés almacenadas), o la pasás directo
}

    public void tomarObservacionCierreOrden(String observacion, boolean ponerFueraServicio) {
        this.observacionCierre = observacion;
        System.out.println("Gestor: observación tomada. ¿Poner FS? " + ponerFueraServicio);
    }

    public void tomarMotivosFueraDeServicio(List<String[]> motivos) {
        for (String[] par : motivos) {
            String descripcion = par[0];
            String comentario = par[1];
            MotivoFueraDeServicio motivo = new MotivoFueraDeServicio(descripcion + ": " + comentario);
            this.motivosSeleccionados.add(motivo);
        }
        System.out.println("Gestor: motivos recibidos.");
    }

    public void tomarConfirmacionCierreOrden(boolean confirmado) {
        if (confirmado) {
            System.out.println("Gestor: cierre confirmado.");
            confirmarCierre(
                new Estado("Cerrado", "OrdenDeInspeccion"),
                new Estado("FueraDeServicio", "Sismografo"),
                new ArrayList<>()
            );
        } else {
            System.out.println("Gestor: cierre cancelado.");
        }
    }

    public void confirmarCierre(Estado estadoCerrado, Estado estadoFueraServicio, List<Empleado> responsablesReparacion) {
        if (observacionCierre == null || observacionCierre.isEmpty()) {
            System.out.println("ERROR: Falta la observación de cierre.");
            return;
        }

        Date ahora = new Date();
        ordenSeleccionada.cerrar(ahora, estadoCerrado);

        Sismografo sismografo = ordenSeleccionada.getEstacion().obtenerSismografo();
        sismografo.finalizarEstadoActual(ahora);

        List<MotivoFueraDeServicio> copiaMotivos = new ArrayList<>(motivosSeleccionados);
        CambioEstado nuevoEstado = new CambioEstado(ahora, estadoFueraServicio, copiaMotivos);
        List<MotivoFueraDeServicio> copia = new ArrayList<>(motivosSeleccionados);
        for (MotivoFueraDeServicio m : copia) {
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

    public void finCU() {
        System.out.println("Gestor: fin de caso de uso.");
    }

    // Métodos originales por compatibilidad con pruebas sueltas
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
}