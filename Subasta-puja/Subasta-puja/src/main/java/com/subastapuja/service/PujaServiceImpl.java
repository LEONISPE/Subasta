package com.subastapuja.service;

import com.subastapuja.excepcions.PeticionInvalidoException;
import com.subastapuja.kafkaproducer.SubastaProducer;
import com.subastapuja.model.*;
import com.subastapuja.repository.PujaRepository;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.subastapuja.model.EstadoSubasta.ACTIVO;
import static com.subastapuja.model.EstadoSubasta.CERRADO;
import org.slf4j.Logger;


@Service
@AllArgsConstructor
public class PujaServiceImpl  implements PujaService {


    private final PujaRepository pujaRepository;
    private final SubastaProducer subastaProducer;
    private static final Logger logger = LoggerFactory.getLogger(PujaServiceImpl.class);

    @Override
    public List<DTOpuja> mostrarPujas() {
        return pujaRepository.findAll()
                .stream()
                .map(puja -> new DTOpuja(
                        puja.getIdProducto(),
                        puja.getNombre(),
                        puja.getCategoria(),
                        puja.getDescripcion(),
                        puja.getPrecioInicial(),
                        puja.getHoraInicio(),
                        puja.getEstadoSubasta(),
                        puja.getEstadoProducto(),
                        puja.getDuracionSubasta(),
                        puja.getPrecioActual(),
                        puja.getNombreUsuario(),
                        puja.getMejorPostor()
                )).toList();
    }

    @Override
    public List<DTOFuturasSubastas> mostrarFuturasSubatsas() {
        LocalDateTime ahora = LocalDateTime.now();

        return pujaRepository.findAll()
                .stream()
                .filter(p -> p.getFechaFuturaInicio() != null) // Evita errores por null
                .filter(p -> p.getFechaFuturaInicio().isAfter(ahora)) // Solo subastas futuras
                .filter(p -> p.getEstadoSubasta() == EstadoSubasta.CERRADO) // Aún no abiertas
                .map(p -> new DTOFuturasSubastas(
                        p.getIdProducto(),
                        p.getNombre(),
                        p.getCategoria(),
                        p.getDescripcion(),
                        p.getPrecioInicial(),
                        p.getEstadoProducto(),
                        p.getDuracionSubasta(),
                        p.getNombreUsuario(),
                        p.getFechaFuturaInicio()
                ))
                .toList();
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
    public void guardarSubastasaFuturo(DTOFuturasSubastas dtoFuturasSubastas) {
        DTOFuturasSubastas dtoFuturasSubastas1 = new DTOFuturasSubastas();
        DTOpuja dtopuja = new DTOpuja();
        dtoFuturasSubastas1.setIdProducto(dtoFuturasSubastas.getIdProducto());
        dtoFuturasSubastas1.setNombre(dtoFuturasSubastas.getNombre());
        dtoFuturasSubastas1.setCategoria(dtoFuturasSubastas.getCategoria());
        dtoFuturasSubastas1.setDescripcion(dtoFuturasSubastas.getDescripcion());
        dtoFuturasSubastas1.setPrecioInicial(dtoFuturasSubastas.getPrecioInicial());
        dtoFuturasSubastas1.setEstadoProducto(dtoFuturasSubastas.getEstadoProducto());
        dtoFuturasSubastas1.setDuracionSubasta(dtoFuturasSubastas.getDuracionSubasta());
        dtoFuturasSubastas1.setNombreUsuario(dtoFuturasSubastas.getNombreUsuario());
        dtoFuturasSubastas1.setFechaFuturaInicio(dtoFuturasSubastas.getFechaFuturaInicio());

        dtopuja.setEstadoSubasta(CERRADO);

        Puja puja = new Puja();
        puja.setIdProducto(dtoFuturasSubastas.getIdProducto());
        puja.setNombre(dtoFuturasSubastas.getNombre());
        puja.setCategoria(dtoFuturasSubastas.getCategoria());
        puja.setDescripcion(dtoFuturasSubastas.getDescripcion());
        puja.setPrecioInicial(dtoFuturasSubastas.getPrecioInicial());
        puja.setPrecioInicial(dtoFuturasSubastas.getPrecioInicial());
        puja.setEstadoProducto(dtoFuturasSubastas.getEstadoProducto());
        puja.setDuracionSubasta(dtoFuturasSubastas.getDuracionSubasta());
        puja.setNombreUsuario(dtoFuturasSubastas.getNombreUsuario());
        puja.setFechaFuturaInicio(dtoFuturasSubastas.getFechaFuturaInicio());
        puja.setEstadoSubasta(CERRADO);
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
                .orElseThrow(() -> new PeticionInvalidoException("No existe esa  puja con el idProducto: " + idProducto));

        BigDecimal precioBase = puja.getPrecioActual();
        if (precioBase == null || precioBase.compareTo(puja.getPrecioInicial()) == 0) {
            precioBase = puja.getPrecioInicial();
        }

        if (nuevoPrecio.compareTo(precioBase) <= 0) {
            throw new PeticionInvalidoException("❌ El precio ofrecido debe ser mayor al precio actual o inicial");
        }
        String anteriorPostor = puja.getMejorPostor();
        String duenioSubasta = puja.getNombreUsuario();

        if (duenioSubasta != null && duenioSubasta.equals(mejorPostor)) {
            throw new PeticionInvalidoException("❌ No puedes apuntarte a tu propia subasta");
        }
        if (puja.getEstadoSubasta().equals(EstadoSubasta.CERRADO)) {
            throw new PeticionInvalidoException("no puedes apuntarte a subastas cerradas ");
        }


        puja.setPrecioActual(nuevoPrecio);
        puja.setMejorPostor(mejorPostor);
        pujaRepository.save(puja);

        logger.info("usted se apunto ala puja con exito");

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

        NotificacionDuenoSubastaDTO dtoDueno = new NotificacionDuenoSubastaDTO();
        dtoDueno.setDestinatario(duenioSubasta);
        dtoDueno.setIdProducto(idProducto);
        dtoDueno.setMensaje("Un nuevo postor ha participado en tu subasta: " + mejorPostor);
        subastaProducer.sendNotificacionDueno(dtoDueno);

        DTOParticipacionSubasta dtoParticipacionSubasta = new DTOParticipacionSubasta();
        dtoParticipacionSubasta.setMejorPostor(mejorPostor);
        dtoParticipacionSubasta.setPrecioActual(nuevoPrecio);
        subastaProducer.sendParticipacionSubasta(dtoParticipacionSubasta);

        return new DTOApuntarsePuja(idProducto, nuevoPrecio, mejorPostor);
    }

    @Scheduled(fixedRate = 60000)
    public void cerrarSubastasFinalizadas() {
        List<Puja> subastasActivas = pujaRepository.findByEstadoSubasta(EstadoSubasta.ACTIVO);

        for (Puja puja : subastasActivas) {
            LocalDateTime horaFin = puja.getHoraInicio().plus(puja.getDuracionSubasta());

            if (LocalDateTime.now().isAfter(horaFin)) {
                puja.setEstadoSubasta(EstadoSubasta.CERRADO);
                pujaRepository.save(puja);

                logger.info("subasta cerrada {} : ", puja.getMejorPostor());

                // Crear DTO
                DTOSubastaFinalizadas dto = new DTOSubastaFinalizadas();
                dto.setIdProducto(puja.getIdProducto());
                dto.setEstadoSubasta(EstadoSubasta.CERRADO);

                // Enviar mensaje Kafka
                subastaProducer.sendSubastaFinalizada(dto);
            }
        }
    }

    @Scheduled(fixedRate = 60000) // cada minuto
    public void abrirSubastasProgramadas() {
        List<Puja> subastasProgramadas = pujaRepository.findByEstadoSubasta(EstadoSubasta.ACTIVO);

        for (Puja puja : subastasProgramadas) {
            LocalDateTime fechaProgramada = puja.getFechaFuturaInicio();
            LocalDateTime ahora = LocalDateTime.now();

            if (fechaProgramada == null) {
                logger.warn("Subasta '{}' no tiene fecha de inicio programada, se omite.", puja.getNombre());
                continue; // saltamos esta y seguimos con las demás
            }

            // Si la hora actual es igual o ya pasó la hora programada
            if (!ahora.isBefore(fechaProgramada)) {
                puja.setEstadoSubasta(EstadoSubasta.ACTIVO);
                puja.setHoraInicio(ahora);
                pujaRepository.save(puja);

                logger.info("Subasta activada: {}", puja.getNombre());
            }
        }
    }
}