package com.cartera.controller;

import com.cartera.model.CatMunicipio;
import com.cartera.model.CatLocalidad;
import com.cartera.repository.CatMunicipiosRepository;
import com.cartera.repository.CatLocalidadesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CatalogosApiController {

    private final CatMunicipiosRepository municipiosRepo;
    private final CatLocalidadesRepository localidadesRepo;

    @GetMapping("/municipios/{idEstado}")
    public List<CatMunicipio> getMunicipios(@PathVariable Integer idEstado) {
        return municipiosRepo.findByIdEstado(idEstado);
    }

    @GetMapping("/localidades/{idMunicipio}")
    public List<CatLocalidad> getLocalidades(@PathVariable Integer idMunicipio) {
        return localidadesRepo.findByIdMunicipio(idMunicipio);
    }
}
