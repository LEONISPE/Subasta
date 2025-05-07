package com.Subasta_Online.Subasta_puja.Repository;

import com.Subasta_Online.Subasta_puja.Model.Puja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PujaRepository  extends JpaRepository<Puja, Long> {

    List<Puja> findByPujaName(String pujaName);

    List<Puja> findByPrecioInicial(float precioInicial);

    List<Puja> findByPrecioFinalandName(float precioFinal, String pujaName);
}
