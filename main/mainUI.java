package main;

import boundary.PantallaCierreOrdenInspeccion_Final;
import gestor.gestorCierreOrdenInspeccion;
import modelo.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class mainUI {
    public static void main(String[] args) {
        // Crear empleado responsable
        Rol rol = new Rol("Responsable de Inspecciones", "RI");
        Empleado ri = new Empleado("Facu", "Pérez", "facu@gmail.com", rol);

        // Crear sesión
        Sesion sesion = new Sesion(ri);

        // Crear estados
        Estado estadoOrden = Estado.buscarPorNombre("CompletamenteRealizada", "OrdenDeInspeccion");
        Estado estadoSismografo = Estado.buscarPorNombre("Inhabilitado", "Sismografo");

        // Crear sismógrafo
        List<MotivoFueraDeServicio> vacio = new ArrayList<>();
        CambioEstado estadoActual = new CambioEstado(new Date(), estadoSismografo, vacio);
        Sismografo sismografo = new Sismografo("S001", estadoActual);

        // Crear estación
        EstacionSismologica estacion = new EstacionSismologica("Estación Norte", "ES01", sismografo);

        // Crear órdenes
 // Estado inicial realista
Estado estadoPendiente = new Estado("Pendiente", "OrdenDeInspeccion");

// Empleado responsable (ya deberías tenerlo creado como `ri`)
OrdenDeInspeccion orden1 = new OrdenDeInspeccion(
    "OI001",
    new Date(), // fecha actual
    estadoPendiente,
    ri,
    estacion
);

// Orden creada el día anterior
OrdenDeInspeccion orden2 = new OrdenDeInspeccion(
    "OI002",
    new Date(System.currentTimeMillis() - 86400000), // hace 1 día
    estadoPendiente,
    ri,
    estacion
);


        List<OrdenDeInspeccion> ordenes = new ArrayList<>();
        ordenes.add(orden1);
        ordenes.add(orden2);

        // Lanzar gestor y UI
        gestorCierreOrdenInspeccion gestor = new gestorCierreOrdenInspeccion(sesion);
        new PantallaCierreOrdenInspeccion_Final(gestor, ordenes);
    }
}
