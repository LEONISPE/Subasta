package com.Subasta_Online.Subasta_service.Test;
import com.Subasta_Online.Subasta_service.Controller.SubastaServiceController;
import com.Subasta_Online.Subasta_service.Model.DTOFuturasSubastas;
import com.Subasta_Online.Subasta_service.Model.DTOiniciarSubasta;
import com.Subasta_Online.Subasta_service.Service.SubastaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;


import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Controller {

    @Mock
    private SubastaServiceImpl subastaService;

    @Mock
    private Jwt jwt;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private SubastaServiceController controller;

    @Test
    void iniciarSubasta_DebeRetornarOkYAsignarUsuario() {
        // Arrange
        DTOiniciarSubasta dto = new DTOiniciarSubasta();
        dto.setIdProducto("123");

        DTOiniciarSubasta respuestaEsperada = new DTOiniciarSubasta();
        respuestaEsperada.setNombreUsuario("testuser");

        when(jwt.getClaimAsString("preferred_username")).thenReturn("testuser");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(jwt);
        SecurityContextHolder.setContext(securityContext);

        when(subastaService.iniciarSubasta(any(DTOiniciarSubasta.class))).thenReturn(respuestaEsperada);

        // Act
        ResponseEntity<DTOiniciarSubasta> respuesta = controller.iniciarSubasta(dto, jwt);

        // Assert
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("testuser", respuesta.getBody().getNombreUsuario());
        verify(subastaService, times(1)).iniciarSubasta(any(DTOiniciarSubasta.class));
    }

    @Test
    void getUsuarios_DebeRetornarListaUsuarios() {
        // Arrange
        List<String> usuariosMock = List.of("usuario1", "usuario2");
        when(subastaService.obtenerUsuariosDesdeKeycloak()).thenReturn(usuariosMock);

        // Act
        ResponseEntity<List<String>> respuesta = controller.getUsuarios();

        // Assert
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals(2, respuesta.getBody().size());
        assertEquals("usuario1", respuesta.getBody().get(0));
        verify(subastaService, times(1)).obtenerUsuariosDesdeKeycloak();
    }

    @Test
    void iniciarSubastasFuturas_DebeRetornarOkYAsignarUsuario() {
        // Arrange
        DTOFuturasSubastas dto = new DTOFuturasSubastas();
        dto.setIdProducto("456");
        dto.setFechaFuturaInicio(LocalDateTime.now().plusDays(2));

        DTOFuturasSubastas respuestaEsperada = new DTOFuturasSubastas();
        respuestaEsperada.setNombreUsuario("testuser");

        when(jwt.getClaimAsString("name")).thenReturn("testuser");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(jwt);
        SecurityContextHolder.setContext(securityContext);

        when(subastaService.programarFuturasSubastas(any(DTOFuturasSubastas.class))).thenReturn(respuestaEsperada);

        // Act
        ResponseEntity<DTOFuturasSubastas> respuesta = controller.iniciarSubastasFuturas(dto, jwt);

        // Assert
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("testuser", respuesta.getBody().getNombreUsuario());
        verify(subastaService, times(1)).programarFuturasSubastas(any(DTOFuturasSubastas.class));
    }

    @Test
    void iniciarSubastasFuturas_SinJwt_DebeUsarNombreUsuarioPorDefecto() {
        // Arrange
        DTOFuturasSubastas dto = new DTOFuturasSubastas();
        dto.setIdProducto("456");
        dto.setFechaFuturaInicio(LocalDateTime.now().plusDays(2));
        dto.setNombreUsuario("usuario-default");

        DTOFuturasSubastas respuestaEsperada = new DTOFuturasSubastas();
        respuestaEsperada.setNombreUsuario("usuario-default");

        when(subastaService.programarFuturasSubastas(any(DTOFuturasSubastas.class))).thenReturn(respuestaEsperada);

        // Act - Llamamos sin pasar el Jwt
        ResponseEntity<DTOFuturasSubastas> respuesta = controller.iniciarSubastasFuturas(dto, null);

        // Assert
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("usuario-default", respuesta.getBody().getNombreUsuario());
        verify(subastaService, times(1)).programarFuturasSubastas(any(DTOFuturasSubastas.class));
    }
}





