package com.subastapuja.service;

import com.subastapuja.model.*;

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
    DTOMarcarsubastasPreferidas marcarSubastaFavorita(String idProducto, String nombreUsuario);
    List<Puja> obtenerFavoritas(String nombreUsuario);
    DTOComentarios EnviarComentariosaProducto(DTOComentarios dtoComentarios);
}
