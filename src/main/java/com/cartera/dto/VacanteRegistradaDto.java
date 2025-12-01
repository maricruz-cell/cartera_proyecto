package com.cartera.dto;

public record VacanteRegistradaDto(
        Long idRequisicion,
        String folio,
        String fechaRegistro,
        String estatus,
        String puesto,
        Integer numVacantes,
        String tipoContratacion
) {}
