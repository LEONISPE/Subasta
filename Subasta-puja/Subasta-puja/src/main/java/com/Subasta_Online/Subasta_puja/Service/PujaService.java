package com.Subasta_Online.Subasta_puja.Service;

import com.Subasta_Online.Subasta_puja.Model.DTOiniciarSubasta;
import com.Subasta_Online.Subasta_puja.Model.DTOpuja;
import com.Subasta_Online.Subasta_puja.Model.Puja;
import com.Subasta_Online.Subasta_puja.Repository.PujaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.Subasta_Online.Subasta_puja.Model.EstadoSubasta.ACTIVO;


@Service
@AllArgsConstructor
public class PujaService {


    private final PujaRepository pujaRepository;


    public DTOpuja guardarPuja(DTOpuja DTOpuja) {
        Puja puja = new Puja();
        puja.setNombre(DTOpuja.getNombre());
        puja.setCategoria(DTOpuja.getCategoria());
        puja.setDescripcion(DTOpuja.getDescripcion());
        puja.setPrecioInicial(DTOpuja.getPrecioInicial());
        puja.setHoraInicio(DTOpuja.getHoraInicio());
        puja.setEstadoSubasta(DTOpuja.getEstadoSubasta());
        puja.setEstadoProducto(DTOpuja.getEstadoProducto());
        puja.setDuracionSubasta(DTOpuja.getDuracionSubasta());
        pujaRepository.save(puja);
        return DTOpuja;

    }

    public List<DTOpuja> mostrarPujas() {
return pujaRepository.findAll()
        .stream()
        .map(Puja -> new DTOpuja(
                Puja.getNombre(),
                Puja.getCategoria(),
                Puja.getDescripcion(),
                Puja.getPrecioInicial(),
                Puja.getHoraInicio(),
                Puja.getEstadoSubasta(),
                Puja.getEstadoProducto(),
                Puja.getDuracionSubasta()
        )) .toList();
    }

    public void guardarPujaDesdeSubastaIniciada(DTOiniciarSubasta dtoiniciarSubasta) {
        DTOpuja dtoPuja = new DTOpuja();

        // Copia los datos comunes
        dtoPuja.setNombre(dtoiniciarSubasta.getNombre());
        dtoPuja.setCategoria(dtoiniciarSubasta.getCategoria());
        dtoPuja.setDescripcion(dtoiniciarSubasta.getDescripcion());
        dtoPuja.setPrecioInicial(dtoiniciarSubasta.getPrecioInicial());
        dtoPuja.setHoraInicio(dtoiniciarSubasta.getHoraInicio());
        dtoPuja.setDuracionSubasta(dtoiniciarSubasta.getDuracionSubasta());
        dtoPuja.setEstadoProducto(dtoiniciarSubasta.getEstadoProducto());

        // Agrega los valores personalizados por ti
        dtoPuja.setEstadoSubasta(ACTIVO); // Valor por defecto o según lógica

        Puja puja = new Puja();
        puja.setNombre(dtoPuja.getNombre());
        puja.setCategoria(dtoPuja.getCategoria());
        puja.setDescripcion(dtoPuja.getDescripcion());
        puja.setPrecioInicial(dtoPuja.getPrecioInicial());
        puja.setHoraInicio(dtoPuja.getHoraInicio());
        puja.setDuracionSubasta(dtoPuja.getDuracionSubasta());
        puja.setEstadoProducto(dtoPuja.getEstadoProducto());
        puja.setEstadoSubasta(dtoPuja.getEstadoSubasta());

        pujaRepository.save(puja);
    }


}