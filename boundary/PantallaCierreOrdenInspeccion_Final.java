
package boundary;

import gestor.gestorCierreOrdenInspeccion;
import modelo.*;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.*;

public class PantallaCierreOrdenInspeccion_Final extends JFrame {
    private gestorCierreOrdenInspeccion gestor;
    private JComboBox<String> comboOrdenes;
    private JTextArea observacionText;
    private JCheckBox motivo1, motivo2;
    private JTextField comentario1, comentario2;
    private JButton confirmarBtn;

    private List<OrdenDeInspeccion> ordenesDisponibles;
    private Map<String, OrdenDeInspeccion> ordenMap;

    public PantallaCierreOrdenInspeccion_Final(gestorCierreOrdenInspeccion gestor, List<OrdenDeInspeccion> ordenes) {
        this.gestor = gestor;
        this.ordenesDisponibles = ordenes;
        this.setTitle("Cierre de Orden de Inspección");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        comboOrdenes = new JComboBox<>();
        observacionText = new JTextArea(3, 40);
        motivo1 = new JCheckBox("Falla en sensor de movimiento");
        comentario1 = new JTextField(40);
        motivo2 = new JCheckBox("No responde a reinicios");
        comentario2 = new JTextField(40);
        confirmarBtn = new JButton("Confirmar Cierre");

        panel.add(new JLabel("Seleccione una orden de inspección"));
        panel.add(comboOrdenes);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Observación de cierre"));
        panel.add(new JScrollPane(observacionText));
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Seleccione motivos y agregue comentarios"));

        panel.add(motivo1);
        panel.add(new JLabel("Comentario para motivo 1:"));
        panel.add(comentario1);
        panel.add(Box.createVerticalStrut(10));

        panel.add(motivo2);
        panel.add(new JLabel("Comentario para motivo 2:"));
        panel.add(comentario2);
        panel.add(Box.createVerticalStrut(10));

        panel.add(confirmarBtn);

        this.add(panel, BorderLayout.CENTER);

        cargarOrdenes();

        confirmarBtn.addActionListener(this::confirmarCierre);
        this.setVisible(true);
    }

    private void cargarOrdenes() {
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

        if (motivo1.isSelected()) {
            gestor.agregarMotivoFueraDeServicio(motivo1.getText() + ": " + comentario1.getText());
        }
        if (motivo2.isSelected()) {
            gestor.agregarMotivoFueraDeServicio(motivo2.getText() + ": " + comentario2.getText());
        }

        Estado cerrado = new Estado("Cerrado", "OrdenDeInspeccion");
        Estado fueraServicio = new Estado("FueraDeServicio", "Sismografo");

        Empleado responsable = new Empleado("Juan", "Juárez", "juan@reparaciones.com", new Rol("Técnico", "Técnico", true));
        gestor.confirmarCierre(cerrado, fueraServicio, Arrays.asList(responsable));

        JOptionPane.showMessageDialog(this, "Orden cerrada correctamente.", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
        PantallaCierreOrdenInspeccion_Final.this.dispose();
    }
}
