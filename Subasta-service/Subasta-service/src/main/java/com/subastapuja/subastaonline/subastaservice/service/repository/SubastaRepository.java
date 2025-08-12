package com.subastapuja.subastaonline.subastaservice.service.repository;

import com.subastapuja.subastaonline.subastaservice.service.model.IniciarSubasta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubastaRepository  extends JpaRepository<IniciarSubasta,Long> {

    Optional<IniciarSubasta> findById(Long id);
}
