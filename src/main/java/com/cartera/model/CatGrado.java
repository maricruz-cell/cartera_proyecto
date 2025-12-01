package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cat_grado_obtenido")
public class CatGrado {

    @Id
    @Column(name = "id_grado")
    private Integer idGrado;

    @Column(name = "des_grado")
    private String desGrado;
}
