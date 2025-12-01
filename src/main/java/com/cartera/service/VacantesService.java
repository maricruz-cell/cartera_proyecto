package com.cartera.service;

import com.cartera.dto.VacanteRegistradaDto;
import com.cartera.repository.RequisicionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class VacantesService {

    private final RequisicionRepository repository;

    public List<VacanteRegistradaDto> listarVacantes() {

        return repository.obtenerVacantes()
                .stream()
                .map(v -> new VacanteRegistradaDto(
                        v.getIdRequisicion(),
                        v.getFolio(),
                        v.getFechaRegistro(),
                        v.getEstatus(),
                        v.getPuesto(),
                        v.getNumVacantes(),
                        v.getTipoContratacion()
                ))
                .toList();
    }
}
