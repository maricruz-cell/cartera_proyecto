package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cat_sueldo")
public class CatSueldo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sueldo")
    private Integer idSueldo;

    @Column(name = "rango")
    private String rango;

    @Column(name = "estatus")
    private Short estatus;
}
