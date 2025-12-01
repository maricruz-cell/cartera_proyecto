package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "dt_capacitacion")
public class DtCapacitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_capacitacion")
    private Integer idCapacitacion;

    @Column(name = "id_persona")
    private Long idPersona;

    @Column(name = "desc_curso")
    private String descCurso;

    @Column(name = "id_area_capacitacion")
    private Integer idAreaCapacitacion;

    @Column(name = "id_tipo_curso")
    private Integer idTipoCurso;

    @Column(name = "id_horas_curso")
    private Integer idHorasCurso;

    @Column(name = "id_institucion")
    private Integer idInstitucion;

    @Column(name = "desc_fecha_inicio")
    private LocalDate descFechaInicio;

    @Column(name = "desc_fecha_fin")
    private LocalDate descFechaFin;

}
