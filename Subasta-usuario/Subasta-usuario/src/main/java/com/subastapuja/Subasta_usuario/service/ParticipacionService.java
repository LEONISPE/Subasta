package com.subastapuja.Subasta_usuario.service;

import com.subastapuja.Subasta_usuario.model.DTO.DTOParticipacionSubasta;
import com.subastapuja.Subasta_usuario.model.DTO.ValoracionParticipacion;
import com.subastapuja.Subasta_usuario.model.Participacion;
import com.subastapuja.Subasta_usuario.repository.ParticipacionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ParticipacionService {

    private final ParticipacionRepository participacionRepository;

    public ParticipacionService(ParticipacionRepository participacionRepository) {
        this.participacionRepository = participacionRepository;
    }

    public void guardarParticipacionSubasta(DTOParticipacionSubasta participacion) {
        BigDecimal precio = participacion.getPrecioActual();

        // 🔍 Clasificar el precio según el enum
        ValoracionParticipacion valoracion = ValoracionParticipacion.clasificarPorMonto(precio.doubleValue());

        // 📝 Seteamos el nivel en el DTO (opcional si vas a devolverlo)
        participacion.setValoracionParticipacion(valoracion);

        // 🗃️ Creamos y llenamos la entidad para guardar en la base
        Participacion entidad = new Participacion();
        entidad.setMejorPostor(participacion.getMejorPostor());
        entidad.setPrecioActual(precio);
        entidad.setValoracionParticipacion(valoracion);

        participacionRepository.save(entidad);

        System.out.println("➡️ Participación guardada: " + participacion.getMejorPostor() +
                " con $" + precio + " (" + valoracion + ")");
    }


}
