package com.devsenior.atiende.controller;

import com.devsenior.atiende.dto.ActualizarEstadoRequest;
import com.devsenior.atiende.dto.CrearTicketRequest;
import com.devsenior.atiende.dto.TicketResponse;
import com.devsenior.atiende.model.EstadoTicket;
import com.devsenior.atiende.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<TicketResponse> crearTicket(@Valid @RequestBody CrearTicketRequest request) {
        TicketResponse creado = ticketService.crearTicket(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> listarTickets(@RequestParam(required = false) EstadoTicket estado) {
        List<TicketResponse> tickets = ticketService.listarTickets(estado);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> obtenerTicketPorId(@PathVariable Long id) {
        TicketResponse ticket = ticketService.obtenerTicketPorId(id);
        return ResponseEntity.ok(ticket);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<TicketResponse> cambiarEstado(@PathVariable Long id,
                                                        @Valid @RequestBody ActualizarEstadoRequest request) {
        TicketResponse actualizado = ticketService.cambiarEstado(id, request);
        return ResponseEntity.ok(actualizado);
    }
}
