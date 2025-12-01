package com.cartera.service;

import com.cartera.dto.PersonaPerfilDTO;
import com.cartera.model.DtPersona;
import com.cartera.model.Persona;
import com.cartera.repository.DtPersonaRepository;
import com.cartera.repository.PersonaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonaPerfilService {

    private final PersonaRepository sbRepo;
    private final DtPersonaRepository dtRepo;

    // ================== CARGAR PERFIL POR CORREO ===================
    public PersonaPerfilDTO cargarPerfil(String correo) {

        Persona sb = sbRepo.findByCorreo(correo)
                .orElseThrow(() ->
                        new RuntimeException("No existe persona con correo: " + correo));

        DtPersona dt = dtRepo.findById(sb.getId_persona())
                .orElse(new DtPersona());

        PersonaPerfilDTO dto = new PersonaPerfilDTO();

        // ==== SB_PERSONA ====
        dto.setIdPersona(sb.getId_persona());
        dto.setNombre(sb.getNombre());
        dto.setApellidoPaterno(sb.getApellido_paterno());
        dto.setApellidoMaterno(sb.getApellido_materno());
        dto.setCurp(sb.getCurp());
        dto.setRfc(sb.getRfc());
        dto.setCorreo(sb.getCorreo());

        // ==== DT_PERSONA ====
        dto.setFechaNacimiento(dt.getFechaNacimiento());
        dto.setEdad(dt.getEdad());
        dto.setSexo(dt.getIdSexo());
        dto.setEstadoCivil(dt.getIdEstadoCivil());
        dto.setNacionalidad(dt.getIdNacionalidad());

        // ==== UBICACIÓN ====
        dto.setIdEstado(dt.getIdEstado());
        dto.setIdMunicipio(dt.getIdMunicipio());
        dto.setIdLocalidad(dt.getIdLocalidad());

        dto.setCalle(dt.getCalle());
        dto.setColonia(dt.getColonia());
        dto.setCodigoPostal(dt.getCodigoPostal());
        dto.setNumExterior(dt.getNumExterior());
        dto.setNumInterior(dt.getNumInterior());

        dto.setTelefono(dt.getTelefono());
        dto.setTelefonoRecados(dt.getTelefonoRecados());
        dto.setCorreoAlternativo(dt.getCorreoAlternativo());
        dto.setRedSocial(dt.getRedSocial());

        dto.setEsEmpleado(dt.getEsEmpleado());
        dto.setSueldo(dt.getIdSueldo());
        dto.setDisponibilidadHorario(dt.getIdDisponibilidadHorario());
        dto.setDisponibilidadViajar(dt.getIdDisponibilidadViajar());


        return dto;
    }

    // ================== CARGAR PERFIL POR CURP ===================
    public PersonaPerfilDTO cargarPerfilPorCurp(String curp) {

        Persona sb = sbRepo.findByCurp(curp)
                .orElseThrow(() ->
                        new RuntimeException("No existe usuario con CURP: " + curp));

        return cargarPerfil(sb.getCorreo());
    }
}
