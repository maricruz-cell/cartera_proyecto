package com.cartera.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cat_idiomas")
@Getter
@Setter
public class CatIdiomas {
    @Id
    @Column(name = "id_idioma")
    private Integer idIdioma;

    @Column(name = "des_idioma")
    private String desIdioma;
}

