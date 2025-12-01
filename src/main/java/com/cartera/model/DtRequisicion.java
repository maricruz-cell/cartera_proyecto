package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "dt_requisicion")
public class DtRequisicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_requisicion;

    private String folio;
    private String titulo;
    private Integer id_estatus_plaza;
    private Integer id_tipo_contratacion;
    private Integer num_vacantes;

    @Column(name = "fecha_publicacion")
    private String fecha_publicacion;
}
