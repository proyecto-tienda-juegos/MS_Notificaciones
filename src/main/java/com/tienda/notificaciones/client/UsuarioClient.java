package com.tienda.notificaciones.client;

import com.tienda.notificaciones.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class UsuarioClient {

    private final RestTemplate restTemplate;

    private static final String URL_BASE = "http://usuario-service/api/usuarios";

    @Autowired
    public UsuarioClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UsuarioDTO buscarPorId(Long usuarioId) {
        try {
            return restTemplate.getForObject(URL_BASE + "/" + usuarioId, UsuarioDTO.class);
        } catch (HttpClientErrorException.NotFound ex) {
            return null;
        }
    }
}
