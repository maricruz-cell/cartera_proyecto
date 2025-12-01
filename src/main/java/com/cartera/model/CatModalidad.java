package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cat_modalidad")
public class CatModalidad {

    @Id
    @Column(name = "id_modalidad")
    private Integer idModalidad;

    @Column(name = "des_modalidad")
    private String desModalidad;
}
