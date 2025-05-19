
package modelo;

public class Sesion {
    private Empleado empleadoLogueado;

    public Sesion(Empleado empleadoLogueado) {
        this.empleadoLogueado = empleadoLogueado;
    }

    public Empleado obtenerUsuario() {
        return empleadoLogueado;
    }
}
