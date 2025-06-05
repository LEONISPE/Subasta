package com.Subasta_Online.Subasta_historial.Service;

import com.Subasta_Online.Subasta_historial.Model.DTOHistorialSubasta;
import com.Subasta_Online.Subasta_historial.Model.DTOPujaActualizada;
import com.Subasta_Online.Subasta_historial.Model.DTOiniciarSubasta;
import com.Subasta_Online.Subasta_historial.Model.EstadoSubasta;

import java.time.LocalDateTime;
import java.util.List;

public interface ServiceSubasta {

    List<DTOHistorialSubasta> getHistorialSubastasPorUsuario(String nombreUsuario);
    void guardarHistorialDesdeSubastaIniciada(DTOiniciarSubasta dtoiniciarSubasta);
void actualizarHistorialConPuja(DTOPujaActualizada dtoPujaActualizada);

}
