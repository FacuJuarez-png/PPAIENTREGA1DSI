package modelo;

public class MotivoFueraDeServicio {
    private String descripcion;
    private String comentario;

    public MotivoFueraDeServicio(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}
