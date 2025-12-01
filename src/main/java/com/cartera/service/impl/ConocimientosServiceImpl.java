package com.cartera.service.impl;

import com.cartera.dto.ConocimientosDTO;
import com.cartera.model.PersonaIdioma;
import com.cartera.model.PersonaInformatica;
import com.cartera.model.PersonaEspecial;
import com.cartera.model.pk.PersonaIdiomaId;
import com.cartera.model.pk.PersonaInformaticaId;
import com.cartera.repository.*;
import com.cartera.service.ConocimientosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ConocimientosServiceImpl implements ConocimientosService {

    private final PersonaIdiomaRepository personaIdiomaRepo;
    private final PersonaInformaticaRepository personaInformaticaRepo;
    private final PersonaEspecialRepository personaEspecialRepo;

    // ================== CARGAR ==================
    @Override
    public ConocimientosDTO cargarConocimientos(Long idPersona) {

        ConocimientosDTO dto = new ConocimientosDTO();
        dto.setIdPersona(idPersona);

        // ----- IDIOMA -----
        List<PersonaIdioma> idioma = personaIdiomaRepo.findByIdPersona(idPersona);

        if (!idioma.isEmpty()) {
            PersonaIdioma pi = idioma.get(0);
            dto.setDominaOtroIdioma("SI");
            dto.setIdiomaSeleccionado(pi.getIdIdioma());
            dto.setNivelIdioma(pi.getNivel());
        } else {
            dto.setDominaOtroIdioma("NO");
        }

        // ----- INFORMÁTICA -----
        Map<Integer, String> mapa = new HashMap<>();

        personaInformaticaRepo.findByIdPersona(idPersona)
                .forEach(info -> mapa.put(info.getIdInformatica(), info.getNivel()));

        dto.setNivelesInformatica(mapa);

        // ----- PERFIL ESPECIALIZADO -----
        List<PersonaEspecial> especList = personaEspecialRepo.findByIdPersona(idPersona);

        if (!especList.isEmpty()) {
            PersonaEspecial e = especList.get(0);
            dto.setDescripcionEspecial(e.getDescripcion());
            dto.setNivelEspecial(e.getNivel());
        }

        return dto;
    }

    // ================== GUARDAR ==================
    @Override
    public void guardarConocimientos(ConocimientosDTO dto) {

        Long idPersona = dto.getIdPersona();

        // ----- IDIOMAS -----
        personaIdiomaRepo.deleteByIdPersona(idPersona);

        if ("SI".equals(dto.getDominaOtroIdioma()) &&
                dto.getIdiomaSeleccionado() != null &&
                dto.getNivelIdioma() != null) {

            PersonaIdioma pi = new PersonaIdioma();
            pi.setIdPersona(idPersona);
            pi.setIdIdioma(dto.getIdiomaSeleccionado());
            pi.setNivel(dto.getNivelIdioma());

            personaIdiomaRepo.save(pi);
        }

        // ----- INFORMÁTICA -----
        personaInformaticaRepo.deleteByIdPersona(idPersona);

        if (dto.getNivelesInformatica() != null) {
            dto.getNivelesInformatica().forEach((idInfo, nivel) -> {
                if (nivel != null && !nivel.isBlank()) {
                    PersonaInformatica info = new PersonaInformatica();
                    info.setIdPersona(idPersona);
                    info.setIdInformatica(idInfo);
                    info.setNivel(nivel);
                    personaInformaticaRepo.save(info);
                }
            });
        }

        // ----- PERFIL ESPECIALIZADO -----
        personaEspecialRepo.deleteByIdPersona(idPersona);

        if (dto.getDescripcionEspecial() != null && !dto.getDescripcionEspecial().isBlank()) {
            PersonaEspecial e = new PersonaEspecial();
            e.setIdPersona(idPersona);
            e.setDescripcion(dto.getDescripcionEspecial());
            e.setNivel(dto.getNivelEspecial());
            personaEspecialRepo.save(e);
        }
    }
}
