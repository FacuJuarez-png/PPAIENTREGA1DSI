
package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sismografo {
    private String nroSerie;
    private CambioEstado estadoActual;
    private List<CambioEstado> cambiosEstado;

    public Sismografo(String nroSerie, CambioEstado estadoActual) {
        this.nroSerie = nroSerie;
        this.estadoActual = estadoActual;
        this.cambiosEstado = new ArrayList<>();
    }

    public void finalizarEstadoActual(Date fechaFin) {
        this.estadoActual.setFechaHoraFin(fechaFin);
    }

    public void setEstadoActual(CambioEstado nuevoEstado) {
        this.estadoActual = nuevoEstado;
    }

    public void cambiarEstado(CambioEstado nuevoCambioEstado) {
        this.cambiosEstado.add(nuevoCambioEstado);
        this.setEstadoActual(nuevoCambioEstado);
    }

    public String getIdentificador() {
        return nroSerie;
    }
}
