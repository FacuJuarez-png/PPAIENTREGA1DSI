
package modelo;

public class Estado {
    private String nombreEstado;
    private String ambito;

    public Estado(String nombreEstado, String ambito) {
        this.nombreEstado = nombreEstado;
        this.ambito = ambito;
    }

    public boolean esAmbitoOrdenDeInspeccion() {
        return ambito.equals("OrdenDeInspeccion");
    }

    public boolean esCompletamenteRealizada() {
        return nombreEstado.equals("CompletamenteRealizada");
    }

    public boolean esCerrado() {
        return nombreEstado.equals("Cerrado");
    }

    public boolean esAmbitoSismografo() {
        return ambito.equals("Sismografo");
    }

    public boolean esFueraDeServicio() {
        return nombreEstado.equals("FueraDeServicio");
    }

    public String getNombre() {
        return nombreEstado;
    }
}
