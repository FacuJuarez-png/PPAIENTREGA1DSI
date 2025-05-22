package gestor;

import modelo.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class gestorCierreOrdenInspeccion {
    private Empleado empleadoLogueado;
    private OrdenDeInspeccion ordenSeleccionada;
    private String observacionCierre;
    private List<MotivoFueraDeServicio> motivosFueraServicio;
    private Sesion sesionActual;
    private boolean ponerSismografoFueraServicio;

    public gestorCierreOrdenInspeccion(Sesion sesion) {
        this.sesionActual = sesion;
        this.empleadoLogueado = obtenerRILogueado();
        this.motivosFueraServicio = new ArrayList<>();
        nuevoCierreOrdenDeInspeccion();
    }

    public void nuevoCierreOrdenDeInspeccion() {
        // Inicializa cualquier recurso necesario
    }

    public Empleado obtenerRILogueado() {
        return sesionActual.obtenerUsuario();
    }

    public List<OrdenDeInspeccion> mostrarInfoOrdenesInspeccion(List<OrdenDeInspeccion> todas) {
        return ordenarOrdenesPorFechaFinalizacion(todas);
    }

    public List<OrdenDeInspeccion> ordenarOrdenesPorFechaFinalizacion(List<OrdenDeInspeccion> ordenes) {
        List<OrdenDeInspeccion> validas = new ArrayList<>();
        for (OrdenDeInspeccion o : ordenes) {
            if (o.esMiRI(empleadoLogueado) && o.esCompletamenteRealizada()) {
                validas.add(o);
            }
        }
        validas.sort((a, b) -> b.getFechaHoraFinalizacion().compareTo(a.getFechaHoraFinalizacion()));
        return validas;
    }

    public void tomarSelectOrdenInspeccion(OrdenDeInspeccion orden) {
        this.ordenSeleccionada = orden;
    }

    public void tomarObservacionCierreOrden(String observacion, boolean ponerFS) {
        this.observacionCierre = observacion;
        this.ponerSismografoFueraServicio = ponerFS;
    }

    public boolean validarObservacionCierre() {
        return observacionCierre != null && !observacionCierre.isEmpty();
    }

    public List<MotivoTipo> mostrarTiposMotivoFueraDeServicio() {
        return MotivoTipo.getMotivosTipo();
    }

    public void tomarMotivosFueraDeServicio(List<String[]> motivos) {
        motivosFueraServicio.clear();
        for (String[] par : motivos) {
            String desc = par[0];
            String comentario = par[1];
            motivosFueraServicio.add(new MotivoFueraDeServicio(desc + ": " + comentario));
        }
    }

    public boolean validarSelectMotivoFueraDeServicio() {
        return !motivosFueraServicio.isEmpty();
    }

    public void tomarConfirmacionCierreOrden(boolean confirmado) {
        if (confirmado) {
            cerrarOrdenDeInspeccion();
        }
    }

    public void cerrarOrdenDeInspeccion() {
        if (!validarObservacionCierre()) {
            System.out.println("Falta observación.");
            return;
        }
        if (ponerSismografoFueraServicio && !validarSelectMotivoFueraDeServicio()) {
            System.out.println("Debe ingresar al menos un motivo.");
            return;
        }

        Date ahora = getFechaHoraActual();
        Estado estadoCerrado = new Estado("Cerrada", "OrdenDeInspeccion");
        ordenSeleccionada.cerrar(ahora, estadoCerrado);

        if (ponerSismografoFueraServicio) {
            actualizarSismografoAFueraDeServicio(ahora);
        }

        notificarResponsablesDeReparacion();
        publicarEnMonitoresCCRS();
    }

    public Date getFechaHoraActual() {
        return new Date();
    }

    public void actualizarSismografoAFueraDeServicio(Date ahora) {
        Estado estadoFS = new Estado("FueraDeServicio", "Sismografo");
        Sismografo s = ordenSeleccionada.obtenerEstacion().obtenerSismografo();
        s.finalizarCambioEstadoActual(ahora);
        CambioEstado nuevo = new CambioEstado(ahora, estadoFS, motivosFueraServicio);
       List<MotivoFueraDeServicio> copiaMotivos = new ArrayList<>(motivosFueraServicio);
    for (MotivoFueraDeServicio m : copiaMotivos) {
    nuevo.registrarMotivo(m);}}


    public void notificarResponsablesDeReparacion() {
        System.out.println("Se notificó al responsable de reparación.");
    }

    public void publicarEnMonitoresCCRS() {
        System.out.println("Se publicó en los monitores del CCRS.");
    }

    public void finCU() {
        this.ordenSeleccionada = null;
        this.observacionCierre = null;
        this.motivosFueraServicio.clear();
    }
}