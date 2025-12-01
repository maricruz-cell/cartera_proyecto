package com.cartera.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cat_informatica")
@Getter
@Setter
public class CatInformatica {
    @Id
    @Column(name = "id_informatica")
    private Integer idInformatica;

    @Column(name = "des_informatica")
    private String desInformatica;
}

