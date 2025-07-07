package com.Subasta_Online.Subasta_puja.Controller;

import com.Subasta_Online.Subasta_puja.Model.DTOApuntarsePuja;
import com.Subasta_Online.Subasta_puja.Model.DTOFuturasSubastas;
import com.Subasta_Online.Subasta_puja.Model.DTOpuja;
import com.Subasta_Online.Subasta_puja.Service.PujaServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "api que maneja las subastas", description = "api encargada de manejar " +
        "lo relacioando a las subasta mostar subastas apuntarse etc")
public class ControllerPuja {

    private final PujaServiceImpl pujaService;

    public ControllerPuja(PujaServiceImpl pujaService) {
        this.pujaService = pujaService;
    }

    @GetMapping("/pujas")
    @Operation(summary = "controlador para subastas", description = "enpoint que se encarga de " +
            "obtener todas las listas de subastas")
    public ResponseEntity<List<DTOpuja>> mostrarPujas() {
        List<DTOpuja> pujas = pujaService.mostrarPujas();
        return ResponseEntity.ok().body(pujas);
    }

    @PostMapping("/pujas/verificar-subasta-activa")
    @Operation(summary = "controlador para ser consumido por subasta service", description =
    "este controlador es el encargado de dar informacion a subasta service verificando si " +
            "una subasta atraves del id del producto esta ya en subasta  o no")
    public ResponseEntity<Boolean> verificarSubastaActiva(@RequestBody String idProducto) {
        boolean estaActivo = pujaService.existeSubastaActiva(idProducto);
        return ResponseEntity.ok().body(estaActivo);
    }

    @PostMapping("pujas/apuntarse")
    @Operation(summary = "controlador para apunte de subastas", description = "" +
            "controlador que maneja el apunte de subastas por parte de los usuarios")
    public ResponseEntity<DTOApuntarsePuja> apuntarsePuja(@RequestBody DTOApuntarsePuja dto, @AuthenticationPrincipal Jwt jwt) {
        String mejorPostor = jwt.getClaimAsString("name");

        DTOApuntarsePuja respuesta = pujaService.apuntarsePuja(
                dto.getIdProducto(),
                dto.getPrecioActual(),
                 mejorPostor
        );

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/pujas/futuras")
    @Operation(summary = "controlador para mostar subastas futuras", description = "" +
            "controlador que maneja subastas futuras")
   public ResponseEntity<List<DTOFuturasSubastas>> mostrarSubastasFuturas() {
        List<DTOFuturasSubastas> mostrarFuturasPujas = pujaService.mostrarFuturasSubatsas();
        return ResponseEntity.ok(mostrarFuturasPujas);
    }
}