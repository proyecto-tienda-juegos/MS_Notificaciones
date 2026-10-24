package com.tienda.notificaciones.dto;

// Misma estructura que el evento que publica Carrito. Lo que importa para
// que Kafka lo entienda son los nombres de los campos del JSON, no el paquete.
public class CompraConfirmadaEvent {

    private Long usuarioId;
    private Double total;
    private Integer cantidadItems;

    public CompraConfirmadaEvent() {
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getCantidadItems() {
        return cantidadItems;
    }

    public void setCantidadItems(Integer cantidadItems) {
        this.cantidadItems = cantidadItems;
    }
}
