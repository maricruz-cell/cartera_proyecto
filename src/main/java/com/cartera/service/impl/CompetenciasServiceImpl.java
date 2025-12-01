package com.cartera.service.impl;

import com.cartera.dto.CompetenciasDTO;
import com.cartera.model.DtCompetencias;
import com.cartera.repository.DtCompetenciasRepository;
import com.cartera.service.CompetenciasService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompetenciasServiceImpl implements CompetenciasService {

    private final DtCompetenciasRepository repo;

    @Override
    public void guardarLista(Long idPersona, List<Long> listaIds) {

        // 1. Borramos lo anterior
        repo.deleteByIdPersona(idPersona);

        // 2. Insertamos los nuevos
        for (Long idC : listaIds) {
            DtCompetencias c = new DtCompetencias();
            c.setIdPersona(idPersona);
            c.setIdCompetencia(idC);
            c.setValor(true);
            repo.save(c);
        }
    }

    @Override
    public List<Long> cargar(Long idPersona) {
        return repo.findByIdPersona(idPersona)
                .stream()
                .map(DtCompetencias::getIdCompetencia)
                .collect(Collectors.toList());
    }
}
