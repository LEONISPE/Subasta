package com.Subasta_Online.Subasta_service.Service;

import com.Subasta_Online.Subasta_service.Config.WebClientConfig;
import com.Subasta_Online.Subasta_service.KafkaProducerConfig.SubastaProducer;
import com.Subasta_Online.Subasta_service.Model.DTOiniciarSubasta;
import com.Subasta_Online.Subasta_service.Model.DTOmostarProducto;
import com.Subasta_Online.Subasta_service.Model.DTOpujaID;
import com.Subasta_Online.Subasta_service.Model.IniciarSubasta;
import com.Subasta_Online.Subasta_service.Repository.SubastaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SubastaService {

    private final SubastaRepository subastaRepository;
    private final SubastaProducer subastaProducer;
    private final WebClient.Builder webClientBuilder;

    public DTOiniciarSubasta iniciarSubasta(DTOiniciarSubasta dto) {
        String productoId = dto.getIdProducto();

        System.out.println("ID recibido: " + productoId);

        // 🟩 Validar existencia del producto
        Boolean existe = webClientBuilder.build()
                .get()
                .uri("lb://SUBASTA-PRODUCTO/producto/mostrar/{id}", productoId)
                .exchangeToMono(response -> {
                    System.out.println("Código de respuesta: " + response.statusCode());

                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(DTOmostarProducto.class)
                                .doOnNext(prod -> System.out.println("Producto encontrado: " + prod.getNombre()))
                                .map(producto -> true);
                    } else {
                        return Mono.just(false);
                    }
                })
                .block();

        if (!existe) {
            throw new IllegalArgumentException("El producto con ID " + productoId + " no existe.");
        }
        Boolean yaEnSubasta = webClientBuilder.build()
                .post()
                .uri("lb://Subasta-puja/api/pujas/verificar-subasta-activa")
                .bodyValue(dto.getIdProducto()) // Solo el ID del producto
                .retrieve()
                .bodyToMono(Boolean.class)
                .block(); // <-- Esto es necesario

        if (Boolean.TRUE.equals(yaEnSubasta)) {
            throw new IllegalArgumentException("El producto ya se encuentra en una subasta activa.");
        }

        // 2. Crear la subasta si el producto existe
        IniciarSubasta iniciarSubasta = new IniciarSubasta();
        iniciarSubasta.setNombre(dto.getNombre());
        iniciarSubasta.setCategoria(dto.getCategoria());
        iniciarSubasta.setDescripcion(dto.getDescripcion());
        iniciarSubasta.setPrecioInicial(dto.getPrecioInicial());
        iniciarSubasta.setEstadoProducto(dto.getEstadoProducto());
        iniciarSubasta.setDuracionSubasta(dto.getDuracionSubasta());

        LocalDateTime ahora = LocalDateTime.now();
        iniciarSubasta.setHoraInicio(ahora);
        dto.setHoraInicio(ahora);

        subastaRepository.save(iniciarSubasta);
        subastaProducer.sendMessaguePuja(dto);

        return dto;
    }




}
