package com.Subasta_Online.Subasta_puja.Repository;

import com.Subasta_Online.Subasta_puja.Model.Categoria;
import com.Subasta_Online.Subasta_puja.Model.EstadoSubasta;
import com.Subasta_Online.Subasta_puja.Model.Puja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PujaRepository  extends JpaRepository<Puja, Long> {

    List<Puja> findByNombre(String nombre);

    List<Puja> findByPrecioInicial(BigDecimal precioInicial);

    List<Puja> findByCategoria(Categoria categoria);

    Optional<Puja> findByIdProducto(String idProducto);
    List<Puja> findByEstadoSubasta(EstadoSubasta estado);


}
