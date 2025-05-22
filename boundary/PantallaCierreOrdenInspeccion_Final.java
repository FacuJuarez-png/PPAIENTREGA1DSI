package boundary;

import gestor.gestorCierreOrdenInspeccion;
import modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class PantallaCierreOrdenInspeccion_Final extends JFrame {
    private gestorCierreOrdenInspeccion gestor;
    private JTable tablaOrdenes;
    private DefaultTableModel modeloTabla;
    private JButton btnSeleccionarOrden, btnConfirmarObservacion, btnConfirmarMotivos, btnConfirmarCierre, btnCancelar;
    private JTextArea campoObservacion;
    private JCheckBox checkPonerFS;
    private JPanel panelObservacion, panelMotivos, panelConfirmacion;
    private JPanel contenedorCentral;
    private Map<MotivoTipo, JTextField> camposComentarios = new HashMap<>();
    private List<OrdenDeInspeccion> ordenesDisponibles;

    public PantallaCierreOrdenInspeccion_Final(gestorCierreOrdenInspeccion gestor, List<OrdenDeInspeccion> ordenes) {
        this.gestor = gestor;
        this.ordenesDisponibles = ordenes;
        setTitle("Sistema de Inspección");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        mostrarMenuPrincipal();
        setVisible(true);
    }

    private void mostrarMenuPrincipal() {
        getContentPane().removeAll();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Bienvenido al sistema");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        JButton btnCerrarOrden = new JButton("Cerrar Orden de Inspección");
        btnCerrarOrden.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrarOrden.addActionListener(e -> mostrarFormularioCierre());

        panel.add(Box.createVerticalStrut(50));
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnCerrarOrden);

        getContentPane().add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void mostrarFormularioCierre() {
        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());
        contenedorCentral = new JPanel();
        contenedorCentral.setLayout(new BoxLayout(contenedorCentral, BoxLayout.Y_AXIS));

        initTablaOrdenes(ordenesDisponibles);
        initComponentes();

        getContentPane().add(new JScrollPane(tablaOrdenes), BorderLayout.NORTH);
        getContentPane().add(contenedorCentral, BorderLayout.CENTER);
        getContentPane().add(panelConfirmacion, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    private void initTablaOrdenes(List<OrdenDeInspeccion> ordenes) {
        modeloTabla = new DefaultTableModel(new Object[]{"Seleccionar", "Nro Orden", "Fecha Fin", "Sismógrafo"}, 0);
        for (OrdenDeInspeccion o : ordenes) {
            modeloTabla.addRow(new Object[]{false, o.getNumero(), o.getFechaHoraFin(), o.getEstacion().obtenerSismografo().getIdentificador()});
        }

        tablaOrdenes = new JTable(modeloTabla) {
            public Class<?> getColumnClass(int column) {
                return column == 0 ? Boolean.class : Object.class;
            }
        };

        btnSeleccionarOrden = new JButton("Seleccionar Orden");
        btnSeleccionarOrden.addActionListener(this::seleccionarOrden);
        contenedorCentral.add(btnSeleccionarOrden);
    }

    private void initComponentes() {
        // Observación
        panelObservacion = new JPanel();
        panelObservacion.setLayout(new BoxLayout(panelObservacion, BoxLayout.Y_AXIS));
        panelObservacion.setVisible(false);

        campoObservacion = new JTextArea(3, 50);
        checkPonerFS = new JCheckBox("¿Registrar sismógrafo como fuera de servicio?");
        btnConfirmarObservacion = new JButton("Confirmar Observación");
        btnConfirmarObservacion.addActionListener(this::confirmarObservacion);

        panelObservacion.add(new JLabel("Observación de cierre:"));
        panelObservacion.add(new JScrollPane(campoObservacion));
        panelObservacion.add(checkPonerFS);
        panelObservacion.add(btnConfirmarObservacion);
        contenedorCentral.add(panelObservacion);

        // Motivos
        panelMotivos = new JPanel();
        panelMotivos.setLayout(new BoxLayout(panelMotivos, BoxLayout.Y_AXIS));
        panelMotivos.setVisible(false);

        btnConfirmarMotivos = new JButton("Confirmar Motivos");
        btnConfirmarMotivos.addActionListener(this::confirmarMotivos);
        panelMotivos.add(btnConfirmarMotivos);
        contenedorCentral.add(panelMotivos);

        // Confirmación
        panelConfirmacion = new JPanel();
        panelConfirmacion.setLayout(new FlowLayout());
        panelConfirmacion.setVisible(false);

        btnConfirmarCierre = new JButton("Confirmar Cierre");
        btnCancelar = new JButton("Cancelar");

        btnConfirmarCierre.addActionListener(e -> {
            gestor.tomarConfirmacionCierreOrden(true);
            JOptionPane.showMessageDialog(this, "La orden fue cerrada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            gestor.finCU();
            mostrarMenuPrincipal();
        });

        btnCancelar.addActionListener(e -> {
            gestor.tomarConfirmacionCierreOrden(false);
            gestor.finCU();
            mostrarMenuPrincipal();
        });

        panelConfirmacion.add(btnConfirmarCierre);
        panelConfirmacion.add(btnCancelar);
    }

    private void seleccionarOrden(ActionEvent e) {
        int row = tablaOrdenes.getSelectedRow();
        if (row != -1) {
            OrdenDeInspeccion ordenSeleccionada = ordenesDisponibles.get(row);
            gestor.seleccionarOrden(ordenSeleccionada);
            tablaOrdenes.setEnabled(false);
            btnSeleccionarOrden.setEnabled(false);
            panelObservacion.setVisible(true);
        }
    }

    private void confirmarObservacion(ActionEvent e) {
        String obs = campoObservacion.getText();
        boolean ponerFS = checkPonerFS.isSelected();
        gestor.tomarObservacionCierreOrden(obs, ponerFS);
        panelObservacion.setEnabled(false);
        checkPonerFS.setEnabled(false);
        campoObservacion.setEnabled(false);
        btnConfirmarObservacion.setEnabled(false);
        if (ponerFS) mostrarMotivos();
        else mostrarConfirmacionFinal();
    }

    private void mostrarMotivos() {
        List<MotivoTipo> motivos = MotivoTipo.getMotivosTipo();
        for (MotivoTipo motivo : motivos) {
            JCheckBox check = new JCheckBox(motivo.getDescripcion());
            JTextField comentario = new JTextField(40);
            comentario.setEnabled(false);
            check.addActionListener(e -> comentario.setEnabled(check.isSelected()));
            panelMotivos.add(check);
            panelMotivos.add(new JLabel("Comentario para: " + motivo.getDescripcion()));
            panelMotivos.add(comentario);
            camposComentarios.put(motivo, comentario);
        }
        panelMotivos.setVisible(true);
    }

    private void confirmarMotivos(ActionEvent e) {
        List<String[]> motivosSeleccionados = new ArrayList<>();
        for (Map.Entry<MotivoTipo, JTextField> entry : camposComentarios.entrySet()) {
            MotivoTipo tipo = entry.getKey();
            JTextField campo = entry.getValue();
            if (campo.isEnabled()) {
                String comentario = campo.getText();
                motivosSeleccionados.add(new String[]{tipo.getDescripcion(), comentario});
            }
        }
        gestor.tomarMotivosFueraDeServicio(motivosSeleccionados);
        panelMotivos.setEnabled(false);
        btnConfirmarMotivos.setEnabled(false);
        mostrarConfirmacionFinal();
    }

    private void mostrarConfirmacionFinal() {
        panelConfirmacion.setVisible(true);
    }
}