package com.devsenior.atiende.service;

import com.devsenior.atiende.dto.ActualizarEstadoRequest;
import com.devsenior.atiende.dto.CrearTicketRequest;
import com.devsenior.atiende.dto.TicketResponse;
import com.devsenior.atiende.exception.TicketNoEncontradoException;
import com.devsenior.atiende.exception.TransicionEstadoInvalidaException;
import com.devsenior.atiende.model.CanalTicket;
import com.devsenior.atiende.model.EstadoTicket;
import com.devsenior.atiende.model.PrioridadTicket;
import com.devsenior.atiende.model.Ticket;
import com.devsenior.atiende.repository.TicketRepository;
import com.devsenior.atiende.service.impl.TicketServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Test
    void crearTicket_datosValidos_asignaEstadoAbiertoYPrioridadMedia() {
        // Arrange
        CrearTicketRequest request = new CrearTicketRequest(
                "Carlos Pérez",
                "carlos@example.com",
                CanalTicket.WHATSAPP,
                "Consulta sobre pedido"
        );

        when(ticketRepository.saveAndFlush(any(Ticket.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        TicketResponse response = ticketService.crearTicket(request);

        // Assert
        assertNotNull(response);
        assertEquals(EstadoTicket.ABIERTO, response.getEstado());
        assertEquals(PrioridadTicket.MEDIA, response.getPrioridad());

        ArgumentCaptor<Ticket> ticketCaptor = ArgumentCaptor.forClass(Ticket.class);
        verify(ticketRepository).saveAndFlush(ticketCaptor.capture());
        Ticket ticketPersistido = ticketCaptor.getValue();
        assertEquals(EstadoTicket.ABIERTO, ticketPersistido.getEstado());
        assertEquals(PrioridadTicket.MEDIA, ticketPersistido.getPrioridad());
    }

    @Test
    void obtenerTicketPorId_idInexistente_lanzaRecursoNoEncontradoException() {
        // Arrange
        Long idInexistente = 999L;
        when(ticketRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TicketNoEncontradoException.class, () -> ticketService.obtenerTicketPorId(idInexistente));
        verify(ticketRepository).findById(idInexistente);
    }

    @Test
    void cambiarEstado_transicionPermitida_cambiaElEstado() {
        // Arrange
        Long id = 1L;
        Ticket ticket = new Ticket("María López", "maria@correo.com", CanalTicket.CORREO, "Problema con entrega");
        ticket.setId(id);
        ticket.setEstado(EstadoTicket.ABIERTO);
        ticket.setPrioridad(PrioridadTicket.MEDIA);
        ticket.setFechaCreacion(LocalDateTime.now().minusHours(1));
        ticket.setFechaActualizacion(LocalDateTime.now().minusHours(1));

        ActualizarEstadoRequest request = new ActualizarEstadoRequest(EstadoTicket.EN_PROCESO);

        when(ticketRepository.findById(id)).thenReturn(Optional.of(ticket));
        when(ticketRepository.saveAndFlush(any(Ticket.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        TicketResponse response = ticketService.cambiarEstado(id, request);

        // Assert
        assertNotNull(response);
        assertEquals(EstadoTicket.EN_PROCESO, response.getEstado());
        verify(ticketRepository).findById(id);
        verify(ticketRepository).saveAndFlush(ticket);
    }

    @Test
    void cambiarEstado_transicionBloqueada_lanzaTransicionInvalidaExceptionYNuncaLlamaSaveAndFlush() {
        // Arrange
        Long id = 1L;
        Ticket ticket = new Ticket("María López", "maria@correo.com", CanalTicket.CORREO, "Problema con entrega");
        ticket.setId(id);
        ticket.setEstado(EstadoTicket.ABIERTO);
        ticket.setPrioridad(PrioridadTicket.MEDIA);

        // ABIERTO -> RESUELTO no está permitida según SPEC
        ActualizarEstadoRequest request = new ActualizarEstadoRequest(EstadoTicket.RESUELTO);

        when(ticketRepository.findById(id)).thenReturn(Optional.of(ticket));

        // Act & Assert
        assertThrows(TransicionEstadoInvalidaException.class, () -> ticketService.cambiarEstado(id, request));
        verify(ticketRepository).findById(id);
        verify(ticketRepository, never()).saveAndFlush(any(Ticket.class));
    }
}
