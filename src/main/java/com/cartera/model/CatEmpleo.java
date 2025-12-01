package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cat_empleo")
public class CatEmpleo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_empleo")
    private Integer idTipoEmpleo;

    @Column(name = "des_tipo_empleo")
    private String desTipoEmpleo;

    @Column(name = "estatus")
    private Short estatus;
}
