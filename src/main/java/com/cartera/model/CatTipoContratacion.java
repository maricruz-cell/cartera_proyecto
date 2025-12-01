package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cat_tipo_contratacion")
public class CatTipoContratacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_contratacion")
    private Integer idTipoContratacion;

    @Column(name = "des_tipo")
    private String desTipo;

    @Column(name = "estatus")
    private Short estatus;
}
