package com.cartera.service;

import com.cartera.model.DtCuestionarios;
import com.cartera.repository.DtCuestionariosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CuestionarioService {

    private final DtCuestionariosRepository repo;

    public boolean yaContesto(Long idPersona) {
        // Aquí mandamos la regla: estatus="1" = contestado
        return repo.existsByIdPersonaAndEstatus(idPersona, "1");
    }

    @Transactional
    public void guardar(DtCuestionarios form) {
        // fuerza estatus contestado
        form.setEstatus("1");
        repo.save(form);
    }
}
