package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cat_sector")
public class CatSector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sector")
    private Integer idSector;

    @Column(name = "nom_sector")
    private String nomSector;

    @Column(name = "estatus")
    private Short estatus;
}
