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

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    public Rol obtenerRol() { return rol; }
    public boolean esResponsableDeReparacion() {
        return rol != null && rol.getTipo().equalsIgnoreCase("Técnico");
    }

    public String obtenerMail() {
        return mail;
    }
}
