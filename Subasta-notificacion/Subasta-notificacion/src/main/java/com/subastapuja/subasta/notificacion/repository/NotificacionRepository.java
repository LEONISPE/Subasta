package com.subastapuja.subasta.notificacion.repository;

import com.subastapuja.subasta.notificacion.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByDestinatarioOrderByFechaDesc(String destinatario);

    List<Notificacion> findByDestinatarioAndFechaFuturaInicioIsNotNullOrderByFechaFuturaInicioDesc(String destinatario);
}

