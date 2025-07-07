package com.Subasta_Online.Subasta_service.Controller;

import com.Subasta_Online.Subasta_service.Model.DTOFuturasSubastas;
import com.Subasta_Online.Subasta_service.Model.DTOMostarSubastas;
import com.Subasta_Online.Subasta_service.Model.DTOiniciarSubasta;
import com.Subasta_Online.Subasta_service.Service.SubastaServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/api")
@Tag(name = "api que maneja la iniciacion de las subasta tanto ahora como futuras")
@RestController
public class SubastaServiceController {

    private final SubastaServiceImpl subastaService;

    public SubastaServiceController(SubastaServiceImpl subastaService) {
        this.subastaService = subastaService;
    }


    @PostMapping("/iniciar")
    @Operation(summary = "controlador para inciar la subasta", description = "inicia la subasta " +
            "con dto iniciar subasta y extrae el nombre de usuario del usuario")
    public ResponseEntity<DTOiniciarSubasta> iniciarSubasta(
            @RequestBody  @Valid DTOiniciarSubasta dto,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String nombreUsuario = jwt.getClaimAsString("preferred_username");
        dto.setNombreUsuario(nombreUsuario);
        DTOiniciarSubasta respuesta = subastaService.iniciarSubasta(dto);
        URI location = URI.create("/api/{id}" + respuesta.getId());
        return ResponseEntity.created(location).body(respuesta);
    }

    @GetMapping("/{id}")
    @Operation(summary = "api para obtener subasta por id " , description =
    "se encarga de buscar las subastas por su id ")
    public ResponseEntity<DTOMostarSubastas> obtenerSubastaPorId(@PathVariable Long id) {
        DTOMostarSubastas dtoMostarSubastas = subastaService.getSubastaPorId(id);
        return ResponseEntity.ok(dtoMostarSubastas);
    }

    @GetMapping("iniciar/usuarios-keycloak")
    @Operation(summary = "controlador encargado de obtener los usuarios desde keyclocak",description =
    "para saber que usuario inicio la subasta se necesita extraer el nombre de usuario " +
            "esto lo hace este controlador ")
    public ResponseEntity<List<String>> getUsuarios() {
        return ResponseEntity.ok(subastaService.obtenerUsuariosDesdeKeycloak());
    }

    @PostMapping("/iniciar/subastas-futuras")
    @Operation(summary = "controlador de iniciar subastas futuras ", description =
    "al igual que  el controlador iniciar subastas este controlador se encarga de iniciar subastas pero " +
            "programadas para el futuro")
    public ResponseEntity<DTOFuturasSubastas> iniciarSubastasFuturas(@RequestBody DTOFuturasSubastas dtoFuturasSubastas ,
                                                                     @AuthenticationPrincipal Jwt jwt){
        String nombreUsuario = jwt.getClaimAsString("name");
        dtoFuturasSubastas.setNombreUsuario(nombreUsuario);
        DTOFuturasSubastas respuestas = subastaService.programarFuturasSubastas(dtoFuturasSubastas);
        return ResponseEntity.ok(respuestas);
    }
}
