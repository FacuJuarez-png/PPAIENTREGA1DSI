package modelo;

public class Sesion {
    private Empleado usuario;

    public Sesion(Empleado usuario) {
        this.usuario = usuario;
    }

    public Empleado obtenerUsuario() { return usuario; }

}
