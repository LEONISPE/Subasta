package com.subastapuja.subastaonline.subastaservice.service.service;

import com.subastapuja.subastaonline.subastaservice.service.excepcions.PeticionInvalidoException;
import com.subastapuja.subastaonline.subastaservice.service.kafkaproducerconfig.SubastaProducer;
import com.subastapuja.subastaonline.subastaservice.service.model.*;
import com.subastapuja.subastaonline.subastaservice.service.repository.SubastaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;




@Service
@AllArgsConstructor
public class SubastaServiceImpl implements SubastaService {

    private final SubastaRepository subastaRepository;
    private final SubastaProducer subastaProducer;
    private final WebClient.Builder webClientBuilder;
    private static final String BEARER_PREFIX = "Bearer ";
    private static final Logger logger = LoggerFactory.getLogger(SubastaServiceImpl.class);


    @Override
    public DTOiniciarSubasta iniciarSubasta(DTOiniciarSubasta dto) {
        String productoId = dto.getIdProducto();
        logger.info("ID recibido: {}", productoId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = ((JwtAuthenticationToken) authentication).getToken().getTokenValue();

        // 🟩 Validar existencia del producto
        boolean existe = webClientBuilder.build()
                .get()
                .uri("lb://SUBASTA-PRODUCTO/producto/mostrar/{id}", productoId)
                .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + jwtToken)
                .exchangeToMono(response -> {
                    logger.info("Respuesta del servicio: {}", response.statusCode());


                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(DTOmostarProducto.class)
                                .doOnNext(productos -> logger.info("Producto recibido: {}", productos.getNombre()))
                                .map(producto -> true);
                    } else {
                        return Mono.just(false);
                    }
                })
                .block();

        if (!existe) {
            throw new PeticionInvalidoException("El producto con ID " + productoId + " no existe.");
        }

        // 🟨 Verificar si ya está en subasta activa
        Boolean yaEnSubasta = webClientBuilder.build()
                .post()
                .uri("lb://Subasta-puja/api/pujas/verificar-subasta-activa")
                .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + jwtToken)
                .bodyValue(dto.getIdProducto())
                .retrieve()
                .onStatus(
                        status -> status.value() == 401,
                        clientResponse -> {
                            logger.info("⚠️ Respuesta 401 desde Subasta-puja");
                            return Mono.error(new PeticionInvalidoException("No autorizado para verificar subasta activa"));
                        }
                )
                .bodyToMono(Boolean.class)
                .onErrorResume(ex -> {
                   logger.info("❌ Error al consultar subasta activa: {}", ex.getMessage());
                    return Mono.error(new PeticionInvalidoException("el producto ya está en subasta"));
                })
                .block();

        if (Boolean.TRUE.equals(yaEnSubasta)) {
            throw new PeticionInvalidoException("El producto ya se encuentra en una subasta activa.");
        }

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

    @Override
    public DTOFuturasSubastas programarFuturasSubastas(DTOFuturasSubastas dto) {
        String productoId = dto.getIdProducto();
        logger.info("ID recibido: {}", productoId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = ((JwtAuthenticationToken) authentication).getToken().getTokenValue();

        // 🟩 Validar existencia del producto
        Boolean existe = webClientBuilder.build()
                .get()
                .uri("lb://SUBASTA-PRODUCTO/producto/mostrar/{id}", productoId)
                .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + jwtToken)
                .exchangeToMono(response -> {
                    logger.info("Respuesta del servicio: {}", response.statusCode());

                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(DTOmostarProducto.class)
                                .doOnNext(producto -> logger.info("Producto encontrado:{} ", producto.getNombre()))
                                .map(producto -> true);
                    } else {
                        return Mono.just(false);
                    }
                })
                .block();

        if (Boolean.FALSE.equals(existe)) {
            throw new PeticionInvalidoException("El producto con ID " + productoId + " no existe.");
        }

        // 🟨 Verificar si ya está en subasta activa
        Boolean yaEnSubasta = webClientBuilder.build()
                .post()
                .uri("lb://Subasta-puja/api/pujas/verificar-subasta-activa")
                .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + jwtToken)
                .bodyValue(dto.getIdProducto())
                .retrieve()
                .onStatus(
                        status -> status.value() == 401,
                        clientResponse -> {
                            logger.info("Respuesta 401 desde Subasta-puja");
                            return Mono.error(new PeticionInvalidoException("No autorizado para verificar subasta activa"));
                        }
                )
                .bodyToMono(Boolean.class)
                .onErrorResume(ex -> {
                    logger.info("❌ Error al consultar subasta activa:{}", ex.getMessage());
                    return Mono.error(new PeticionInvalidoException("el producto ya está en subasta"));
                })
                .block();

        if (Boolean.TRUE.equals(yaEnSubasta)) {
            throw new PeticionInvalidoException("El producto ya se encuentra en una subasta activa.");
        }

        LocalDateTime fechaMinima = LocalDate.now().plusDays(1).atStartOfDay(); // mañana a las 00:00

        if (dto.getFechaFuturaInicio().isBefore(fechaMinima)) {
            throw new PeticionInvalidoException("Debes programar la subasta mínimo con 1 día de anticipación");
        }

        IniciarSubasta iniciarSubasta = new IniciarSubasta();
        iniciarSubasta.setNombre(dto.getNombre());
        iniciarSubasta.setCategoria(dto.getCategoria());
        iniciarSubasta.setDescripcion(dto.getDescripcion());
        iniciarSubasta.setPrecioInicial(dto.getPrecioInicial());
        iniciarSubasta.setEstadoProducto(dto.getEstadoProducto());
        iniciarSubasta.setDuracionSubasta(dto.getDuracionSubasta());
        iniciarSubasta.setFechaFuturaInicio(dto.getFechaFuturaInicio());

        subastaRepository.save(iniciarSubasta);
        subastaProducer.sendMensajePujaFuturasPujas(dto);


        List<String> usuarios = obtenerUsuariosDesdeKeycloak();

        for (String usuario : usuarios) {
            NotificacionSubastaProgramadaDTO notificacion = new NotificacionSubastaProgramadaDTO();
            notificacion.setDestinatario(usuario);
            notificacion.setIdProducto(productoId);
            notificacion.setMensaje("Se ha programado una nueva subasta: " + dto.getNombre());
            notificacion.setFechaFuturaInicio(dto.getFechaFuturaInicio());

            subastaProducer.sendMensajeNotificacionFuturasSubastas(notificacion);
        }

        return dto;
    }

    @Override
    public DTOMostarSubastas getSubastaPorId(Long id) {
        return subastaRepository.findById(id)
                .map(iniciarSubasta -> new DTOMostarSubastas(
                        iniciarSubasta.getId(),
                        iniciarSubasta.getIdProducto(),
                        iniciarSubasta.getNombre(),
                        iniciarSubasta.getCategoria(),
                        iniciarSubasta.getDescripcion(),
                        iniciarSubasta.getPrecioInicial(),
                        iniciarSubasta.getHoraInicio(),
                        iniciarSubasta.getEstadoProducto(),
                        iniciarSubasta.getDuracionSubasta(),
                        iniciarSubasta.getNombreUsuario()
                ))
                .orElseThrow(() -> new PeticionInvalidoException("Producto con ID " + id + " no encontrado"));
    }


    // Método para obtener el token de admin
    public String obtenerAdminToken() {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8181")
                .build();

        String response = webClient.post()
                .uri("/realms/master/protocol/openid-connect/token")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters
                        .fromFormData("grant_type", "password")
                        .with("client_id", "admin-cli") // cliente por defecto
                        .with("username", "admin")      // tu usuario Keycloak
                        .with("password", "admin"))     // tu contraseña
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response);
            return jsonNode.get("access_token").asText();
        } catch (Exception e) {
            throw new PeticionInvalidoException("Error al obtener el token de Keycloak", e);
        }
    }

    // Método para obtener los usuarios desde Keycloak
    public List<String> obtenerUsuariosDesdeKeycloak() {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8181")
                .defaultHeader(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + obtenerAdminToken())
                .build();

        List<Map<String, Object>> response = webClient.get()
                .uri("/admin/realms/subasta-realm/users")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {
                })
                .block();

        Objects.requireNonNull(response, "La respuesta del WebClient fue null");

        return response.stream()
                .map(user -> (String) user.get("username"))
                .toList();
    }
}


