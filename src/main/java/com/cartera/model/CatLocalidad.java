package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cat_localidades", schema = "public")
public class CatLocalidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_localidad")
    private Integer idLocalidad;

    @Column(name = "id_municipio")
    private Integer idMunicipio;

    @Column(name = "nom_localidad")
    private String nomLocalidad;

    @Column(name = "id_estado")
    private Integer idEstado;
}
