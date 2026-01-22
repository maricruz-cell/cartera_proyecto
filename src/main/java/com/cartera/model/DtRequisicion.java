package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "dt_requisicion")
public class DtRequisicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_requisicion")
    private Integer idRequisicion;

    @Column(name = "folio")
    private String folio;

    @Column(name = "anio")
    private Integer anio;

    @Column(name = "nomsecretaria")
    private String nomsecretaria;

    @Column(name = "fecha_ocupacion")
    private LocalDate fecha_ocupacion;

    @Column(name = "id_persona")
    private Long idPersona;

    @Column(name = "des_puesto")
    private String despuesto;

    @Column(name="numerovacantes")
    private Integer numeroVacantes;

    
    private Integer estatus;




}











