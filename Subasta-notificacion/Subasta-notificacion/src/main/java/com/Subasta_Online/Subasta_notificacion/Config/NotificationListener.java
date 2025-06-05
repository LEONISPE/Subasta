package com.Subasta_Online.Subasta_notificacion.Config;

import com.Subasta_Online.Subasta_notificacion.Model.NotificacionDTO;
import com.Subasta_Online.Subasta_notificacion.Service.NotificacionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {

    private final NotificacionService notificacionService;

    public NotificationListener(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }


    @KafkaListener(
            topics = "notificacion-topic",
            groupId = "notificacion-group",
            containerFactory = "notificacionListenerContainerFactory"
    )
    public void escucharNotificaciones(NotificacionDTO notificacion) {
        System.out.println("📩 Recibida notificacion para: " + notificacion.getDestinatario());
        notificacionService.guardarNotificacion(notificacion);
    }
}
