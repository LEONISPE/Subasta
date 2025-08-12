package com.subastapuja.service;

import com.subastapuja.model.DTOApuntarsePuja;
import com.subastapuja.model.DTOFuturasSubastas;
import com.subastapuja.model.DTOiniciarSubasta;
import com.subastapuja.model.DTOpuja;

import java.math.BigDecimal;
import java.util.List;

public interface PujaService {

List<DTOpuja> mostrarPujas();

void  guardarPujaDesdeSubastaIniciada(DTOiniciarSubasta dtoiniciarSubasta);

boolean existeSubastaActiva(String idProducto);

DTOApuntarsePuja apuntarsePuja(String idProducto, BigDecimal nuevoPrecio, String mejorPostor);
void cerrarSubastasFinalizadas();
void  guardarSubastasaFuturo(DTOFuturasSubastas dtoFuturasSubastas);
List<DTOFuturasSubastas> mostrarFuturasSubatsas();
}
