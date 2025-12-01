package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cat_estudios_concluidos")
public class CatEstudio {

    @Id
    @Column(name = "id_estudio_concluido")
    private Integer idEstudio;

    @Column(name = "desc_estudio")
    private String descEstudio;
}
