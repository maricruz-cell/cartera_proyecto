package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "dt_requisicion_funciones")
@IdClass(DtRequisicionFuncionesId.class)
public class DtRequisicionFunciones {

    @Id
    @Column(name = "id_requisicion")
    private Long idRequisicion;

    @Id
    @Column(name = "id_funcion")
    private Long idFuncion;
}
