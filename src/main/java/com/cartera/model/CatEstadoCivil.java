package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cat_civil", schema = "public")
@Data
public class CatEstadoCivil {

    @Id
    private Integer id_estado_civil;

    private String descripcion;
}

