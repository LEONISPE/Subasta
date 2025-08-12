package com.subastapuja.subasta.notificacion.config;

import com.subastapuja.subasta.notificacion.model.NotificacionDTO;
import com.subastapuja.subasta.notificacion.model.NotificacionSubastaProgramadaDTO;
import com.subastapuja.subasta.notificacion.service.NotificacionService;
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

    @KafkaListener(
            topics = "subasta-notificacion-iniciada-topic",
            groupId = "notificacion-group",
            containerFactory = "notificacionListenerContainerFactoryFuturasSubastas"
    )
    public void recibirNotificacionesSubatasFuturas(NotificacionSubastaProgramadaDTO notificacion) {
        try {
            System.out.println("Notificación recibida para: " + notificacion.getDestinatario());
            notificacionService.guardarNotificacionSubataFuturas(notificacion);
        } catch (Exception e) {
            System.out.println("Error al guardar notificación para " + notificacion.getDestinatario() + ": " + e.getMessage());
        }
    }
}
