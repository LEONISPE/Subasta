package com.Subasta_Online.Subasta_puja.Controller;

import com.Subasta_Online.Subasta_puja.Model.DTOApuntarsePuja;
import com.Subasta_Online.Subasta_puja.Model.DTOpuja;
import com.Subasta_Online.Subasta_puja.Model.DTOpujaID;
import com.Subasta_Online.Subasta_puja.Service.PujaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ControllerPuja {

    private final PujaService pujaService;

    public ControllerPuja(PujaService pujaService) {
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
    public ResponseEntity<DTOApuntarsePuja> apuntarsePuja(@RequestBody DTOApuntarsePuja dto) {
        DTOApuntarsePuja respuesta = pujaService.apuntarsePuja(dto.getIdProducto(), dto.getPrecioActual());
        return ResponseEntity.ok(respuesta);
    }

}
