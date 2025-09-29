package com.cartera.auth.model;

public class Direccion {
    private String estado;
    private String municipio;
    private String localidad;
    private String colonia;
    private String calle;
    private String numExterior;
    private String numInterior;
    private String cp;

    // --- Getters y Setters ---
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }

    public String getLocalidad() { return localidad; }
    public void setLocalidad(String localidad) { this.localidad = localidad; }

    public String getColonia() { return colonia; }
    public void setColonia(String colonia) { this.colonia = colonia; }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public String getNumExterior() { return numExterior; }
    public void setNumExterior(String numExterior) { this.numExterior = numExterior; }

    public String getNumInterior() { return numInterior; }
    public void setNumInterior(String numInterior) { this.numInterior = numInterior; }

    public String getCp() { return cp; }
    public void setCp(String cp) { this.cp = cp; }
}
