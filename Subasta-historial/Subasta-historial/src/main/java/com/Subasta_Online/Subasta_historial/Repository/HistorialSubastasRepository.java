package com.Subasta_Online.Subasta_historial.Repository;

import com.Subasta_Online.Subasta_historial.Model.EstadoSubasta;
import com.Subasta_Online.Subasta_historial.Model.HistorialPuja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HistorialSubastasRepository extends JpaRepository<HistorialPuja, Long> {

    List<HistorialPuja> findByNombre(String nombre);

    List<HistorialPuja> findByPrecioInicial(float precioInicial);

    List<HistorialPuja> findByHora(LocalDateTime hora);

    List<HistorialPuja> findByEstadoSubasta(EstadoSubasta estadoSubasta);
}
