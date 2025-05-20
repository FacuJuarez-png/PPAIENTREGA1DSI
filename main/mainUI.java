package main;

import boundary.PantallaCierreOrdenInspeccion;
import gestor.gestorCierreOrdenInspeccion;
import modelo.*;

public class mainUI {
    public static void main(String[] args) {
        // Empleado dummy para la sesión
        Empleado ri = new Empleado("Facu", "Pérez", "facundo@gmail.com",
                new Rol("RI", "Responsable", false));
        Sesion sesion = new Sesion(ri);

        // Crear gestor y lanzar la UI
        gestorCierreOrdenInspeccion gestor = new gestorCierreOrdenInspeccion(sesion);
        new PantallaCierreOrdenInspeccion(gestor);
    }
}
