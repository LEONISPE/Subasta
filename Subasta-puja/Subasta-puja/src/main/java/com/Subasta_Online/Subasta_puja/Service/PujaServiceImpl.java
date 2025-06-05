package com.Subasta_Online.Subasta_puja.Service;

import com.Subasta_Online.Subasta_puja.KafkaProducer.SubastaProducer;
import com.Subasta_Online.Subasta_puja.Model.*;
import com.Subasta_Online.Subasta_puja.Repository.PujaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.Subasta_Online.Subasta_puja.Model.EstadoSubasta.ACTIVO;


@Service
@AllArgsConstructor
public class PujaServiceImpl  implements PujaService {


    private final PujaRepository pujaRepository;
    private final SubastaProducer subastaProducer;


    @Override
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
                        Puja.getPrecioActual(),
                        Puja.getNombreUsuario(),
                        Puja.getMejorPostor()
                )).toList();
    }

    @Override
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
        dtOiniciarSubasta.setNombreUsuario(dtoiniciarSubasta.getNombreUsuario());


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
        puja.setNombreUsuario(dtoiniciarSubasta.getNombreUsuario());
        pujaRepository.save(puja);
    }

    @Override
    public boolean existeSubastaActiva(String idProducto) {
        return pujaRepository.findByIdProducto(idProducto)
                .stream()
                .anyMatch(puja -> puja.getEstadoSubasta().equals(EstadoSubasta.ACTIVO));
    }

    @Override
    public DTOApuntarsePuja apuntarsePuja(String idProducto, BigDecimal nuevoPrecio, String mejorPostor) {
        Puja puja = pujaRepository.findByIdProducto(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("❌ El producto no está en subasta"));

        BigDecimal precioBase = puja.getPrecioActual();
        if (precioBase == null || precioBase.compareTo(puja.getPrecioInicial()) == 0) {
            precioBase = puja.getPrecioInicial();
        }

        if (nuevoPrecio.compareTo(precioBase) <= 0) {
            throw new IllegalArgumentException("❌ El precio ofrecido debe ser mayor al precio actual o inicial");
        }

        String anteriorPostor = puja.getMejorPostor();
        String duenioSubasta = puja.getNombreUsuario(); // Asumiendo que guardas este campo

        puja.setPrecioActual(nuevoPrecio);
        puja.setMejorPostor(mejorPostor);
        pujaRepository.save(puja);

        System.out.println("✅ Usted se apuntó a la puja con éxito");

        DTOPujaActualizada dtoPujaActualizada = new DTOPujaActualizada();
        dtoPujaActualizada.setIdProducto(idProducto);
        dtoPujaActualizada.setPrecioActual(nuevoPrecio);
        dtoPujaActualizada.setMejorPostor(mejorPostor);
        subastaProducer.sendMessaguePuja(dtoPujaActualizada);

        // Notificaciones
        if (anteriorPostor != null && !anteriorPostor.equals(mejorPostor)) {
            NotificacionMejorPostorDTO dtoMejorPostor = new NotificacionMejorPostorDTO();
            dtoMejorPostor.setDestinatario(anteriorPostor);
            dtoMejorPostor.setIdProducto(idProducto);
            dtoMejorPostor.setMensaje("Tu puja ha sido superada por " + mejorPostor);
            subastaProducer.sendNotificacionMejorPostor(dtoMejorPostor);
        }

        NotificacionDueñoSubastaDTO dtoDueno = new NotificacionDueñoSubastaDTO();
        dtoDueno.setDestinatario(duenioSubasta);
        dtoDueno.setIdProducto(idProducto);
        dtoDueno.setMensaje("Un nuevo postor ha participado en tu subasta: " + mejorPostor);
        subastaProducer.sendNotificacionDueno(dtoDueno);

        return new DTOApuntarsePuja(idProducto, nuevoPrecio, mejorPostor);
    }
}