package com.subastapuja.repository;

import com.subastapuja.model.Puja;
import com.subastapuja.model.SubastaFavorita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubastaFavoritaRepository extends JpaRepository<SubastaFavorita, Long> {
    @Query("SELECT sf.puja FROM SubastaFavorita sf JOIN FETCH sf.puja WHERE sf.nombreUsuario = :nombreUsuario")
    List<Puja> findPujasFavoritasByNombreUsuario(@Param("nombreUsuario") String nombreUsuario);
}
