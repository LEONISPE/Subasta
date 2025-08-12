package com.subastapuja.subasta.historial.service;

import com.subastapuja.subasta.historial.model.DTOHistorialSubasta;
import com.subastapuja.subasta.historial.model.DTOPujaActualizada;
import com.subastapuja.subasta.historial.model.DTOSubastaFinalizadas;
import com.subastapuja.subasta.historial.model.DTOiniciarSubasta;

import java.util.List;

public interface ServiceSubasta {

    List<DTOHistorialSubasta> getHistorialSubastasPorUsuario(String nombreUsuario);
    void guardarHistorialDesdeSubastaIniciada(DTOiniciarSubasta dtoiniciarSubasta);
void actualizarHistorialConPuja(DTOPujaActualizada dtoPujaActualizada);
    void  finalizarSubastas(DTOSubastaFinalizadas dto);

}
