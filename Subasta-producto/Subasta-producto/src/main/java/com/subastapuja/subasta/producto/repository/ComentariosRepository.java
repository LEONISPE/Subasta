package com.subastapuja.subasta.producto.repository;

import com.subastapuja.subasta.producto.model.Comentarios;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ComentariosRepository extends MongoRepository<Comentarios, String> {
}
