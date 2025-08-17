package com.subastapuja.subasta.historial.controller;

import com.subastapuja.subasta.historial.model.DTOHistorialSubasta;
import com.subastapuja.subasta.historial.service.ServiceSubastaImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "api para manejar historial de usuario")
public class ControllerHistorial {

    private final ServiceSubastaImpl serviceSubasta;
    private static final Logger logger = LoggerFactory.getLogger(ControllerHistorial.class);

    public ControllerHistorial(ServiceSubastaImpl serviceSubasta) {
        this.serviceSubasta = serviceSubasta;
    }

    @GetMapping("/historial")
    @Operation( summary = "controlador para obtener historial de subastas",description = "" +
            "controlador get para obtener el historial de subastas por parte del usuario")
    public ResponseEntity<List<DTOHistorialSubasta>> historialSubasta(@AuthenticationPrincipal Jwt jwt) {
        String nombreUsuario = jwt.getClaimAsString("preferred_username");
        logger.info("consultando historial de usuario {}:", nombreUsuario);
        List<DTOHistorialSubasta> dtoHistorialSubastas = serviceSubasta.getHistorialSubastasPorUsuario(nombreUsuario);
       logger.info("resultados encontrados {} : ", dtoHistorialSubastas.size());
        return ResponseEntity.ok().body(dtoHistorialSubastas);
    }

    @GetMapping("/subastas-preferidas")
    public ResponseEntity<List<DTOMarcarsubastasPreferidas>> subastasPreferidas(@AuthenticationPrincipal Jwt jwt) {
        String nombreUsuario = jwt.getClaimAsString("preferred_username");
        List<DTOMarcarsubastasPreferidas> dtoMarcarsubastasPreferidas = serviceSubasta.obtenerSubastasPreferidasByUsuario(nombreUsuario);
        return ResponseEntity.ok().body(dtoMarcarsubastasPreferidas);
    }

}
