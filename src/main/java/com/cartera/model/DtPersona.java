package com.cartera.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "dt_persona", schema = "public")
public class DtPersona {

    @Id
    @Column(name = "id_persona")
    private Long idPersona;  // Relación 1:1 con sb_persona

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_persona")
    private Persona persona;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    private Integer edad;

    @Column(name = "id_sexo")
    private Integer idSexo;

    @Column(name = "id_estado_civil")
    private Integer idEstadoCivil;

    @Column(name = "id_nacionalidad")
    private Integer idNacionalidad;

    private String otraNacionalidad;
    private String direccion;
    private String calle;
    private String numExterior;
    private String numInterior;
    private String colonia;
    private String codigoPostal;
    private Integer idEstado;
    private Integer idMunicipio;
    private Integer idLocalidad;
    private String telefono;
    private String telefonoRecados;
    private String correoAlternativo;
    private Integer idSueldo;
    private Integer idDisponibilidadHorario;
    private Integer idDisponibilidadViajar;
    private Boolean esEmpleado;

    // ======= Getters & Setters =======
    public Long getIdPersona() { return idPersona; }
    public void setIdPersona(Long idPersona) { this.idPersona = idPersona; }

    public Persona getPersona() { return persona; }
    public void setPersona(Persona persona) { this.persona = persona; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }

    public Integer getIdSexo() { return idSexo; }
    public void setIdSexo(Integer idSexo) { this.idSexo = idSexo; }

    public Integer getIdEstadoCivil() { return idEstadoCivil; }
    public void setIdEstadoCivil(Integer idEstadoCivil) { this.idEstadoCivil = idEstadoCivil; }

    public Integer getIdNacionalidad() { return idNacionalidad; }
    public void setIdNacionalidad(Integer idNacionalidad) { this.idNacionalidad = idNacionalidad; }

    public String getOtraNacionalidad() { return otraNacionalidad; }
    public void setOtraNacionalidad(String otraNacionalidad) { this.otraNacionalidad = otraNacionalidad; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public String getNumExterior() { return numExterior; }
    public void setNumExterior(String numExterior) { this.numExterior = numExterior; }

    public String getNumInterior() { return numInterior; }
    public void setNumInterior(String numInterior) { this.numInterior = numInterior; }

    public String getColonia() { return colonia; }
    public void setColonia(String colonia) { this.colonia = colonia; }

    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    public Integer getIdEstado() { return idEstado; }
    public void setIdEstado(Integer idEstado) { this.idEstado = idEstado; }

    public Integer getIdMunicipio() { return idMunicipio; }
    public void setIdMunicipio(Integer idMunicipio) { this.idMunicipio = idMunicipio; }

    public Integer getIdLocalidad() { return idLocalidad; }
    public void setIdLocalidad(Integer idLocalidad) { this.idLocalidad = idLocalidad; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getTelefonoRecados() { return telefonoRecados; }
    public void setTelefonoRecados(String telefonoRecados) { this.telefonoRecados = telefonoRecados; }

    public String getCorreoAlternativo() { return correoAlternativo; }
    public void setCorreoAlternativo(String correoAlternativo) { this.correoAlternativo = correoAlternativo; }

    public Integer getIdSueldo() { return idSueldo; }
    public void setIdSueldo(Integer idSueldo) { this.idSueldo = idSueldo; }

    public Integer getIdDisponibilidadHorario() { return idDisponibilidadHorario; }
    public void setIdDisponibilidadHorario(Integer idDisponibilidadHorario) { this.idDisponibilidadHorario = idDisponibilidadHorario; }

    public Integer getIdDisponibilidadViajar() { return idDisponibilidadViajar; }
    public void setIdDisponibilidadViajar(Integer idDisponibilidadViajar) { this.idDisponibilidadViajar = idDisponibilidadViajar; }

    public Boolean getEsEmpleado() { return esEmpleado; }
    public void setEsEmpleado(Boolean esEmpleado) { this.esEmpleado = esEmpleado; }
}
