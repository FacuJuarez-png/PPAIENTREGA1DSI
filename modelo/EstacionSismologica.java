
package modelo;

public class EstacionSismologica {
    private String nombre;
    private String codEstacion;
    private Sismografo sismografo;

    public EstacionSismologica(String nombre, String codEstacion, Sismografo sismografo) {
        this.nombre = nombre;
        this.codEstacion = codEstacion;
        this.sismografo = sismografo;
    }

    public String getNombre() {
        return nombre;
    }

    public String mostrarIdentificadorSismografo() {
        return sismografo.getIdentificador();
    }

    public Sismografo getSismografo() {
        return sismografo;
    }
}
