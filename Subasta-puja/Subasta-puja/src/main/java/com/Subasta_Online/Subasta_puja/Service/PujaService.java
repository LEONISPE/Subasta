package com.Subasta_Online.Subasta_puja.Service;

import com.Subasta_Online.Subasta_puja.Model.*;
import com.Subasta_Online.Subasta_puja.Repository.PujaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.Subasta_Online.Subasta_puja.Model.EstadoSubasta.ACTIVO;


@Service
@AllArgsConstructor
public class PujaService {


    private final PujaRepository pujaRepository;


    public List<DTOpuja> mostrarPujas() {
        return pujaRepository.findAll()
                .stream()
                .map(Puja -> new DTOpuja(
                        Puja.getIdProducto(),
                        Puja.getNombre(),
                        Puja.getCategoria(),
                        Puja.getDescripcion(),
                        Puja.getPrecioInicial(),
                        Puja.getHoraInicio(),
                        Puja.getEstadoSubasta(),
                        Puja.getEstadoProducto(),
                        Puja.getDuracionSubasta()
                )).toList();
    }

    public void guardarPujaDesdeSubastaIniciada(DTOiniciarSubasta dtoiniciarSubasta) {
        DTOiniciarSubasta dtOiniciarSubasta = new DTOiniciarSubasta();
        DTOpuja dtopuja = new DTOpuja();

        // Copia los datos comunes
        dtOiniciarSubasta.setIdProducto(dtoiniciarSubasta.getIdProducto());
        dtOiniciarSubasta.setNombre(dtoiniciarSubasta.getNombre());
        dtOiniciarSubasta.setCategoria(dtoiniciarSubasta.getCategoria());
        dtOiniciarSubasta.setDescripcion(dtoiniciarSubasta.getDescripcion());
        dtOiniciarSubasta.setPrecioInicial(dtoiniciarSubasta.getPrecioInicial());
        dtOiniciarSubasta.setHoraInicio(dtoiniciarSubasta.getHoraInicio());
        dtOiniciarSubasta.setDuracionSubasta(dtoiniciarSubasta.getDuracionSubasta());
        dtOiniciarSubasta.setEstadoProducto(dtoiniciarSubasta.getEstadoProducto());

        // Agrega los valores personalizados por ti
        dtopuja.setEstadoSubasta(ACTIVO); // Valor por defecto o según lógica

        Puja puja = new Puja();
        puja.setIdProducto(dtoiniciarSubasta.getIdProducto());
        puja.setNombre(dtoiniciarSubasta.getNombre());
        puja.setCategoria(dtoiniciarSubasta.getCategoria());
        puja.setDescripcion(dtoiniciarSubasta.getDescripcion());
        puja.setPrecioInicial(dtoiniciarSubasta.getPrecioInicial());
        puja.setHoraInicio(dtoiniciarSubasta.getHoraInicio());
        puja.setDuracionSubasta(dtoiniciarSubasta.getDuracionSubasta());
        puja.setEstadoProducto(dtoiniciarSubasta.getEstadoProducto());

        pujaRepository.save(puja);
    }

    public boolean existeSubastaActiva(String idProducto) {
        return pujaRepository.findByIdProducto(idProducto)
                .stream()
                .anyMatch(puja -> puja.getEstadoSubasta().equals(EstadoSubasta.ACTIVO));
    }
}