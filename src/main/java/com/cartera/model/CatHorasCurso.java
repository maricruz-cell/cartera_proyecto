package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cat_horas_curso")
public class CatHorasCurso {

    @Id
    @Column(name = "id_horas_curso")
    private Integer idHorasCurso;

    @Column(name = "des_horas")
    private String desHoras;
}
