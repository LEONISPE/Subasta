package com.Subasta_Online.Subasta_puja.Service;

import com.Subasta_Online.Subasta_puja.Model.DTOpuja;
import com.Subasta_Online.Subasta_puja.Model.Puja;
import com.Subasta_Online.Subasta_puja.Repository.PujaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class pujaService {


    private final PujaRepository pujaRepository;


    public DTOpuja guardarPuja(DTOpuja DTOpuja) {
        Puja puja = new Puja();
        puja.setNombre(DTOpuja.getNombre());
        puja.setPrecioInicial(DTOpuja.getPrecioInicial());
        puja.setHora(DTOpuja.getHora());
        puja.setPrecioInicial(DTOpuja.getPrecioInicial());
        pujaRepository.save(puja);
        return DTOpuja;

    }

    public List<DTOpuja> mostrarPujas() {
return pujaRepository.findAll()
        .stream()
        .map(Puja -> new DTOpuja(
                Puja.getNombre(),
                Puja.getPrecioInicial(),
                Puja.getHora(),
                Puja.getEstadoSubasta()
        )) .toList();
    }


}