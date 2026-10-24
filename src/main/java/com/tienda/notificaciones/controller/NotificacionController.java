package com.tienda.notificaciones.controller;

import com.tienda.notificaciones.model.Notificacion;
import com.tienda.notificaciones.service.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    @Autowired
    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    // POST http://localhost:8085/api/notificaciones
    // Body (JSON): { "usuarioId": 1, "tipo": "CONFIRMACION_COMPRA", "mensaje": "Tu compra fue confirmada" }
    @PostMapping
    public ResponseEntity<Notificacion> enviar(@Valid @RequestBody Notificacion notificacion) {
        Notificacion enviada = notificacionService.enviar(notificacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(enviada);
    }

    // GET http://localhost:8085/api/notificaciones/usuario/1
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Notificacion>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.listarPorUsuario(usuarioId));
    }
}
