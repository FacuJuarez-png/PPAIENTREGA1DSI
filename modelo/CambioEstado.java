
package modelo;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class CambioEstado {
    private Date fechaHoraDesde;
    private Estado estado;
    private List<MotivoFueraDeServicio> motivos = new ArrayList<>();

    public CambioEstado(Date fechaHoraDesde, Estado estado, List<MotivoFueraDeServicio> motivos) {
        this.fechaHoraDesde = fechaHoraDesde;
        this.estado = estado;
        this.motivos = motivos;
    }

    public void registrarMotivo(MotivoFueraDeServicio motivo) {
        motivos.add(motivo);
    }

    public Date getFechaHoraDesde() {
        return fechaHoraDesde;
    }

    public void setFechaHoraDesde(Date fechaHoraDesde) {
        this.fechaHoraDesde = fechaHoraDesde;
    }

    public Estado obtenerEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<MotivoFueraDeServicio> obtenerMotivos() {
        return motivos;
    }

    public void setMotivos(List<MotivoFueraDeServicio> motivos) {
        this.motivos = motivos;
    }
}
