package com.subastapuja.subasta.producto.service;

import com.subastapuja.subasta.producto.config.ConsumerProducto;
import com.subastapuja.subasta.producto.excepcions.ProductoInvalidoException;
import com.subastapuja.subasta.producto.model.*;
import com.subastapuja.subasta.producto.repository.ComentariosRepository;
import com.subastapuja.subasta.producto.repository.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductoServiceImpl  implements ProductoService{

    private final ProductoRepository productoRepository;
    private final ComentariosRepository comentariosRepository;
    private final ConsumerProducto consumerProducto;

@Override
    public DTOaddProducto addProducto(DTOaddProducto dtOaddProducto) {
    if (dtOaddProducto.getNombre().contains("xxx")) {
        throw new ProductoInvalidoException("Nombre de producto no permitido");
    }
        Producto productonew = new Producto();
        productonew.setNombre(dtOaddProducto.getNombre());
        productonew.setDescripcion(dtOaddProducto.getDescripcion());
        productonew.setEstadoProducto(dtOaddProducto.getEstadoProducto());
        productonew.setCategoria(dtOaddProducto.getCategoria());
        productoRepository.save(productonew);
        return dtOaddProducto;


    }

    @Override
    public List<DTOmostrarProducto> getAllProductos() {
        return productoRepository.findAll()
                .stream()
                .map(producto -> new DTOmostrarProducto(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getDescripcion(),
                        producto.getEstadoProducto(),
                        producto.getCategoria()
                )).toList();


    }

    @Override
    public DTOmostrarProducto getProductoPorId(String id) {
        return productoRepository.findById(id)
                .map(producto -> new DTOmostrarProducto(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getDescripcion(),
                        producto.getEstadoProducto(),
                        producto.getCategoria()
                ))
                .orElseThrow(() -> new ProductoInvalidoException("Producto con ID " + id + " no encontrado"));
    }

    @Override
    public DTOComentarios guardarcomentariosdesdePuja(DTOComentarios dtoComentarios) {
    DTOComentarios dto = new DTOComentarios();
    dto.setProductoId(dtoComentarios.getProductoId());
    dto.setMensaje(dtoComentarios.getMensaje());
    dto.setUsuarioId(dtoComentarios.getUsuarioId());
    dto.setRespuesta(dtoComentarios.getRespuesta());
    dto.setFechaCreacion(dtoComentarios.getFechaCreacion());

    Comentarios comentarios = new Comentarios();
    comentarios.setProductoId(dtoComentarios.getProductoId());
    comentarios.setMensaje(dtoComentarios.getMensaje());
    comentarios.setUsuarioId(dtoComentarios.getUsuarioId());
    comentarios.setRespuesta(dtoComentarios.getRespuesta());
    comentarios.setFechaCreacion(dtoComentarios.getFechaCreacion());

    comentariosRepository.save(comentarios);
    consumerProducto.consumirComentarios(dtoComentarios);

    return dto;
    }

    @Override
    public DTOComentarios getComentariosPorId(String id) {
     return comentariosRepository.findById(id)
             .map(comentarios -> new DTOComentarios(
                     comentarios.getProductoId(),
                     comentarios.getUsuarioId(),
                     comentarios.getMensaje(),
                     comentarios.getRespuesta(),
                     comentarios.getFechaCreacion()
             ))
             .orElseThrow(() -> new ProductoInvalidoException("comentario con ID " + id + " no encontrado"));
    }

    }

