
package main;

import boundary.PantallaCierreOrdenInspeccion_Final;
import gestor.gestorCierreOrdenInspeccion;
import modelo.*;

import java.util.*;

public class mainUI {
    public static void main(String[] args) {
        // Crear empleado responsable de inspecciones (RI)
        Empleado ri = new Empleado("Facu", "Pérez", "facu@gmail.com", new Rol("Responsable de Inspecciones", "RI", false));
        Sesion sesion = new Sesion(ri);
        gestorCierreOrdenInspeccion gestor = new gestorCierreOrdenInspeccion(sesion);

        // Crear sismógrafo con estado actual
        Estado estadoSismografo = new Estado("Inhabilitado", "Sismografo");
        CambioEstado estadoActual = new CambioEstado(estadoSismografo, new Date());
        Sismografo sismografo = new Sismografo("S001", estadoActual);
        EstacionSismologica estacion = new EstacionSismologica("Estación Norte", "ES01", sismografo);

        // Crear estado y órdenes de inspección
        Estado estadoOrden = new Estado("CompletamenteRealizada", "OrdenDeInspeccion");
        OrdenDeInspeccion orden1 = new OrdenDeInspeccion("OI001", new Date(), estadoOrden, ri, estacion);
        OrdenDeInspeccion orden2 = new OrdenDeInspeccion("OI002", new Date(System.currentTimeMillis() - 86400000), estadoOrden, ri, estacion);

        List<OrdenDeInspeccion> ordenes = Arrays.asList(orden1, orden2);

        // Lanzar interfaz
        new PantallaCierreOrdenInspeccion_Final(gestor, ordenes);
    }
}
