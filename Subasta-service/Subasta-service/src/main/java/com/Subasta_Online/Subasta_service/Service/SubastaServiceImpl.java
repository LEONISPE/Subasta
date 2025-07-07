package com.Subasta_Online.Subasta_service.Service;

import com.Subasta_Online.Subasta_service.Excepcions.PeticionInvalidoException;
import com.Subasta_Online.Subasta_service.KafkaProducerConfig.SubastaProducer;
import com.Subasta_Online.Subasta_service.Model.*;
import com.Subasta_Online.Subasta_service.Repository.SubastaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubastaServiceImpl implements SubastaService {

    private final SubastaRepository subastaRepository;
    private final SubastaProducer subastaProducer;
    private final WebClient.Builder webClientBuilder;

    @Override
    public DTOiniciarSubasta iniciarSubasta(DTOiniciarSubasta dto) {
        String productoId = dto.getIdProducto();
        System.out.println("ID recibido: " + productoId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = ((JwtAuthenticationToken) authentication).getToken().getTokenValue();

        // 🟩 Validar existencia del producto
        Boolean existe = webClientBuilder.build()
                .get()
                .uri("lb://SUBASTA-PRODUCTO/producto/mostrar/{id}", productoId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
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
            throw new PeticionInvalidoException("El producto con ID " + productoId + " no existe.");
        }

        // 🟨 Verificar si ya está en subasta activa
        Boolean yaEnSubasta = webClientBuilder.build()
                .post()
                .uri("lb://Subasta-puja/api/pujas/verificar-subasta-activa")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .bodyValue(dto.getIdProducto())
                .retrieve()
                .onStatus(
                        status -> status.value() == 401,
                        clientResponse -> {
                            System.out.println("⚠️ Respuesta 401 desde Subasta-puja");
                            return Mono.error(new PeticionInvalidoException("No autorizado para verificar subasta activa"));
                        }
                )
                .bodyToMono(Boolean.class)
                .onErrorResume(ex -> {
                    System.out.println("❌ Error al consultar subasta activa: " + ex.getMessage());
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
        System.out.println("ID recibido: " + productoId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = ((JwtAuthenticationToken) authentication).getToken().getTokenValue();

        // 🟩 Validar existencia del producto
        Boolean existe = webClientBuilder.build()
                .get()
                .uri("lb://SUBASTA-PRODUCTO/producto/mostrar/{id}", productoId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
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
            throw new PeticionInvalidoException("El producto con ID " + productoId + " no existe.");
        }

        // 🟨 Verificar si ya está en subasta activa
        Boolean yaEnSubasta = webClientBuilder.build()
                .post()
                .uri("lb://Subasta-puja/api/pujas/verificar-subasta-activa")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .bodyValue(dto.getIdProducto())
                .retrieve()
                .onStatus(
                        status -> status.value() == 401,
                        clientResponse -> {
                            System.out.println("⚠️ Respuesta 401 desde Subasta-puja");
                            return Mono.error(new PeticionInvalidoException("No autorizado para verificar subasta activa"));
                        }
                )
                .bodyToMono(Boolean.class)
                .onErrorResume(ex -> {
                    System.out.println("❌ Error al consultar subasta activa: " + ex.getMessage());
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
            throw new RuntimeException("Error al obtener el token de Keycloak", e);
        }
    }

    // Método para obtener los usuarios desde Keycloak
    public List<String> obtenerUsuariosDesdeKeycloak() {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8181")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + obtenerAdminToken())
                .build();

        List<Map<String, Object>> response = webClient.get()
                .uri("/admin/realms/subasta-realm/users")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                .block();

        return response.stream()
                .map(user -> (String) user.get("username"))
                .collect(Collectors.toList());
    }
}



