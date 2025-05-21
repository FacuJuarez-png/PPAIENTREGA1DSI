//envía una notificación por defecto a los mails de los empleados responsables de reparaciones
package boundary;

import java.util.List;

public class InterfazNotificacion {
    
    public void notificar(List<String> mails, String cuerpoNotificacion){
        // Simulación de envío de notificación
        for (String mail : mails) {
            System.out.println("Enviando notificación a: " + mail);
            System.out.println("Cuerpo de la notificación: " + cuerpoNotificacion);
        }
        
    }
}