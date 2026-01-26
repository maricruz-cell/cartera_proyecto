package com.cartera.service.reclutamiento;

import com.cartera.dto.reclutamiento.RequisicionDetalleDTO;
import com.cartera.dto.reclutamiento.RequisicionListadoDTO;
import com.cartera.model.CatEstatusPlaza;
import com.cartera.repository.CatEstatusPlazaRepository;
import com.cartera.repository.reclutamiento.UnidadRequisicionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnidadRequisicionService {

    private final UnidadRequisicionRepository repo;


    private final CatEstatusPlazaRepository estatusRepo;

    public List<CatEstatusPlaza> obtenerCatalogoEstatus() {
        return estatusRepo.findAll();
    }

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

    /* =========================
       CAMBIAR ESTATUS
    ========================= */

    public void cambiarEstatusPlaza(Long idRequisicion, Integer estatus) {
        repo.cambiarEstatusPlaza(idRequisicion, estatus);
    }



}
