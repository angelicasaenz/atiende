package com.devsenior.atiende.service.impl;

import com.devsenior.atiende.dto.ActualizarEstadoRequest;
import com.devsenior.atiende.dto.CrearTicketRequest;
import com.devsenior.atiende.dto.TicketResponse;
import com.devsenior.atiende.exception.TicketNoEncontradoException;
import com.devsenior.atiende.exception.TransicionEstadoInvalidaException;
import com.devsenior.atiende.model.EstadoTicket;
import com.devsenior.atiende.model.PrioridadTicket;
import com.devsenior.atiende.model.Ticket;
import com.devsenior.atiende.repository.TicketRepository;
import com.devsenior.atiende.service.TicketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public TicketResponse crearTicket(CrearTicketRequest request) {
        Ticket ticket = new Ticket();
        ticket.setClienteNombre(request.getClienteNombre().trim());
        ticket.setClienteContacto(request.getClienteContacto().trim());
        ticket.setCanal(request.getCanal());
        ticket.setMensaje(request.getMensaje().trim());

        // Regla 1: Todo ticket nuevo nace ABIERTO y con prioridad MEDIA.
        ticket.setEstado(EstadoTicket.ABIERTO);
        ticket.setPrioridad(PrioridadTicket.MEDIA);

        LocalDateTime now = LocalDateTime.now();
        ticket.setFechaCreacion(now);
        ticket.setFechaActualizacion(now);

        Ticket guardado = ticketRepository.saveAndFlush(ticket);
        return TicketResponse.fromEntity(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponse> listarTickets(EstadoTicket estado) {
        List<Ticket> tickets;
        if (estado != null) {
            tickets = ticketRepository.findByEstadoOrderByFechaCreacionDesc(estado);
        } else {
            tickets = ticketRepository.findAllByOrderByFechaCreacionDesc();
        }

        return tickets.stream()
                .map(TicketResponse::fromEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TicketResponse obtenerTicketPorId(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNoEncontradoException(id));
        return TicketResponse.fromEntity(ticket);
    }

    @Override
    public TicketResponse cambiarEstado(Long id, ActualizarEstadoRequest request) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNoEncontradoException(id));

        EstadoTicket estadoActual = ticket.getEstado();
        EstadoTicket nuevoEstado = request.getEstado();

        // Reglas 2 y 3: Validar transición permitida
        if (!estadoActual.puedeCambiarA(nuevoEstado)) {
            throw new TransicionEstadoInvalidaException(estadoActual, nuevoEstado);
        }

        ticket.setEstado(nuevoEstado);
        ticket.setFechaActualizacion(LocalDateTime.now());

        Ticket actualizado = ticketRepository.saveAndFlush(ticket);
        return TicketResponse.fromEntity(actualizado);
    }
}
