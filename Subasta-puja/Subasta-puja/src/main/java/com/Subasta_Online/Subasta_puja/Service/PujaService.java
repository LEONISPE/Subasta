package com.Subasta_Online.Subasta_puja.Service;

import com.Subasta_Online.Subasta_puja.Model.DTOApuntarsePuja;
import com.Subasta_Online.Subasta_puja.Model.DTOiniciarSubasta;
import com.Subasta_Online.Subasta_puja.Model.DTOpuja;

import java.math.BigDecimal;
import java.util.List;

public interface PujaService {

List<DTOpuja> mostrarPujas();

void  guardarPujaDesdeSubastaIniciada(DTOiniciarSubasta dtoiniciarSubasta);

boolean existeSubastaActiva(String idProducto);

DTOApuntarsePuja apuntarsePuja(String idProducto, BigDecimal nuevoPrecio, String mejorPostor);
}
