package com.Subasta_Online.Subasta_puja.Controller;

import com.Subasta_Online.Subasta_puja.Model.DTOpuja;
import com.Subasta_Online.Subasta_puja.Service.PujaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pujas")
public class ControllerPuja {

    private final PujaService pujaService;

    public ControllerPuja(PujaService pujaService) {
        this.pujaService = pujaService;
    }

    @GetMapping
    public ResponseEntity<DTOpuja> mostrarPujas() {
        pujaService.mostrarPujas();
        return ResponseEntity.ok().body(new DTOpuja());
    }
}
