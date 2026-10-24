package com.tienda.notificaciones.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El id de usuario es obligatorio")
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    // Ej: CONFIRMACION_COMPRA, PROMOCION, ALERTA_SEGURIDAD
    @NotBlank(message = "El tipo es obligatorio")
    @Column(nullable = false)
    private String tipo;

    @NotBlank(message = "El mensaje es obligatorio")
    @Column(nullable = false, length = 500)
    private String mensaje;

    // Guardamos el correo al que se envio, tal como vino de usuario-service
    // en el momento del envio (asi el historial no depende de que el usuario
    // siga existiendo despues)
    @Column(name = "email_destino")
    private String emailDestino;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @Column(nullable = false)
    private String estado; // ENVIADA o FALLIDA

    public Notificacion() {
    }

    public Notificacion(Long usuarioId, String tipo, String mensaje) {
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.mensaje = mensaje;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEmailDestino() {
        return emailDestino;
    }

    public void setEmailDestino(String emailDestino) {
        this.emailDestino = emailDestino;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
