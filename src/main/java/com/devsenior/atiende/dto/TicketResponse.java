package com.devsenior.atiende.dto;

import com.devsenior.atiende.model.CanalTicket;
import com.devsenior.atiende.model.EstadoTicket;
import com.devsenior.atiende.model.PrioridadTicket;
import com.devsenior.atiende.model.Ticket;

import java.time.LocalDateTime;

public class TicketResponse {

    private Long id;
    private String clienteNombre;
    private String clienteContacto;
    private CanalTicket canal;
    private String mensaje;
    private EstadoTicket estado;
    private PrioridadTicket prioridad;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public TicketResponse() {
    }

    public TicketResponse(Long id, String clienteNombre, String clienteContacto, CanalTicket canal,
                          String mensaje, EstadoTicket estado, PrioridadTicket prioridad,
                          LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.clienteNombre = clienteNombre;
        this.clienteContacto = clienteContacto;
        this.canal = canal;
        this.mensaje = mensaje;
        this.estado = estado;
        this.prioridad = prioridad;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public static TicketResponse fromEntity(Ticket ticket) {
        if (ticket == null) {
            return null;
        }
        return new TicketResponse(
                ticket.getId(),
                ticket.getClienteNombre(),
                ticket.getClienteContacto(),
                ticket.getCanal(),
                ticket.getMensaje(),
                ticket.getEstado(),
                ticket.getPrioridad(),
                ticket.getFechaCreacion(),
                ticket.getFechaActualizacion()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public EstadoTicket getEstado() {
        return estado;
    }

    public void setEstado(EstadoTicket estado) {
        this.estado = estado;
    }

    public PrioridadTicket getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(PrioridadTicket prioridad) {
        this.prioridad = prioridad;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
