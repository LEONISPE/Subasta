package com.Subasta_Online.Subasta_historial.Service;

import com.Subasta_Online.Subasta_historial.Model.DTOHistorialSubasta;
import com.Subasta_Online.Subasta_historial.Model.EstadoSubasta;

import java.time.LocalDateTime;
import java.util.List;

public interface ServiceSubasta {

    List<DTOHistorialSubasta> getallHistorialSubastas();

    List<DTOHistorialSubasta> getaHistorialSubastaByNombre(String nombre);

    List<DTOHistorialSubasta> getHistorialSubastaByPrecioInicial(float precioInicial);

    List<DTOHistorialSubasta> getHistorialSubastaByHora(LocalDateTime hora);

    List<DTOHistorialSubasta> getHistorialByEstadoSubasta(EstadoSubasta estadoSubasta);
}
