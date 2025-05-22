
package modelo;

public class Sismografo {
    private String identificador;
    private CambioEstado estadoActual;

    public Sismografo(String identificador, CambioEstado estadoActual) {
        this.identificador = identificador;
        this.estadoActual = estadoActual;
    }

    public void finalizarEstadoActual(java.util.Date fecha) {
        estadoActual.setFechaHoraDesde(fecha);
    }

    public void cambiarEstado(CambioEstado nuevo) {
        this.estadoActual = nuevo;
    }

    public CambioEstado obtenerEstadoActual() {
        return estadoActual;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

}
