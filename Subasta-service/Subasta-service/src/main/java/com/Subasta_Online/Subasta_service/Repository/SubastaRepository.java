package com.Subasta_Online.Subasta_service.Repository;

import com.Subasta_Online.Subasta_service.Model.IniciarSubasta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubastaRepository  extends JpaRepository<IniciarSubasta,Long> {

}
