package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "dt_experiencia_laboral")
public class DtExperienciaLaboral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_experiencia_laboral")
    private Integer idExperienciaLaboral;

    @Column(name = "id_persona")
    private Long idPersona;

    // === Catálogos de la primera pantalla ===
    @Column(name = "id_tipo_empleo")
    private Integer idTipoEmpleo;          // FK cat_empleo

    @Column(name = "id_sector_general")
    private Integer idSectorGeneral;       // FK cat_sector_general

    @Column(name = "id_sector")
    private Integer idSector;              // FK cat_sector

    @Column(name = "id_laboral")
    private Integer idLaboral;             // FK cat_laboral

    // === Datos generales del empleo ===
    @Column(name = "empresa")
    private String empresa;

    @Column(name = "puesto")
    private String puesto;

    @Column(name = "campo_experiencia")
    private String campoExperiencia;       // área laboral específica

    @Column(name = "funciones")
    private String funciones;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "anios")
    private Integer anios;

    @Column(name = "meses")
    private Integer meses;

    // === Catálogos segunda pantalla ===
    @Column(name = "id_sueldo")
    private Integer idSueldo;              // cat_sueldo

    @Column(name = "id_tipo_contratacion")
    private Integer idTipoContratacion;    // cat_tipo_contratacion

    @Column(name = "id_motivo_separacion")
    private Integer idMotivoSeparacion;

    @Column(name = "motivo_separacion")
    private String motivoSeparacion;

    @Column(name = "nivel_puesto")
    private Short nivelPuesto;

    @Column(name = "observaciones")
    private String observaciones;
}
