package com.cartera.service;

import com.cartera.dto.CapacitacionDTO;
import java.util.List;

public interface CapacitacionService {

    List<CapacitacionDTO> listarPorPersona(Long idPersona);

    CapacitacionDTO buscarPorId(Integer idCapacitacion);

    void guardarCapacitacion(CapacitacionDTO dto);

    void eliminar(Integer idCapacitacion);
}
