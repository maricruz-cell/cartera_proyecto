package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cat_nacionalidad", schema = "public")
@Data
public class CatNacionalidad {

    @Id
    private Integer id_nacionalidad;

    private String descripcion;
}

