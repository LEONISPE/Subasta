package com.Subasta_Online.Subasta_puja.Controller;

import com.Subasta_Online.Subasta_puja.Model.DTOApuntarsePuja;
import com.Subasta_Online.Subasta_puja.Model.DTOFuturasSubastas;
import com.Subasta_Online.Subasta_puja.Model.DTOpuja;
import com.Subasta_Online.Subasta_puja.Service.PujaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ControllerPuja {

    private final PujaServiceImpl pujaService;

    public ControllerPuja(PujaServiceImpl pujaService) {
        this.pujaService = pujaService;
    }

    @GetMapping("/pujas")
    public ResponseEntity<List<DTOpuja>> mostrarPujas() {
        List<DTOpuja> pujas = pujaService.mostrarPujas();
        return ResponseEntity.ok(pujas);
    }

    @PostMapping("/pujas/verificar-subasta-activa")
    public ResponseEntity<Boolean> verificarSubastaActiva(@RequestBody String idProducto) {
        boolean estaActivo = pujaService.existeSubastaActiva(idProducto);
        return ResponseEntity.ok(estaActivo);
    }

    @PostMapping("pujas/apuntarse")
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
   public ResponseEntity<List<DTOFuturasSubastas>> mostrarSubastasFuturas() {
        List<DTOFuturasSubastas> mostrarFuturasPujas = pujaService.mostrarFuturasSubatsas();
        return ResponseEntity.ok(mostrarFuturasPujas);
    }
}