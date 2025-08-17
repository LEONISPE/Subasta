package com.subastapuja.subasta.historial.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MarcarSubastaPreferidaRepository extends JpaRepository<MarcarSubastaPreferida, Long> {

    List<MarcarSubastaPreferida> findByNombreUsuario(String nombreUsuario);
}
