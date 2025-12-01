package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cat_municipios", schema = "public")
public class CatMunicipio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_municipio")
    private Integer idMunicipio;

    @Column(name = "id_estado")
    private Integer idEstado;

    @Column(name = "nom_municipio")
    private String nomMunicipio;
}
