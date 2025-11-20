
// RegistroDto.java
package com.cartera.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegistroDto {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String curp;
    private String rfc;
    private String correo;
    private String correoConfirmacion;
    private String contrasena;
    private String contrasenaConfirmacion;
    private String sexo;              // si lo usas: "H" / "M"
    private Integer estadoCivil;       // opcional
    private Integer nacionalidad;      // opcional
    private String fechaNacimiento;   // yyyy-MM-dd
    private Integer edad;
    private Integer idEstado;





}
