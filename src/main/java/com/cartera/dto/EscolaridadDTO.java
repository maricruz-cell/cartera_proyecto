package com.cartera.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EscolaridadDTO {

    private Integer idEscolaridad;   // id_escolaridad
    private Long idPersona;          // id_persona

    // IDs
    private Integer idEstudio;       // cat_estudios_concluidos
    private Integer idGrado;         // cat_grado_obtenido
    private Integer idLicenciatura;  // cat_licenciaturas
    private Integer idInstitucion;   // cat_institucion
    private Integer idModalidad;     // cat_modalidad

    private String otraCarrera;      // otra_carrera

    private Integer anioInicio;      // anio_inicio
    private Integer anioFin;         // anio_fin

    private String aniosCursados;    // calculado

    // 🔥 NUEVOS CAMPOS PARA MOSTRAR TEXTO EN LA TABLA
    private String estudioNombre;
    private String gradoNombre;
    private String licenciaturaNombre;
    private String institucionNombre;
    private String modalidadNombre;
}
