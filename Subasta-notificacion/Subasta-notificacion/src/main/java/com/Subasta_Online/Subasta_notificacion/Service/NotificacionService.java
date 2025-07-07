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
        Notificacion notificacion = new Notificacion();
        notificacion.setDestinatario(dto.getDestinatario());
        notificacion.setMensaje(dto.getMensaje());
        notificacion.setIdProducto(dto.getIdProducto());
        notificacion.setFecha(LocalDateTime.now());
        notificacionRepository.save(notificacion);
    }

    public List<NotificacionDTO> obtenerNotificacionesUsuario(String usuario) {
        return notificacionRepository.findByDestinatarioOrderByFechaDesc(usuario)
                .stream()
                .map(notificacion -> {
                    NotificacionDTO dto = new NotificacionDTO();
                    dto.setDestinatario(notificacion.getDestinatario());
                    dto.setMensaje(notificacion.getMensaje());
                    dto.setIdProducto(notificacion.getIdProducto());
                    dto.setFecha(notificacion.getFecha());
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
                .map(notificacion -> {
                    NotificacionSubastaProgramadaDTO dto = new NotificacionSubastaProgramadaDTO();
                    dto.setDestinatario(notificacion.getDestinatario());
                    dto.setIdProducto(notificacion.getIdProducto());
                    dto.setMensaje(notificacion.getMensaje());
                    dto.setFechaFuturaInicio(notificacion.getFechaFuturaInicio());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}


