package modelo;

public class EstacionSismologica {
    private String nombre;
    private String codigo;
    private Sismografo sismografo;

    public EstacionSismologica(String nombre, String codigo, Sismografo sismografo) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.sismografo = sismografo;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public Sismografo obtenerSismografo() { return sismografo; }

}
