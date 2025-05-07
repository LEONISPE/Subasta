package com.Subasta_Online.Subasta_service.Service;

import com.Subasta_Online.Subasta_service.Model.DTOiniciarSubasta;
import com.Subasta_Online.Subasta_service.Model.IniciarSubasta;
import com.Subasta_Online.Subasta_service.Repository.SubastaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubastaService {

    private final SubastaRepository subastaRepository;

    public DTOiniciarSubasta iniciarSubasta(DTOiniciarSubasta dtOiniciarSubasta) {
        IniciarSubasta iniciarSubasta = new IniciarSubasta();
        iniciarSubasta.setNombre(dtOiniciarSubasta.getNombre());
        iniciarSubasta.setPrecioInicial(dtOiniciarSubasta.getPrecioInicial());
        iniciarSubasta.setHora(dtOiniciarSubasta.getHora());
        iniciarSubasta.setEstadoSubasta(dtOiniciarSubasta.getEstadoSubasta());
        subastaRepository.save(iniciarSubasta);
        return dtOiniciarSubasta;
    }




}
