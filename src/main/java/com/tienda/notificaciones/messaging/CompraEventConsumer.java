package com.tienda.notificaciones.messaging;

import com.tienda.notificaciones.dto.CompraConfirmadaEvent;
import com.tienda.notificaciones.model.Notificacion;
import com.tienda.notificaciones.service.NotificacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CompraEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(CompraEventConsumer.class);

    private final NotificacionService notificacionService;

    @Autowired
    public CompraEventConsumer(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    // Este metodo se ejecuta solo cada vez que llega un mensaje al topic
    @KafkaListener(topics = "compra-confirmada", groupId = "notificaciones-group")
    public void recibirCompra(CompraConfirmadaEvent evento) {
        log.info("Evento recibido de Kafka: compra del usuario {} por {}",
                evento.getUsuarioId(), evento.getTotal());

        String mensaje = "Tu compra de " + evento.getCantidadItems()
                + " producto(s) por un total de $" + evento.getTotal() + " fue confirmada.";

        try {
            notificacionService.enviar(
                    new Notificacion(evento.getUsuarioId(), "CONFIRMACION_COMPRA", mensaje));
        } catch (Exception ex) {
            // Si el usuario no existe no queremos que Kafka reintente para siempre
            log.error("No se pudo notificar al usuario {}: {}", evento.getUsuarioId(), ex.getMessage());
        }
    }
}
