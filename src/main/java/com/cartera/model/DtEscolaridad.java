package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "dt_escolaridad")
public class DtEscolaridad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_escolaridad")
    private Integer idEscolaridad;

    @Column(name = "id_persona")
    private Long idPersona;

    @Column(name = "id_estudio_concluido")
    private Integer idEstudio;

    @Column(name = "id_grado")
    private Integer idGrado;

    @Column(name = "id_institucion")
    private Integer idInstitucion;

    @Column(name = "id_modalidad")
    private Integer idModalidad;

    @Column(name = "id_licenciatura")
    private Integer idLicenciatura;

    @Column(name = "anio_inicio")
    private Integer anioInicio;

    @Column(name = "anio_fin")
    private Integer anioFin;

    @Column(name = "otra_carrera")
    private String otraCarrera;

    @Column(name = "anios_cursados")
    private String aniosCursados;
}
