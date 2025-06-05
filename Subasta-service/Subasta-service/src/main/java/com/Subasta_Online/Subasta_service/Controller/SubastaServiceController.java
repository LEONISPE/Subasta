package com.Subasta_Online.Subasta_service.Controller;

import com.Subasta_Online.Subasta_service.Model.DTOiniciarSubasta;
import com.Subasta_Online.Subasta_service.Service.SubastaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class SubastaServiceController {

    private final SubastaServiceImpl subastaService;

    public SubastaServiceController(SubastaServiceImpl subastaService) {
        this.subastaService = subastaService;
    }


    @PostMapping("/iniciar")
    public ResponseEntity<DTOiniciarSubasta> iniciarSubasta(
            @RequestBody DTOiniciarSubasta dto,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String nombreUsuario = jwt.getClaimAsString("preferred_username"); // o "name"
        dto.setNombreUsuario(nombreUsuario); // agregar este campo en el DTO
        DTOiniciarSubasta respuesta = subastaService.iniciarSubasta(dto);
        return ResponseEntity.ok(respuesta);
    }
}
