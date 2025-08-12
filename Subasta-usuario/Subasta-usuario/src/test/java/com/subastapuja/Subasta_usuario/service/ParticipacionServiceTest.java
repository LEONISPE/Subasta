package com.subastapuja.Subasta_usuario.service;

import com.subastapuja.Subasta_usuario.model.DTO.DTOParticipacionSubasta;
import com.subastapuja.Subasta_usuario.model.DTO.ValoracionParticipacion;
import com.subastapuja.Subasta_usuario.model.Participacion;
import com.subastapuja.Subasta_usuario.repository.ParticipacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import java.math.BigDecimal;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ParticipacionServiceTest {

    @Mock
    private ParticipacionRepository participacionRepository;

    @InjectMocks
    private ParticipacionService participacionService;

    private DTOParticipacionSubasta dto;

    @BeforeEach
    void setUp() {
        dto = new DTOParticipacionSubasta();
        dto.setMejorPostor("juan perez");
        dto.setPrecioActual(BigDecimal.valueOf(1200.00));
    }

    @Test
    void guardarParticipacion_deberiaGuardarConValoracionCorrecta() {
        // Act
        participacionService.guardarParticipacionSubasta(dto);

        // Assert
        ArgumentCaptor<Participacion> captor = ArgumentCaptor.forClass(Participacion.class);
        verify(participacionRepository, times(1)).save(captor.capture());

        Participacion guardada = captor.getValue();

        assertEquals("juan perez", guardada.getMejorPostor());
        assertEquals(BigDecimal.valueOf(1200.00), guardada.getPrecioActual());
        assertEquals(
                ValoracionParticipacion.C3,
                guardada.getValoracionParticipacion()
        );
    }
}