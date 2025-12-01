package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cat_estados", schema = "public")
public class CatEstado {

    @Id
    @Column(name = "id_estado")
    private Integer idEstado;

    @Column(name = "nom_estado")
    private String nomEstado;

    @Column(name = "abreviatura")
    private String abreviatura;
}
