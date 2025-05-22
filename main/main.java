
package main;

import gestor.gestorCierreOrdenInspeccion;
import modelo.Empleado;
import modelo.Estado;
import modelo.MotivoFueraDeServicio;
import modelo.OrdenDeInspeccion;
import modelo.Rol;
import modelo.Sesion;
import modelo.CambioEstado;
import modelo.Sismografo;
import modelo.EstacionSismologica;

import java.util.*;

public class main {
    public static void main(String[] args) {
        Estado estadoCompletada = new Estado("CompletamenteRealizada", "OrdenDeInspeccion");
        Estado estadoCerrada = new Estado("Cerrado", "OrdenDeInspeccion");
        Estado estadoInhabilitado = new Estado("Inhabilitado", "Sismografo");
        Estado estadoFueraServicio = new Estado("FueraDeServicio", "Sismografo");

        Rol rolRI = new Rol("Responsable de Inspecciones", "RI");
        Rol rolTecnico = new Rol("Técnico", "Técnico");

        Empleado empleadoRI = new Empleado("Facundo", "Pérez", "facundo@gmail.com", rolRI);
        Empleado tecnico = new Empleado("Juan", "Juárez", "juan@reparaciones.com", rolTecnico);

        Sesion sesion = new Sesion(empleadoRI);

    List<MotivoFueraDeServicio> motivosIniciales = new ArrayList<>();
    CambioEstado estadoActualSismografo = new CambioEstado(new Date(), estadoInhabilitado, motivosIniciales);
        Sismografo sismografo = new Sismografo("S001", estadoActualSismografo);
        EstacionSismologica estacion = new EstacionSismologica("Estación Norte", "ES01", sismografo);


        OrdenDeInspeccion orden1 = new OrdenDeInspeccion("OI001", new Date(), estadoCompletada, empleadoRI , estacion);

        List<OrdenDeInspeccion> todasLasOrdenes = new ArrayList<>();
        todasLasOrdenes.add(orden1);

        gestorCierreOrdenInspeccion gestor = new gestorCierreOrdenInspeccion(sesion);

        List<OrdenDeInspeccion> ordenesValidas = gestor.obtenerOrdenesCompletamenteRealizadas(todasLasOrdenes);
        System.out.println("Órdenes disponibles para cerrar:");
        for (OrdenDeInspeccion o : ordenesValidas) {
            System.out.println(o.mostrarDatos());
        }

        gestor.seleccionarOrden(orden1);
        gestor.tomarObservacionCierre("Se finalizó correctamente, pero el sismógrafo tiene fallas.");
        gestor.agregarMotivoFueraDeServicio("Falla en sensor de movimiento.");
        gestor.agregarMotivoFueraDeServicio("No responde a reinicios.");
        gestor.confirmarCierre(estadoCerrada, estadoFueraServicio, Arrays.asList(tecnico));
    }
}
