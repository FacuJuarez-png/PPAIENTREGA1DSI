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
        Estado estadoCompletada = Estado.buscarPorNombre("CompletamenteRealizada", "OrdenDeInspeccion");
        Estado estadoSismografo = Estado.buscarPorNombre("Inhabilitado", "Sismografo");

        // Crear estado actual del sismógrafo
        List<MotivoFueraDeServicio> vacio = new ArrayList<>();
        CambioEstado estadoActual = new CambioEstado(new Date(), estadoSismografo, vacio);

        // Crear sismógrafo
        Sismografo sismografo = new Sismografo("S001", estadoActual);

        // Crear estación
        EstacionSismologica estacion = new EstacionSismologica("Estación Norte", "ES01", sismografo);

        // Crear orden con estado completamente realizada (solo esta aparecerá en la tabla)
        OrdenDeInspeccion orden1 = new OrdenDeInspeccion(
            "OI001",
            new Date(),
            estadoCompletada,
            ri,
            estacion
        );

        // Crear orden que no aparecerá (estado pendiente)
        Estado estadoPendiente = new Estado("Pendiente", "OrdenDeInspeccion");
        OrdenDeInspeccion orden2 = new OrdenDeInspeccion(
            "OI002",
            new Date(System.currentTimeMillis() - 86400000),
            estadoPendiente,
            ri,
            estacion
        );

        // Crear lista total de órdenes
        List<OrdenDeInspeccion> todasLasOrdenes = new ArrayList<>();
        todasLasOrdenes.add(orden1);
        todasLasOrdenes.add(orden2);

        // Crear gestor y filtrar órdenes válidas
        gestorCierreOrdenInspeccion gestor = new gestorCierreOrdenInspeccion(sesion);
        List<OrdenDeInspeccion> ordenesValidas = gestor.mostrarInfoOrdenesInspeccion(todasLasOrdenes);

        // Lanzar la interfaz
        new PantallaCierreOrdenInspeccion_Final(gestor, ordenesValidas);
    }
}
