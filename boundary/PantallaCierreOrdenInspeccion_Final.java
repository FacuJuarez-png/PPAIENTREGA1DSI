package boundary;

import gestor.gestorCierreOrdenInspeccion;
import modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


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
    private JLabel resumenFinal;
    

    public PantallaCierreOrdenInspeccion_Final(gestorCierreOrdenInspeccion gestor, List<OrdenDeInspeccion> ordenes) {
        this.gestor = gestor;
        this.ordenesDisponibles = ordenes;
        setTitle("Sistema de Inspección");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        habilitarVentana();
        setVisible(true);
    }

    public void habilitarVentana() {
        getContentPane().removeAll();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Bienvenido al sistema");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        JButton btnCerrarOrden = new JButton("Cerrar Orden de Inspección");
        btnCerrarOrden.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrarOrden.addActionListener(e -> opcionCerrarOrdenDeInspeccion());

        resumenFinal = new JLabel(" ");
        resumenFinal.setAlignmentX(Component.CENTER_ALIGNMENT);
        resumenFinal.setFont(new Font("Arial", Font.ITALIC, 12));

        panel.add(Box.createVerticalStrut(50));
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnCerrarOrden);
        panel.add(Box.createVerticalStrut(20));
        panel.add(resumenFinal);

        getContentPane().add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void opcionCerrarOrdenDeInspeccion() {
        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());
        contenedorCentral = new JPanel();
        contenedorCentral.setLayout(new BoxLayout(contenedorCentral, BoxLayout.Y_AXIS));

        infoOrdenesInspeccion();
        initComponentes();

        getContentPane().add(new JScrollPane(tablaOrdenes), BorderLayout.NORTH);
        getContentPane().add(contenedorCentral, BorderLayout.CENTER);
        getContentPane().add(panelConfirmacion, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    public void infoOrdenesInspeccion() {
        modeloTabla = new DefaultTableModel(new Object[]{"Seleccionar", "Nro Orden", "Fecha Fin", "Sismógrafo"}, 0);
        List<OrdenDeInspeccion> ordenesValidas = gestor.mostrarInfoOrdenesInspeccion(ordenesDisponibles);
        for (OrdenDeInspeccion o : ordenesValidas) {
            modeloTabla.addRow(new Object[]{false, o.getNumero(), o.getFechaHoraFinalizacion(), o.obtenerEstacion().obtenerSismografo().getIdentificador()});


        tablaOrdenes = new JTable(modeloTabla) {
            public Class<?> getColumnClass(int column) {
                return column == 0 ? Boolean.class : Object.class;
            }
        };

        btnSeleccionarOrden = new JButton("Seleccionar Orden");
        btnSeleccionarOrden.addActionListener(this::tomarSelecOrdenInspeccion);
        contenedorCentral.add(btnSeleccionarOrden);}
    }

    private void initComponentes() {
        panelObservacion = new JPanel();
        panelObservacion.setLayout(new BoxLayout(panelObservacion, BoxLayout.Y_AXIS));
        panelObservacion.setVisible(false);

        campoObservacion = new JTextArea(3, 50);
        checkPonerFS = new JCheckBox("¿Registrar sismógrafo como fuera de servicio?");
        btnConfirmarObservacion = new JButton("Confirmar Observación");
        btnConfirmarObservacion.addActionListener(this::tomarObservacionCierreOrden);

        panelObservacion.add(new JLabel("Observación de cierre:"));
        panelObservacion.add(new JScrollPane(campoObservacion));
        panelObservacion.add(checkPonerFS);
        panelObservacion.add(btnConfirmarObservacion);
        contenedorCentral.add(panelObservacion);

        panelMotivos = new JPanel();
        panelMotivos.setLayout(new BoxLayout(panelMotivos, BoxLayout.Y_AXIS));
        panelMotivos.setVisible(false);

        btnConfirmarMotivos = new JButton("Confirmar Motivos");
        btnConfirmarMotivos.addActionListener(this::tomarComentarioPorMotivoTipo);
        panelMotivos.add(btnConfirmarMotivos);
        contenedorCentral.add(panelMotivos);

        panelConfirmacion = new JPanel();
        panelConfirmacion.setLayout(new FlowLayout());
        panelConfirmacion.setVisible(false);

        btnConfirmarCierre = new JButton("Confirmar Cierre");
        btnCancelar = new JButton("Cancelar");

        btnConfirmarCierre.addActionListener(e -> {
            gestor.tomarConfirmacionCierreOrden(true);

            List<String> notificaciones = Arrays.asList(
                "Se notificó al responsable de reparaciones.",
                "Sismógrafo marcado como Fuera de Servicio.",
                "Orden cerrada correctamente."
            );

            new PantallaNotificacion(notificaciones);
            JOptionPane.showMessageDialog(this, "La orden fue cerrada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            resumenFinal.setText("Última acción: Orden cerrada y notificaciones enviadas.");
            gestor.finCU();
            habilitarVentana();
        });

        btnCancelar.addActionListener(e -> {
            gestor.tomarConfirmacionCierreOrden(false);
            resumenFinal.setText("Acción cancelada por el usuario.");
            gestor.finCU();
            habilitarVentana();
        });

        panelConfirmacion.add(btnConfirmarCierre);
        panelConfirmacion.add(btnCancelar);
    }

    public void tomarSelecOrdenInspeccion(ActionEvent e) {
        int row = tablaOrdenes.getSelectedRow();
        if (row != -1) {
            OrdenDeInspeccion ordenSeleccionada = ordenesDisponibles.get(row);
            gestor.tomarSelectOrdenInspeccion(ordenSeleccionada);
            tablaOrdenes.setEnabled(false);
            btnSeleccionarOrden.setEnabled(false);
            solicitarObservacionCierreOrden();
        }
    }

    public void solicitarObservacionCierreOrden() {
        panelObservacion.setVisible(true);
    }

    public void tomarObservacionCierreOrden(ActionEvent e) {
        String obs = campoObservacion.getText();
        boolean ponerFS = checkPonerFS.isSelected();
        gestor.tomarObservacionCierreOrden(obs, ponerFS);
        panelObservacion.setEnabled(false);
        checkPonerFS.setEnabled(false);
        campoObservacion.setEnabled(false);
        btnConfirmarObservacion.setEnabled(false);
        if (ponerFS) solicitarMotivosFueraDeServicio();
        else solicitarConfirmacionCierreOrden();
    }

    public void solicitarMotivosFueraDeServicio() {
        List<MotivoTipo> motivos = gestor.mostrarTiposMotivoFueraDeServicio();
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

    public void tomarComentarioPorMotivoTipo(ActionEvent e) {
        List<String[]> motivosSeleccionados = new ArrayList<>();
        for (Map.Entry<MotivoTipo, JTextField> entry : camposComentarios.entrySet()) {
            MotivoTipo tipo = entry.getKey();
            JTextField campo = entry.getValue();
            if (campo.isEnabled()) {
                motivosSeleccionados.add(new String[]{tipo.getDescripcion(), campo.getText()});
            }
        }
        gestor.tomarMotivosFueraDeServicio(motivosSeleccionados);
        panelMotivos.setEnabled(false);
        btnConfirmarMotivos.setEnabled(false);
        solicitarConfirmacionCierreOrden();
    }

    public void solicitarConfirmacionCierreOrden() {
        panelConfirmacion.setVisible(true);
    }
}