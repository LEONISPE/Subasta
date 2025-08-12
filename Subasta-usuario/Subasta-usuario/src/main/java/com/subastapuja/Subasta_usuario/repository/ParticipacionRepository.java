package com.subastapuja.Subasta_usuario.repository;

import com.subastapuja.Subasta_usuario.model.Participacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipacionRepository extends JpaRepository<Participacion, Long> {
}
