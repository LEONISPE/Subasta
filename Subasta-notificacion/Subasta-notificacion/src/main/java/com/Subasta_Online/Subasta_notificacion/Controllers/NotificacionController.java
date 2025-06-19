package com.Subasta_Online.Subasta_notificacion.Controllers;

import com.Subasta_Online.Subasta_notificacion.Model.NotificacionDTO;
import com.Subasta_Online.Subasta_notificacion.Model.NotificacionSubastaProgramadaDTO;
import com.Subasta_Online.Subasta_notificacion.Service.NotificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    // solo para pruebas
   // @PostMapping
    //public ResponseEntity<String> recibirNotificacion(@RequestBody NotificacionDTO dto) {
      //  notificacionService.guardarNotificacion(dto);
        //return ResponseEntity.ok("✅ Notificación guardada");
    //}

    @GetMapping("/mis-notificaciones")
    public ResponseEntity<List<NotificacionDTO>> verMisNotificaciones(@AuthenticationPrincipal Jwt jwt) {
        String usuario = jwt.getClaimAsString("name");
        List<NotificacionDTO> notis = notificacionService.obtenerNotificacionesUsuario(usuario);
        return ResponseEntity.ok(notis);
    }

    @GetMapping("/mis-subastas/futuras")
    public ResponseEntity<List<NotificacionSubastaProgramadaDTO>> verMisSubastasFuturas(@AuthenticationPrincipal Jwt jwt) {
        String usuario = jwt.getClaimAsString("preferred_username");
        System.out.println("Usuario autenticado: " + usuario);
        List<NotificacionSubastaProgramadaDTO> subastas = notificacionService.obtenerSubastasFuturasPorUsuario(usuario);
        return ResponseEntity.ok(subastas);
    }
}

