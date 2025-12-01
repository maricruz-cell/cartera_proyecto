package com.cartera.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cat_sexo", schema = "public")
@Data
public class CatSexo {
    @Id
    private String id_sexo;

    private String des_sexo;
}
