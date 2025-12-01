package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cat_area_capacitacion")
public class CatAreaCapacitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idArea;

    @Column(name = "des_area")
    private String desArea;

    private Short estatus;
}
