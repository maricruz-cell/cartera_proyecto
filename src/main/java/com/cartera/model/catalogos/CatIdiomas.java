package com.cartera.model.catalogos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cat_idiomas")
@Getter @Setter
public class CatIdiomas {

    @Id
    @Column(name = "id_idioma")
    private Integer idIdioma;

    @Column(name = "desc_idioma")
    private String descIdioma;
}
