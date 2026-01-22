package com.cartera.service.unidad;

import com.cartera.dto.unidad.RequisicionDetalleDTO;
import com.cartera.dto.unidad.RequisicionListadoDTO;
import com.cartera.repository.unidad.UnidadRequisicionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnidadRequisicionService {

    private final UnidadRequisicionRepository repo;

    public List<RequisicionListadoDTO> listar() {
        return repo.listar();
    }

    public RequisicionDetalleDTO detalle(Long id) {
        return repo.obtenerDetalle(id);
    }

    public List<RequisicionListadoDTO> listarRegistradas() {
        return repo.listarRegistradas();
    }

    public List<RequisicionListadoDTO> listarCubiertas() {
        return repo.listarCubiertas();
    }

}
