package com.subastapuja.subasta.historial.repository;

import com.subastapuja.subasta.historial.model.HistorialPuja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialSubastasRepository extends JpaRepository<HistorialPuja, Long> {


    List<HistorialPuja> findByNombreUsuario(String nombreUsuario);


}
