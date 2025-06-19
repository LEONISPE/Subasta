package com.Subasta_Online.Subasta_puja.Service;

import com.Subasta_Online.Subasta_puja.Model.*;

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
