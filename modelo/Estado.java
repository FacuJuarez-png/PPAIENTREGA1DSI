package modelo;

public class Estado {
    private String nombre;
    private String ambito;

    public Estado(String nombre, String ambito) {
        this.nombre = nombre;
        this.ambito = ambito;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getAmbito() { return ambito; }
    public void setAmbito(String ambito) { this.ambito = ambito; }

    public boolean esCerrada() {
        return nombre.equalsIgnoreCase("Cerrado") && ambito.equalsIgnoreCase("OrdenDeInspeccion");
    }

    public boolean esFueraDeServicio() {
        return nombre.equalsIgnoreCase("FueraDeServicio") && ambito.equalsIgnoreCase("Sismografo");
    }

    public boolean esCompletamenteRealizada() {
        return nombre.equalsIgnoreCase("CompletamenteRealizada") && ambito.equalsIgnoreCase("OrdenDeInspeccion");
    }

    public static Estado buscarPorNombre(String nombre, String ambito) {
        return new Estado(nombre, ambito);
    }
}
