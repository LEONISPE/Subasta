package com.Subasta_Online.Subasta_historial.Controller;

import com.Subasta_Online.Subasta_historial.Model.DTOHistorialSubasta;
import com.Subasta_Online.Subasta_historial.Service.ServiceSubastaImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ControllerHistorial {

    private final ServiceSubastaImpl serviceSubasta;

    public ControllerHistorial(ServiceSubastaImpl serviceSubasta) {
        this.serviceSubasta = serviceSubasta;
    }

    @GetMapping("/historial")
    public ResponseEntity<List<DTOHistorialSubasta>> historialSubasta(@AuthenticationPrincipal Jwt jwt) {
        String nombreUsuario = jwt.getClaimAsString("preferred_username");
        System.out.println("🔎 Consultando historial para usuario: " + nombreUsuario);
        List<DTOHistorialSubasta> dtoHistorialSubastas = serviceSubasta.getHistorialSubastasPorUsuario(nombreUsuario);
        System.out.println("📄 Resultados encontrados: " + dtoHistorialSubastas.size());
        return ResponseEntity.ok(dtoHistorialSubastas);
    }
}
