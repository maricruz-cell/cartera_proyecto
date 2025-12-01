package com.cartera.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "vw_capacitacion_completa")
public class VwCapacitacionCompleta {

    @Id
    private Integer idCapacitacion;

    private Long idPersona;
    private String descCurso;

    private String descFechaInicio;
    private String descFechaFin;

    private String area;
    private String tipoCurso;
    private String institucion;
    private String horas;

}
