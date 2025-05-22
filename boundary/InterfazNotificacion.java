//envía una notificación por defecto a los mails de los empleados responsables de reparaciones
package boundary;

import javax.swing.*;
import java.util.List;

public class InterfazNotificacion {

    public void enviarNotificaion(List<String> mails, String cuerpoNotificacion) {
        // Simulación de envío de notificación
        for (String mail : mails) {
            String mensaje = "Enviando notificación a: " + mail + "\n\n"
                           + "Cuerpo de la notificación:\n" + cuerpoNotificacion;

            JOptionPane.showMessageDialog(
                null,
                mensaje,
                "Notificación enviada",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}