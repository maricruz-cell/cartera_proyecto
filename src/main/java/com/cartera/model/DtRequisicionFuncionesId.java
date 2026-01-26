package com.cartera.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class DtRequisicionFuncionesId implements Serializable {
    private Long idRequisicion;
    private Long idFuncion;
}
