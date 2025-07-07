package com.Subasta_Online.Subasta_historial.Service;

import com.Subasta_Online.Subasta_historial.Model.*;
import com.Subasta_Online.Subasta_historial.Repository.HistorialSubastasRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceSubastaImpl implements ServiceSubasta {


    private final HistorialSubastasRepository historialSubastasRepository;

    @Override
    public List<DTOHistorialSubasta> getHistorialSubastasPorUsuario(String nombreUsuario) {
        return historialSubastasRepository.findByNombreUsuario(nombreUsuario)
                .stream()
                .map(HistorialPuja -> new DTOHistorialSubasta(
                        HistorialPuja.getId(),
                        HistorialPuja.getNombre(),
                        HistorialPuja.getEstadoSubasta(),
                        HistorialPuja.getIdProducto(),
                        HistorialPuja.getCategoria(),
                        HistorialPuja.getDescripcion(),
                        HistorialPuja.getPrecioInicial(),
                        HistorialPuja.getHoraInicio(),
                        HistorialPuja.getEstadoProducto(),
                        HistorialPuja.getDuracionSubasta(),
                        HistorialPuja.getNombreUsuario(),
                        HistorialPuja.getPrecioActual(),
                        HistorialPuja.getMejorPostor()
                )).toList();
    }


    @Override
    public void guardarHistorialDesdeSubastaIniciada(DTOiniciarSubasta dtoiniciarSubasta) {
        System.out.println("📦 Recibido DTO para guardar historial: " + dtoiniciarSubasta);
        System.out.println("👤 Nombre de usuario: " + dtoiniciarSubasta.getNombreUsuario());
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
        System.out.println("Guardando historial para: " + historialPuja.getNombreUsuario());
        historialSubastasRepository.save(historialPuja);
        System.out.println("✅ Historial guardado: " + historialPuja);
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


    }