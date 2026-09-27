package com.devsenior.atiende.model;

public enum EstadoTicket {
    ABIERTO,
    EN_PROCESO,
    RESUELTO,
    CERRADO;

    public boolean puedeCambiarA(EstadoTicket destino) {
        if (destino == null) {
            return false;
        }
        return switch (this) {
            case ABIERTO -> destino == EN_PROCESO || destino == CERRADO;
            case EN_PROCESO -> destino == RESUELTO;
            case RESUELTO -> destino == CERRADO || destino == EN_PROCESO;
            case CERRADO -> false;
        };
    }
}
