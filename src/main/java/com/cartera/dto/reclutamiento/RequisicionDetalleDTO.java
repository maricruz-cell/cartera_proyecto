package com.cartera.dto.unidad;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RequisicionDetalleDTO {

    private Long idRequisicion;

    // ===== Dependencia =====
    private String folio;
    private String cveDependencia;
    private String nomsecretaria;
    private String nombredep;
    private String fechaRegistro;
    private Long estatus;

    // ===== Solicitante =====
    private String unidadsolicitante;
    private String solicitante;
    private String diaentrevista;
    private String diaentrevista2;
    private String horaentrevista;
    private String presentarsecon;

    // ===== Dirección entrevista =====
    private Integer idEstadoSol;
    private Integer idMunicipioSol;
    private Integer idLocalidadSol;
    private String coloniaSol;
    private String calleSol;
    private String exteSol;
    private String inteSol;
    private String codigoPostalSol;
    private String correoSol;
    private String telSol;

    // ===== Vacante =====
    private String desPuesto;
    private String otroPuesto;
    private String tipoPlaza;
    private String otroPlaza;
    private String tipoContrato;
    private String otroContrato;
    private String numerovacantes;
    private String sueldoVac;       // en tu tabla es bigint, pero lo muestro string para UI (sin inventar conversiones)
    private String diasLaborar;
    private String otroDia;
    private String horaEntrada;
    private String horaSalida;
    private String viaje;

    // ===== Lugar vacante =====
    private Integer idEstadoVac;
    private Integer idMunicipioVac;
    private Integer idLocalidadVac;
    private String coloniaVac;
    private String calleVac;
    private String exteriorVac;
    private String interiorVac;
    private String postalVac;

    // ===== Perfil candidato =====
    private String documentoObtenido;
    private String carreraprof;
    private String otraCarrera;
    private Long idExperiencia;
    private String aniosExperiencia;     // tu columna: "años_experiencia"
    private String especificos;
    private String informatica;          // ojo: además existe tabla puente dt_requisicion_informatica
    private String observaciones;

    // ===== Relacionales por tablas puente (IDs por ahora) =====
    private List<String> idiomas;
    private List<String> informaticaList;
    private List<String> competencias;
    private List<String> escolaridad;
    private List<String> funciones;

    // ===== Helpers para mostrar OTRO sin lógica fea en HTML =====
    public String puestoMostrado() {
        return (otroPuesto != null && !otroPuesto.isBlank()) ? otroPuesto : desPuesto;
    }

    public String contratoMostrado() {
        return (otroContrato != null && !otroContrato.isBlank()) ? otroContrato : tipoContrato;
    }

    public String plazaMostrada() {
        return (otroPlaza != null && !otroPlaza.isBlank()) ? otroPlaza : tipoPlaza;
    }

    public String diasLaborarMostrado() {
        return (otroDia != null && !otroDia.isBlank()) ? otroDia : diasLaborar;
    }

    public String carreraMostrada() {
        return (otraCarrera != null && !otraCarrera.isBlank()) ? otraCarrera : carreraprof;
    }
}
