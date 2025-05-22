package modelo;

import java.util.Date;

public class Sismografo {
    private String identificador;
    private CambioEstado estadoActual;

    public Sismografo(String identificador, CambioEstado estadoActual) {
        this.identificador = identificador;
        this.estadoActual = estadoActual;
    }

    public void finalizarCambioEstadoActual(Date fecha) {
        estadoActual.setFechaHoraDesde(fecha);
    }

    public void registrarCambioEstado(CambioEstado nuevo) {
        this.estadoActual = nuevo;
    }

    // Método alternativo con nombre cambiarEstado para compatibilidad
    public void cambiarEstado(CambioEstado nuevo) {
        registrarCambioEstado(nuevo);
    }

    public CambioEstado getEstadoActual() {
        return estadoActual;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public boolean esMiEstacion(EstacionSismologica estacion) {
        return estacion != null && estacion.obtenerSismografo() == this;
    }
}
