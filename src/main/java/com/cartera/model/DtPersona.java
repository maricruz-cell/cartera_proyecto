package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "dt_persona", schema = "public")
public class DtPersona {

    @Id
    @Column(name = "id_persona")
    private Long idPersona;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    private Integer edad;

    @Column(name = "id_sexo")
    private String idSexo;

    @Column(name = "id_estado_civil")
    private Integer idEstadoCivil;

    @Column(name = "id_nacionalidad")
    private Integer idNacionalidad;

    @Column(name = "id_estado")
    private Integer idEstado;

    @Column(name = "id_municipio")
    private Integer idMunicipio;

    @Column(name = "id_localidad")
    private Integer idLocalidad;

    private String calle;
    private String colonia;

    @Column(name = "codigo_postal")
    private String codigoPostal;

    @Column(name = "num_exterior")
    private String numExterior;

    @Column(name = "num_interior")
    private String numInterior;

    private String telefono;

    @Column(name = "telefono_recados")
    private String telefonoRecados;

    @Column(name = "correo_alternativo")
    private String correoAlternativo;

    @Column(name = "red_social")
    private String redSocial;

    @Column(name = "es_empleado")
    private Boolean esEmpleado;

    @Column(name = "id_sueldo")
    private Integer idSueldo;

    @Column(name = "id_disponibilidad_horario")
    private Integer idDisponibilidadHorario;

    @Column(name = "id_disponibilidad_viajar")
    private Integer idDisponibilidadViajar;
}
