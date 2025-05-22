package modelo;

import java.util.Arrays;
import java.util.List;

public class MotivoTipo {
    private String descripcion;

    public MotivoTipo(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public static List<MotivoTipo> getMotivosTipo() {
    return Arrays.asList(
        new MotivoTipo("Falla en sensor de movimiento"),
        new MotivoTipo("No responde a reinicios"),
        new MotivoTipo("Lectura errática persistente"));}
    

}
