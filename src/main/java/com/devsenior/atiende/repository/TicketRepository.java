package com.devsenior.atiende.repository;

import com.devsenior.atiende.model.EstadoTicket;
import com.devsenior.atiende.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByOrderByFechaCreacionDesc();

    List<Ticket> findByEstadoOrderByFechaCreacionDesc(EstadoTicket estado);
}
