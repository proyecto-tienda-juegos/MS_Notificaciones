package com.tienda.notificaciones.service;

import com.tienda.notificaciones.client.UsuarioClient;
import com.tienda.notificaciones.dto.UsuarioDTO;
import com.tienda.notificaciones.model.Notificacion;
import com.tienda.notificaciones.repository.NotificacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionService.class);

    private final NotificacionRepository notificacionRepository;
    private final UsuarioClient usuarioClient;

    @Autowired
    public NotificacionService(NotificacionRepository notificacionRepository, UsuarioClient usuarioClient) {
        this.notificacionRepository = notificacionRepository;
        this.usuarioClient = usuarioClient;
    }

    public Notificacion enviar(Notificacion notificacion) {
        // 1. Buscar los datos de contacto del usuario (llamada a usuario-service)
        UsuarioDTO usuario = usuarioClient.buscarPorId(notificacion.getUsuarioId());
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No existe el usuario con id " + notificacion.getUsuarioId());
        }

        // 2. "Enviar" el mensaje. En un proyecto real aqui se integraria un
        //    servicio de correo (ej. spring-boot-starter-mail). Para esta
        //    entrega lo simulamos con un log, que es suficiente para
        //    demostrar el flujo completo.
        log.info("Enviando notificacion [{}] a {} ({}): {}",
                notificacion.getTipo(), usuario.getNombre(), usuario.getEmail(), notificacion.getMensaje());

        notificacion.setEmailDestino(usuario.getEmail());
        notificacion.setFechaEnvio(LocalDateTime.now());
        notificacion.setEstado("ENVIADA");

        return notificacionRepository.save(notificacion);
    }

    public List<Notificacion> listarPorUsuario(Long usuarioId) {
        return notificacionRepository.findByUsuarioId(usuarioId);
    }
}
