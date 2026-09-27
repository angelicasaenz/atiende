package com.devsenior.atiende.dto;

import com.devsenior.atiende.model.EstadoTicket;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActualizarEstadoRequest {

    @NotNull(message = "El campo estado es obligatorio")
    private EstadoTicket estado;

    public ActualizarEstadoRequest() {
    }

    public ActualizarEstadoRequest(EstadoTicket estado) {
        this.estado = estado;
    }

    public EstadoTicket getEstado() {
        return estado;
    }

    public void setEstado(EstadoTicket estado) {
        this.estado = estado;
    }
}
