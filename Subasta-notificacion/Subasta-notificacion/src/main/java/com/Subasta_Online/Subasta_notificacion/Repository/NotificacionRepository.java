package com.Subasta_Online.Subasta_notificacion.Repository;

import com.Subasta_Online.Subasta_notificacion.Model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByDestinatarioOrderByFechaDesc(String destinatario);
}

