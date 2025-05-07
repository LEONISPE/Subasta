package com.Subasta_Online.Subasta_historial.Service;

import com.Subasta_Online.Subasta_historial.Model.DTOHistorialSubasta;
import com.Subasta_Online.Subasta_historial.Model.EstadoSubasta;
import com.Subasta_Online.Subasta_historial.Model.HistorialPuja;
import com.Subasta_Online.Subasta_historial.Repository.HistorialSubastasRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceSubasta {


    private final HistorialSubastasRepository historialSubastasRepository;

    public List<DTOHistorialSubasta> getallHistorialSubastas() {
        return historialSubastasRepository.findAll()
                .stream()
                .map(HistorialPuja -> new DTOHistorialSubasta(
                        HistorialPuja.getId(),
                        HistorialPuja.getNombre(),
                        HistorialPuja.getPrecioFinal(),
                        HistorialPuja.getHora(),
                        HistorialPuja.getEstadoSubasta(),
                        HistorialPuja.getPrecioInicial()
                )).toList();

    }

    public List<DTOHistorialSubasta> getaHistorialSubastaByNombre(String nombre) {
        return historialSubastasRepository.findByNombre(nombre)
                .stream()
                .map(HistorialPuja -> new DTOHistorialSubasta(
                        HistorialPuja.getId(),
                        HistorialPuja.getNombre(),
                        HistorialPuja.getPrecioFinal(),
                        HistorialPuja.getHora(),
                        HistorialPuja.getEstadoSubasta(),
                        HistorialPuja.getPrecioInicial()
                )).toList();

    }

    public List<DTOHistorialSubasta> getHistorialSubastaByPrecioInicial(float precioInicial) {
        return historialSubastasRepository.findByPrecioInicial(precioInicial)
                .stream()
                .map(HistorialPuja -> new DTOHistorialSubasta(
                        HistorialPuja.getId(),
                        HistorialPuja.getNombre(),
                        HistorialPuja.getPrecioFinal(),
                        HistorialPuja.getHora(),
                        HistorialPuja.getEstadoSubasta(),
                        HistorialPuja.getPrecioInicial()
                )).toList();

    }
    public List<DTOHistorialSubasta> getHistorialSubastaByHora(LocalDateTime hora) {
        return historialSubastasRepository.findByHora(hora)
                .stream()
                .map(HistorialPuja -> new DTOHistorialSubasta(
                        HistorialPuja.getId(),
                        HistorialPuja.getNombre(),
                        HistorialPuja.getPrecioFinal(),
                        HistorialPuja.getHora(),
                        HistorialPuja.getEstadoSubasta(),
                        HistorialPuja.getPrecioInicial()
                )) .toList();

}
    public List<DTOHistorialSubasta> getHistorialByEstadoSubasta(EstadoSubasta estadoSubasta) {
        return historialSubastasRepository.findByEstadoSubasta(estadoSubasta)
                .stream()
                .map(HistorialPuja -> new DTOHistorialSubasta(
                        HistorialPuja.getId(),
                        HistorialPuja.getNombre(),
                        HistorialPuja.getPrecioFinal(),
                        HistorialPuja.getHora(),
                        HistorialPuja.getEstadoSubasta(),
                        HistorialPuja.getPrecioInicial()
                )).toList();


    }


    }