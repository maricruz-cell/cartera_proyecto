package com.cartera.dto.unidad;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequisicionListadoDTO {
    private Long idRequisicion;
    private String folio;
    private String cveDependencia;
    private String nomsecretaria;
    private String nombredep;
    private String fechaRegistro;
    private Long estatus;
}
