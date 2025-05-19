
package modelo;

public class Empleado {
    private String nombre;
    private String apellido;
    private String mail;
    private Rol rol;

    public Empleado(String nombre, String apellido, String mail, Rol rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.rol = rol;
    }

    public String getMail() {
        return mail;
    }

    public boolean esResponsableDeReparacion() {
        return rol.esResponsableDeReparacion();
    }
}
