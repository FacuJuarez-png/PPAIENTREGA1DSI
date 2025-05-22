
package modelo;

import java.util.List;
import java.util.ArrayList;

public class Empleado {
    private String nombre;
    private String apellido;
    private String mail;
    private String telefono;
    private Rol rol;
    
    // Simulación de persistencia
    private static final List<Empleado> todosLosEmpleados = new ArrayList<>();

    public Empleado(String nombre, String apellido, String mail, String telefono, Rol rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.telefono = telefono;
        this.rol = rol;
    }

    public String getMail() {
        return mail;
    }

    public boolean esResponsableDeReparacion() {
        return rol.esResponsableDeReparacion();
    }

    // simulación de persistencia
    public static List<Empleado> obtenerTodosLosEmpleados() {
        return todosLosEmpleados;
    }
}
