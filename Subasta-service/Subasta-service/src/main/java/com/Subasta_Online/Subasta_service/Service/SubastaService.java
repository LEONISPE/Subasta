package com.Subasta_Online.Subasta_service.Service;

import com.Subasta_Online.Subasta_service.KafkaProducerConfig.SubastaProducer;
import com.Subasta_Online.Subasta_service.Model.DTOiniciarSubasta;
import com.Subasta_Online.Subasta_service.Model.IniciarSubasta;
import com.Subasta_Online.Subasta_service.Repository.SubastaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SubastaService {

    private final SubastaRepository subastaRepository;
    private final SubastaProducer subastaProducer;

    public DTOiniciarSubasta iniciarSubasta(DTOiniciarSubasta dtOiniciarSubasta) {
        IniciarSubasta iniciarSubasta = new IniciarSubasta();
        iniciarSubasta.setNombre(dtOiniciarSubasta.getNombre());
        iniciarSubasta.setCategoria(dtOiniciarSubasta.getCategoria());
        iniciarSubasta.setDescripcion(dtOiniciarSubasta.getDescripcion());
        iniciarSubasta.setPrecioInicial(dtOiniciarSubasta.getPrecioInicial());
        iniciarSubasta.setEstadoProducto(dtOiniciarSubasta.getEstadoProducto());
        iniciarSubasta.setDuracionSubasta(dtOiniciarSubasta.getDuracionSubasta());

        // Establecer hora de inicio actual tanto en la entidad como en el DTO
        LocalDateTime ahora = LocalDateTime.now();
        iniciarSubasta.setHoraInicio(ahora);
        dtOiniciarSubasta.setHoraInicio(ahora);

        subastaRepository.save(iniciarSubasta);
        subastaProducer.sendMessaguePuja(dtOiniciarSubasta);

        return dtOiniciarSubasta;
    }




}
