package boundary;

import gestor.gestorCierreOrdenInspeccion;
import modelo.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PantallaCierreOrdenInspeccion extends JFrame {
    private gestorCierreOrdenInspeccion gestor;
    private JComboBox<String> comboOrdenes;
    private JTextArea observacionText;
    private JCheckBox motivo1, motivo2;
    private JButton confirmarBtn;

    private List<OrdenDeInspeccion> ordenesDisponibles;
    private Map<String, OrdenDeInspeccion> ordenMap;

    public PantallaCierreOrdenInspeccion(gestorCierreOrdenInspeccion gestor) {
        this.gestor = gestor;
        this.setTitle("Cierre de Orden de Inspección");
        this.setSize(500, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        comboOrdenes = new JComboBox<>();
        observacionText = new JTextArea(3, 40);
        motivo1 = new JCheckBox("Falla en sensor de movimiento");
        motivo2 = new JCheckBox("No responde a reinicios");
        confirmarBtn = new JButton("Confirmar Cierre");

        panel.add(new JLabel("Seleccione una orden de inspección"));
        panel.add(comboOrdenes);
        panel.add(new JLabel("Observación de cierre"));
        panel.add(new JScrollPane(observacionText));
        panel.add(new JLabel("Seleccione motivos"));
        panel.add(motivo1);
        panel.add(motivo2);
        panel.add(confirmarBtn);

        this.add(panel, BorderLayout.CENTER);

        cargarOrdenes();

        confirmarBtn.addActionListener(this::confirmarCierre);
        this.setVisible(true);
    }

    private void cargarOrdenes() {
        ordenesDisponibles = gestor.obtenerOrdenesCompletamenteRealizadas(dummyOrdenes());
        ordenMap = new HashMap<>();
        for (OrdenDeInspeccion o : ordenesDisponibles) {
            String desc = o.mostrarDatos();
            ordenMap.put(desc, o);
            comboOrdenes.addItem(desc);
        }
    }

    private void confirmarCierre(ActionEvent e) {
        String ordenSeleccionada = (String) comboOrdenes.getSelectedItem();
        OrdenDeInspeccion orden = ordenMap.get(ordenSeleccionada);
        gestor.seleccionarOrden(orden);
        gestor.tomarObservacionCierre(observacionText.getText());

        if (motivo1.isSelected()) gestor.agregarMotivoFueraDeServicio(motivo1.getText());
        if (motivo2.isSelected()) gestor.agregarMotivoFueraDeServicio(motivo2.getText());

        Estado cerrado = new Estado("Cerrado", "OrdenDeInspeccion");
        Estado fueraServicio = new Estado("FueraDeServicio", "Sismografo");

        Empleado responsable = new Empleado("Juan", "Juárez", "juan@reparaciones.com",
                new Rol("Técnico", "Técnico", true));
        gestor.confirmarCierre(cerrado, fueraServicio, Arrays.asList(responsable));
        JOptionPane.showMessageDialog(this, "Orden cerrada correctamente.");
    }

    private List<OrdenDeInspeccion> dummyOrdenes() {
        Estado estadoFinalizada = new Estado("CompletamenteRealizada", "OrdenDeInspeccion");
        Estado estadoInhabilitado = new Estado("Inhabilitado", "Sismografo");

        Rol rol = new Rol("RI", "Responsable", false);
        Empleado ri = new Empleado("Facu", "Pérez", "facundo@gmail.com", rol);
        Sismografo s = new Sismografo("S001", new CambioEstado(estadoInhabilitado, new Date()));
        EstacionSismologica est = new EstacionSismologica("Estación Norte", "ES01", s);

        OrdenDeInspeccion orden = new OrdenDeInspeccion("OI001",
                new Date(System.currentTimeMillis() - 3600000), estadoFinalizada, ri, est);
        return Arrays.asList(orden);
    }
}
