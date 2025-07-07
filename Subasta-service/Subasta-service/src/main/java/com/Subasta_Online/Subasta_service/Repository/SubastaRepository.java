package com.Subasta_Online.Subasta_service.Repository;

import com.Subasta_Online.Subasta_service.Model.IniciarSubasta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubastaRepository  extends JpaRepository<IniciarSubasta,Long> {

    Optional<IniciarSubasta> findById(Long id);
}
