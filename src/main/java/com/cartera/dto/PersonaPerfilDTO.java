package com.cartera.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PersonaPerfilDTO {

    // --- SB_PERSONA ---
    private Long idPersona;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String curp;
    private String rfc;
    private String correo;
    private Boolean primerLogin;


    // --- DT_PERSONA ---
    private LocalDate fechaNacimiento;
    private Integer edad;
    private String sexo;
    private Integer estadoCivil;
    private Integer nacionalidad;

    // 🔹 NUEVOS CAMPOS PARA UBICACIÓN
    private Integer idEstado;

    private Integer idMunicipio;
    private Integer idLocalidad;



    private String calle;
    private String colonia;
    private String codigoPostal;
    private String numExterior;
    private String numInterior;

    private String telefono;
    private String telefonoRecados;
    private String correoAlternativo;
    private String redSocial;

    private Boolean esEmpleado;
    private Integer sueldo;
    private Integer disponibilidadHorario;
    private Integer disponibilidadViajar;
}
