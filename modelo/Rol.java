
package modelo;

public class Rol {
    private String nombre;
    private String descripcionRol;
    private boolean responsableDeReparacion;

    public Rol(String nombre, String descripcionRol, boolean responsableDeReparacion) {
        this.nombre = nombre;
        this.descripcionRol = descripcionRol;
        this.responsableDeReparacion = responsableDeReparacion;
    }

    public boolean esResponsableDeReparacion() {
        return responsableDeReparacion;
    }
}
