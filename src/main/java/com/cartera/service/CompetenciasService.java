package com.cartera.service;

import com.cartera.dto.CompetenciasDTO;
import java.util.List;

public interface CompetenciasService {

    void guardarLista(Long idPersona, List<Long> listaIds);

    List<Long> cargar(Long idPersona);
}
