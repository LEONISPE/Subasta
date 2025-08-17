package com.subastapuja.subasta.historial.service;

import com.subastapuja.subasta.historial.model.*;

import java.util.List;

public interface ServiceSubasta {

    List<DTOHistorialSubasta> getHistorialSubastasPorUsuario(String nombreUsuario);
    void guardarHistorialDesdeSubastaIniciada(DTOiniciarSubasta dtoiniciarSubasta);
void actualizarHistorialConPuja(DTOPujaActualizada dtoPujaActualizada);
    void  finalizarSubastas(DTOSubastaFinalizadas dto);
    List<DTOMarcarsubastasPreferidas> obtenerSubastasPreferidasByUsuario(String nombreUsuario);
}
