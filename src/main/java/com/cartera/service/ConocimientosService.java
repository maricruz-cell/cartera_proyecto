package com.cartera.service;

import com.cartera.dto.ConocimientosDTO;

public interface ConocimientosService {
    ConocimientosDTO cargarConocimientos(Long idPersona);
    void guardarConocimientos(ConocimientosDTO dto);
}
