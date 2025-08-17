package com.subastapuja.subasta.historial.service;

import com.subastapuja.subasta.historial.model.*;
import com.subastapuja.subasta.historial.repository.HistorialSubastasRepository;
import com.subastapuja.subasta.historial.repository.MarcarSubastaPreferidaRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceSubastaImpl implements ServiceSubasta {


    private final HistorialSubastasRepository historialSubastasRepository;
    private final MarcarSubastaPreferidaRepository marcarSubastaPreferidaRepository;
    private static final Logger logger = LoggerFactory.getLogger(ServiceSubastaImpl.class);

    @Override
    public List<DTOHistorialSubasta> getHistorialSubastasPorUsuario(String nombreUsuario) {
        return historialSubastasRepository.findByNombreUsuario(nombreUsuario)
                .stream()
                .map(historialPuja -> new DTOHistorialSubasta(
                        historialPuja.getId(),
                        historialPuja.getNombre(),
                        historialPuja.getEstadoSubasta(),
                        historialPuja.getIdProducto(),
                        historialPuja.getCategoria(),
                        historialPuja.getDescripcion(),
                        historialPuja.getPrecioInicial(),
                        historialPuja.getHoraInicio(),
                        historialPuja.getEstadoProducto(),
                        historialPuja.getDuracionSubasta(),
                        historialPuja.getNombreUsuario(),
                        historialPuja.getPrecioActual(),
                        historialPuja.getMejorPostor()
                )).toList();
    }


    @Override
    public void guardarHistorialDesdeSubastaIniciada(DTOiniciarSubasta dtoiniciarSubasta) {
     logger.info("📦 Recibido DTO para guardar historial {}:" , dtoiniciarSubasta.getNombre());
        logger.info("👤 Nombre de usuario {}:", dtoiniciarSubasta.getNombreUsuario());
        DTOiniciarSubasta dtOiniciarSubasta = new DTOiniciarSubasta();
        DTOHistorialSubasta dtoHistorialSubasta = new DTOHistorialSubasta();


        dtOiniciarSubasta.setIdProducto(dtoiniciarSubasta.getIdProducto());
        dtOiniciarSubasta.setNombre(dtoiniciarSubasta.getNombre());
        dtOiniciarSubasta.setCategoria(dtoiniciarSubasta.getCategoria());
        dtOiniciarSubasta.setDescripcion(dtoiniciarSubasta.getDescripcion());
        dtOiniciarSubasta.setPrecioInicial(dtoiniciarSubasta.getPrecioInicial());
        dtOiniciarSubasta.setHoraInicio(dtoiniciarSubasta.getHoraInicio());
        dtOiniciarSubasta.setDuracionSubasta(dtoiniciarSubasta.getDuracionSubasta());
        dtOiniciarSubasta.setEstadoProducto(dtoiniciarSubasta.getEstadoProducto());
        dtOiniciarSubasta.setNombreUsuario(dtoiniciarSubasta.getNombreUsuario());



        dtoHistorialSubasta.setEstadoSubasta(EstadoSubasta.ACTIVO);


        HistorialPuja historialPuja = new HistorialPuja();
        historialPuja.setIdProducto(dtoiniciarSubasta.getIdProducto());
        historialPuja.setNombre(dtoiniciarSubasta.getNombre());
        historialPuja.setCategoria(dtoiniciarSubasta.getCategoria());
        historialPuja.setDescripcion(dtoiniciarSubasta.getDescripcion());
        historialPuja.setPrecioInicial(dtoiniciarSubasta.getPrecioInicial());
        historialPuja.setHoraInicio(dtoiniciarSubasta.getHoraInicio());
        historialPuja.setDuracionSubasta(dtoiniciarSubasta.getDuracionSubasta());
        historialPuja.setEstadoProducto(dtoiniciarSubasta.getEstadoProducto());

        historialPuja.setEstadoSubasta(EstadoSubasta.ACTIVO);
        historialPuja.setNombreUsuario(dtoiniciarSubasta.getNombreUsuario());
        logger.info("guardando historial para {}:" , dtoiniciarSubasta.getNombreUsuario());
        historialSubastasRepository.save(historialPuja);
        logger.info("historial guardado {}",historialPuja);
    }
    public void actualizarHistorialConPuja(DTOPujaActualizada dtoPujaActualizada) {

        DTOPujaActualizada dtpujaActualizada = new DTOPujaActualizada();

        dtoPujaActualizada.setIdProducto(dtpujaActualizada.getIdProducto());
        dtoPujaActualizada.setPrecioActual(dtpujaActualizada.getPrecioActual());
        dtoPujaActualizada.setMejorPostor(dtpujaActualizada.getMejorPostor());

        HistorialPuja historialPuja = new HistorialPuja();
        historialPuja.setIdProducto(dtoPujaActualizada.getIdProducto());
        historialPuja.setPrecioActual(dtoPujaActualizada.getPrecioActual());
        historialPuja.setMejorPostor(dtoPujaActualizada.getMejorPostor());
        historialSubastasRepository.save(historialPuja);

    }
public void  finalizarSubastas(DTOSubastaFinalizadas dto){
       DTOSubastaFinalizadas dtoSubastaFinalizadas = new DTOSubastaFinalizadas();
       dtoSubastaFinalizadas.setIdProducto(dtoSubastaFinalizadas.getIdProducto());
       dtoSubastaFinalizadas.setEstadoSubasta(dtoSubastaFinalizadas.getEstadoSubasta());

       HistorialPuja historialPuja = new HistorialPuja();
       historialPuja.setIdProducto(dtoSubastaFinalizadas.getIdProducto());
       historialPuja.setEstadoSubasta(dtoSubastaFinalizadas.getEstadoSubasta());
       historialSubastasRepository.save(historialPuja);
}

public void actualizarSubastaPreferida(DTOPujaActualizada dtoPujaActualizada) {

    DTOPujaActualizada dtpujaActualizada = new DTOPujaActualizada();

    dtoPujaActualizada.setIdProducto(dtpujaActualizada.getIdProducto());
    dtoPujaActualizada.setPrecioActual(dtpujaActualizada.getPrecioActual());
    dtoPujaActualizada.setMejorPostor(dtpujaActualizada.getMejorPostor());

    MarcarSubastaPreferida marcarSubastaPreferida = new MarcarSubastaPreferida();
    marcarSubastaPreferida.setIdProducto(dtpujaActualizada.getIdProducto());
    marcarSubastaPreferida.setPrecioActual(dtpujaActualizada.getPrecioActual());
    marcarSubastaPreferida.setMejorPostor(dtpujaActualizada.getMejorPostor());
    marcarSubastaPreferidaRepository.save(marcarSubastaPreferida);
}


@Override
public  List<DTOMarcarsubastasPreferidas> obtenerSubastasPreferidasByUsuario(String nombreUsuario){
     return marcarSubastaPreferidaRepository.findByNombreUsuario(nombreUsuario)
             .stream()
             .map(marcarSubastaPreferida -> new DTOMarcarsubastasPreferidas(
                     marcarSubastaPreferida.getIdProducto(),
                     marcarSubastaPreferida.getNombreUsuario(),
                     marcarSubastaPreferida.getNombre(),
                     marcarSubastaPreferida.getCategoria(),
                     marcarSubastaPreferida.getDescripcion(),
                     marcarSubastaPreferida.getPrecioInicial(),
                     marcarSubastaPreferida.getHoraInicio(),
                     marcarSubastaPreferida.getEstadoSubasta(),
                     marcarSubastaPreferida.getEstadoProducto(),
                     marcarSubastaPreferida.getDuracionSubasta(),
                     marcarSubastaPreferida.getPrecioActual(),
                     marcarSubastaPreferida.getMejorPostor()
             )).toList();
}


    }