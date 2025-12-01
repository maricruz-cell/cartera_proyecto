package com.cartera.projection;

public interface VacanteProjection {
    Long getIdRequisicion();
    String getFolio();
    String getFechaRegistro();
    String getEstatus();
    String getPuesto();
    Integer getNumVacantes();
    String getTipoContratacion();
}
