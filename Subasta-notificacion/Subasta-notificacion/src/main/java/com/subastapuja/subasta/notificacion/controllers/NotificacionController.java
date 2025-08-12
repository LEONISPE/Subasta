package com.subastapuja.subasta.notificacion.controllers;

import com.subastapuja.subasta.notificacion.model.NotificacionDTO;
import com.subastapuja.subasta.notificacion.model.NotificacionSubastaProgramadaDTO;
import com.subastapuja.subasta.notificacion.service.NotificacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;
    private static final Logger logger = LoggerFactory.getLogger(NotificacionController.class);

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }


    @GetMapping("/mis-notificaciones")
    public ResponseEntity<List<NotificacionDTO>> verMisNotificaciones(@AuthenticationPrincipal Jwt jwt) {
        String usuario = jwt.getClaimAsString("preferred_username");
        List<NotificacionDTO> notis = notificacionService.obtenerNotificacionesUsuario(usuario);
        return ResponseEntity.ok(notis);
    }

    @GetMapping("/subastas/futuras")
    public ResponseEntity<List<NotificacionSubastaProgramadaDTO>> verSubastasFuturas(@AuthenticationPrincipal Jwt jwt) {
        String usuario = jwt.getClaimAsString("preferred_username");
        logger.info("usuario autenticado {}", usuario);
        List<NotificacionSubastaProgramadaDTO> subastas = notificacionService.obtenerSubastasFuturasPorUsuario(usuario);
        return ResponseEntity.ok(subastas);
    }
}

