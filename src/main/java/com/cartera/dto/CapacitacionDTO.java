package com.cartera.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CapacitacionDTO {

    private Integer idCapacitacion;
    private Long idPersona;

    // Nombre del curso (antes lo llamabas nombreCurso)
    private String descCurso;

    private Integer idAreaCapacitacion;
    private Integer idTipoCurso;
    private Integer idHorasCurso;
    private Integer idInstitucion;

    private LocalDate descFechaInicio;
    private LocalDate descFechaFin;



    private String area;
    private String tipoCurso;
    private String institucion;
    private String horas;


}
