package com.devsenior.atiende.service;

import com.devsenior.atiende.dto.ActualizarEstadoRequest;
import com.devsenior.atiende.dto.CrearTicketRequest;
import com.devsenior.atiende.dto.TicketResponse;
import com.devsenior.atiende.model.EstadoTicket;

import java.util.List;

public interface TicketService {

    TicketResponse crearTicket(CrearTicketRequest request);

    List<TicketResponse> listarTickets(EstadoTicket estado);

    TicketResponse obtenerTicketPorId(Long id);

    TicketResponse cambiarEstado(Long id, ActualizarEstadoRequest request);
}
