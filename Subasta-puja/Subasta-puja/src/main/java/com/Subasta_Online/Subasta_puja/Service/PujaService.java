package com.Subasta_Online.Subasta_puja.Service;

import com.Subasta_Online.Subasta_puja.Model.*;
import com.Subasta_Online.Subasta_puja.Repository.PujaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
                        Puja.getDuracionSubasta(),
                        Puja.getPrecioActual()
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

        puja.setEstadoSubasta(EstadoSubasta.ACTIVO);
        pujaRepository.save(puja);
    }

    public boolean existeSubastaActiva(String idProducto) {
        return pujaRepository.findByIdProducto(idProducto)
                .stream()
                .anyMatch(puja -> puja.getEstadoSubasta().equals(EstadoSubasta.ACTIVO));
    }

    public DTOApuntarsePuja apuntarsePuja(String idProducto, BigDecimal nuevoPrecio) {
        Puja puja = pujaRepository.findByIdProducto(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("❌ El producto no está en subasta"));

        // Validar si el nuevo precio es mayor al actual
        BigDecimal precioBase = puja.getPrecioActual();

        if (precioBase == null || precioBase.compareTo(puja.getPrecioInicial()) == 0) {
            // nadie ha pujado aún → usamos el precio inicial como base
            precioBase = puja.getPrecioInicial();
        }

// Si el nuevo precio es menor o igual al precio base, rechazar
        if (nuevoPrecio.compareTo(precioBase) <= 0) {
            throw new IllegalArgumentException("❌ El precio ofrecido debe ser mayor al precio actual o inicial");
        }

        // Actualizar el precio actual con el nuevo precio
        puja.setPrecioActual(nuevoPrecio);
        pujaRepository.save(puja);

        System.out.println("✅ Usted se apuntó a la puja con éxito");

        // Retornar DTO actualizado
        return new DTOApuntarsePuja(puja.getIdProducto(), puja.getPrecioActual());
    }
}