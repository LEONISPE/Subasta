package com.Subasta_Online.Subasta_notificacion.Service;

import com.Subasta_Online.Subasta_notificacion.Model.Notificacion;
import com.Subasta_Online.Subasta_notificacion.Model.NotificacionDTO;
import com.Subasta_Online.Subasta_notificacion.Model.NotificacionSubastaProgramadaDTO;
import com.Subasta_Online.Subasta_notificacion.Repository.NotificacionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    public void guardarNotificacion(NotificacionDTO dto) {
        Notificacion n = new Notificacion();
        n.setDestinatario(dto.getDestinatario());
        n.setMensaje(dto.getMensaje());
        n.setIdProducto(dto.getIdProducto());
        n.setFecha(LocalDateTime.now());
        notificacionRepository.save(n);
    }

    public List<NotificacionDTO> obtenerNotificacionesUsuario(String usuario) {
        return notificacionRepository.findByDestinatarioOrderByFechaDesc(usuario)
                .stream()
                .map(n -> {
                    NotificacionDTO dto = new NotificacionDTO();
                    dto.setDestinatario(n.getDestinatario());
                    dto.setMensaje(n.getMensaje());
                    dto.setIdProducto(n.getIdProducto());
                    dto.setFecha(n.getFecha());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public void guardarNotificacionSubataFuturas(NotificacionSubastaProgramadaDTO notificacionSubastaProgramadaDTO) {
        Notificacion n = new Notificacion();
        n.setDestinatario(notificacionSubastaProgramadaDTO.getDestinatario());
        n.setIdProducto(notificacionSubastaProgramadaDTO.getIdProducto());
        n.setMensaje(notificacionSubastaProgramadaDTO.getMensaje());
        n.setFechaFuturaInicio(notificacionSubastaProgramadaDTO.getFechaFuturaInicio());
        notificacionRepository.save(n);

    }
    public List<NotificacionSubastaProgramadaDTO> obtenerSubastasFuturasPorUsuario(String usuario) {
        return notificacionRepository.findByDestinatarioAndFechaFuturaInicioIsNotNullOrderByFechaFuturaInicioDesc(usuario)
                .stream()
                .map(n -> {
                    NotificacionSubastaProgramadaDTO dto = new NotificacionSubastaProgramadaDTO();
                    dto.setDestinatario(n.getDestinatario());
                    dto.setIdProducto(n.getIdProducto());
                    dto.setMensaje(n.getMensaje());
                    dto.setFechaFuturaInicio(n.getFechaFuturaInicio());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}


