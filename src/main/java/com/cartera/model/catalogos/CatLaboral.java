package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cat_laboral")
public class CatLaboral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_laboral")
    private Integer idLaboral;

    @Column(name = "des_laboral")
    private String desLaboral;

    @Column(name = "estatus")
    private Short estatus;
}
