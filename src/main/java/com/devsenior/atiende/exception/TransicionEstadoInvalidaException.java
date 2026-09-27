package com.devsenior.atiende.exception;

import com.devsenior.atiende.model.EstadoTicket;

public class TransicionEstadoInvalidaException extends RuntimeException {

    private final EstadoTicket origen;
    private final EstadoTicket destino;

    public TransicionEstadoInvalidaException(EstadoTicket origen, EstadoTicket destino) {
        super(String.format("Transición de estado no permitida de %s a %s", origen, destino));
        this.origen = origen;
        this.destino = destino;
    }

    public TransicionEstadoInvalidaException(String mensaje) {
        super(mensaje);
        this.origen = null;
        this.destino = null;
    }

    public EstadoTicket getOrigen() {
        return origen;
    }

    public EstadoTicket getDestino() {
        return destino;
    }
}
