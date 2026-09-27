package com.devsenior.atiende.dto;

import com.devsenior.atiende.model.CanalTicket;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CrearTicketRequest {

    @NotBlank(message = "El campo clienteNombre es obligatorio")
    @Size(max = 100, message = "El campo clienteNombre no puede superar los 100 caracteres")
    private String clienteNombre;

    @NotBlank(message = "El campo clienteContacto es obligatorio")
    @Size(max = 120, message = "El campo clienteContacto no puede superar los 120 caracteres")
    private String clienteContacto;

    @NotNull(message = "El campo canal es obligatorio")
    private CanalTicket canal;

    @NotBlank(message = "El campo mensaje es obligatorio")
    @Size(max = 2000, message = "El campo mensaje no puede superar los 2000 caracteres")
    private String mensaje;

    public CrearTicketRequest() {
    }

    public CrearTicketRequest(String clienteNombre, String clienteContacto, CanalTicket canal, String mensaje) {
        this.clienteNombre = clienteNombre;
        this.clienteContacto = clienteContacto;
        this.canal = canal;
        this.mensaje = mensaje;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteContacto() {
        return clienteContacto;
    }

    public void setClienteContacto(String clienteContacto) {
        this.clienteContacto = clienteContacto;
    }

    public CanalTicket getCanal() {
        return canal;
    }

    public void setCanal(CanalTicket canal) {
        this.canal = canal;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
