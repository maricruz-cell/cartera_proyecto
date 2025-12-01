package com.cartera.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ExperienciaLaboralDTO {

    private Integer idExperienciaLaboral;   // dt_experiencia_laboral.id_experiencia_laboral
    private Long    idPersona;             // dt_experiencia_laboral.id_persona

    // ---------- FKs a catálogos (pantalla DATOS LABORALES) ----------
    private Integer idTipoEmpleo;          // cat_empleo.id_tipo_empleo
    private Integer idSectorGeneral;       // cat_sector_general.id_sector_general
    private Integer idSector;              // cat_sector.id_sector  (Sector público)
    private Integer idLaboral;             // cat_laboral.id_laboral

    // ---------- FKs a catálogos (DESCRIPCIÓN DEL EMPLEO) ----------
    private Integer idSueldo;              // cat_sueldo.id_sueldo
    private Integer idTipoContratacion;    // cat_tipo_contratacion.id_tipo_contratacion
    private Integer idMotivoSeparacion;    // cat_motivo_separacion.id_motivo

    private Short   nivelPuesto;           // nivel_puesto (smallint)

    // ---------- Campos de texto / fechas ----------
    private String areaLaboralEspecifica;  // campo_experiencia
    private String empresa;                // empresa
    private String puesto;                 // puesto
    private String funciones;              // funciones
    private String otraFuncion;            // se guarda en OBSERVACIONES
    private String motivoSeparacion;       // motivo_separacion (texto libre)

    private LocalDate fechaInicio;         // fecha_inicio
    private LocalDate fechaFin;            // fecha_fin

    private Integer anios;                 // anios
    private Integer meses;                 // meses

    // ------ Nombres para mostrar en la tabla de arriba ------
    private String tipoEmpleoNombre;
    private String sectorGeneralNombre;
    private String sectorNombre;
    private String laboralNombre;
    private String sueldoNombre;
    private String tipoContratacionNombre;
    private String motivoSeparacionNombre;
    private String nivelPuestoNombre;
}
