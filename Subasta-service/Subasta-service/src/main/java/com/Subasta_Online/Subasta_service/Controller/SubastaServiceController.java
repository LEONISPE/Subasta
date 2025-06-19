package com.Subasta_Online.Subasta_service.Controller;

import com.Subasta_Online.Subasta_service.Model.DTOFuturasSubastas;
import com.Subasta_Online.Subasta_service.Model.DTOiniciarSubasta;
import com.Subasta_Online.Subasta_service.Service.SubastaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        String nombreUsuario = jwt.getClaimAsString("preferred_username");
        dto.setNombreUsuario(nombreUsuario);
        DTOiniciarSubasta respuesta = subastaService.iniciarSubasta(dto);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("iniciar/usuarios-keycloak")
    public ResponseEntity<List<String>> getUsuarios() {
        return ResponseEntity.ok(subastaService.obtenerUsuariosDesdeKeycloak());
    }

    @PostMapping("/iniciar/subastas-futuras")
    public ResponseEntity<DTOFuturasSubastas> iniciarSubastasFuturas(@RequestBody DTOFuturasSubastas dtoFuturasSubastas ,
                                                                     @AuthenticationPrincipal Jwt jwt){
        String nombreUsuario = jwt.getClaimAsString("name");
        dtoFuturasSubastas.setNombreUsuario(nombreUsuario);
        DTOFuturasSubastas respuestas = subastaService.programarFuturasSubastas(dtoFuturasSubastas);
        return ResponseEntity.ok(respuestas);
    }
}
