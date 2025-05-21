package com.Subasta_Online.Subasta_service.Controller;

import com.Subasta_Online.Subasta_service.Model.DTOiniciarSubasta;
import com.Subasta_Online.Subasta_service.Service.SubastaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class SubastaServiceController {

    private final SubastaService subastaService;

    public SubastaServiceController(SubastaService subastaService) {
        this.subastaService = subastaService;
    }


    @PostMapping("/iniciar")
    public ResponseEntity<DTOiniciarSubasta> iniciarSubasta(@RequestBody DTOiniciarSubasta dto) {
        subastaService.iniciarSubasta(dto);
        return ResponseEntity.ok().body(dto);
    }
}
