
package modelo;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class CambioEstado {
    private Date fechaHoraInicio;
    private Date fechaHoraFin;
    private Estado estado;
    private List<MotivoFueraDeServicio> motivosFueraDeServicio;

    public CambioEstado(Estado estado, Date fechaHoraInicio) {
        this.estado = estado;
        this.fechaHoraInicio = fechaHoraInicio;
        this.motivosFueraDeServicio = new ArrayList<>();
    }

    public void setFechaHoraFin(Date fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public void registrarMotivo(MotivoFueraDeServicio motivo) {
        motivosFueraDeServicio.add(motivo);
    }
}
