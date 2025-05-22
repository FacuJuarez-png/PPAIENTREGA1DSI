package boundary;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PantallaNotificacion extends JFrame {
    public PantallaNotificacion(List<String> notificaciones) {
        setTitle("Notificación de cierre");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));

        for (String n : notificaciones) {
            area.append("• " + n + "\n");
        }

        add(new JScrollPane(area), BorderLayout.CENTER);

        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(e -> dispose());

        JPanel panelBoton = new JPanel();
        panelBoton.add(cerrar);
        add(panelBoton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}