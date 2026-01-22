package com.cartera.repository.unidad;

import com.cartera.dto.unidad.RequisicionDetalleDTO;
import com.cartera.dto.unidad.RequisicionListadoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
@RequiredArgsConstructor
public class UnidadRequisicionRepository {

    private final NamedParameterJdbcTemplate jdbc;

    /* =========================
       LISTADO
    ========================= */
    public List<RequisicionListadoDTO> listar() {
        String sql = """
            SELECT
                id_requisicion,
                folio,
                cve_dependencia,
                nomsecretaria,
                nombredep,
                fecha_registro,
                estatus
            FROM dt_requisicion
            ORDER BY id_requisicion DESC
        """;

        return jdbc.query(sql, (rs, rowNum) ->
                new RequisicionListadoDTO(
                        rs.getLong("id_requisicion"),
                        rs.getString("folio"),
                        rs.getString("cve_dependencia"),
                        rs.getString("nomsecretaria"),
                        rs.getString("nombredep"),
                        rs.getString("fecha_registro"),
                        rs.getObject("estatus") == null ? null : rs.getLong("estatus")
                )
        );
    }

    /* =========================
       DETALLE
    ========================= */
    public RequisicionDetalleDTO obtenerDetalle(Long id) {

        String sql = """
            SELECT *
            FROM dt_requisicion
            WHERE id_requisicion = :id
        """;

        var p = new MapSqlParameterSource("id", id);

        RequisicionDetalleDTO dto = jdbc.queryForObject(sql, p, (rs, rowNum) ->
                RequisicionDetalleDTO.builder()
                        .idRequisicion(rs.getLong("id_requisicion"))

                        .folio(rs.getString("folio"))
                        .cveDependencia(rs.getString("cve_dependencia"))
                        .nomsecretaria(rs.getString("nomsecretaria"))
                        .nombredep(rs.getString("nombredep"))
                        .fechaRegistro(rs.getString("fecha_registro"))
                        .estatus(rs.getObject("estatus") == null ? null : rs.getLong("estatus"))

                        .unidadsolicitante(rs.getString("unidadsolicitante"))
                        .solicitante(rs.getString("solicitante"))
                        .diaentrevista(rs.getString("diaentrevista"))
                        .diaentrevista2(rs.getString("diaentrevista2"))
                        .horaentrevista(rs.getString("horaentrevista"))
                        .presentarsecon(rs.getString("presentarsecon"))

                        .idEstadoSol(rs.getObject("id_estado_sol", Integer.class))
                        .idMunicipioSol(rs.getObject("id_municipio_sol", Integer.class))
                        .idLocalidadSol(rs.getObject("id_localidad_sol", Integer.class))
                        .coloniaSol(rs.getString("colonia_sol"))
                        .calleSol(rs.getString("calle_sol"))
                        .exteSol(rs.getString("exte_sol"))
                        .inteSol(rs.getString("inte_sol"))
                        .codigoPostalSol(rs.getString("codigo_postal_sol"))
                        .correoSol(rs.getString("correo_sol"))
                        .telSol(rs.getString("tel_sol"))

                        .desPuesto(rs.getString("des_puesto"))
                        .otroPuesto(rs.getString("otro_puesto"))
                        .tipoContrato(rs.getString("tipo_contrato"))
                        .otroContrato(rs.getString("otro_contrato"))
                        .tipoPlaza(rs.getString("tipo_plaza"))
                        .otroPlaza(rs.getString("otro_plaza"))
                        .numerovacantes(rs.getString("numerovacantes"))
                        .sueldoVac(rs.getObject("sueldo_vac") == null ? null : String.valueOf(rs.getLong("sueldo_vac")))
                        .diasLaborar(rs.getString("dias_laborar"))
                        .otroDia(rs.getString("otro_dia"))
                        .horaEntrada(rs.getString("hora_entrada"))
                        .horaSalida(rs.getString("hora_salida"))
                        .viaje(rs.getString("viaje"))

                        .idEstadoVac(rs.getObject("id_estado_vac", Integer.class))
                        .idMunicipioVac(rs.getObject("id_municipio_vac", Integer.class))
                        .idLocalidadVac(rs.getObject("id_localidad_vac", Integer.class))
                        .coloniaVac(rs.getString("colonia_vac"))
                        .calleVac(rs.getString("calle_vac"))
                        .exteriorVac(rs.getString("exterior_vac"))
                        .interiorVac(rs.getString("interior_vac"))
                        .postalVac(rs.getString("postal_vac"))

                        .documentoObtenido(rs.getString("documento_obtenido"))
                        .carreraprof(rs.getString("carreraprof"))
                        .otraCarrera(rs.getString("otra_carrera"))
                        .aniosExperiencia(rs.getString("años_experiencia"))
                        .especificos(rs.getString("especificos"))
                        .observaciones(rs.getString("observaciones"))
                        .build()
        );

        /* ===== LISTAS DESCRIPTIVAS ===== */
        dto.setFunciones(obtenerFunciones(id));
        dto.setIdiomas(obtenerIdiomas(id));
        dto.setInformaticaList(obtenerInformatica(id));
        dto.setCompetencias(obtenerCompetencias(id));
        dto.setEscolaridad(obtenerEscolaridad(id));

        return dto;
    }

    /* =========================
       CATÁLOGOS (TEXTOS)
    ========================= */
    public List<String> obtenerFunciones(Long id) {
        return jdbc.queryForList("""
            SELECT f.desc_funcion
            FROM dt_requisicion_funciones rf
            JOIN cat_funciones f ON f.id_funcion = rf.id_funcion
            WHERE rf.id_requisicion = :id
        """, Map.of("id", id), String.class);
    }

    public List<String> obtenerIdiomas(Long id) {
        return jdbc.queryForList("""
            SELECT i.desc_idioma
            FROM dt_requisicion_idiomas ri
            JOIN cat_idiomas i ON i.id_idioma = ri.id_idioma
            WHERE ri.id_requisicion = :id
        """, Map.of("id", id), String.class);
    }

    public List<String> obtenerInformatica(Long id) {
        return jdbc.queryForList("""
            SELECT inf.desc_informatica
            FROM dt_requisicion_informatica ri
            JOIN cat_informatica inf ON inf.id_informatica = ri.id_informatica
            WHERE ri.id_requisicion = :id
        """, Map.of("id", id), String.class);
    }

    public List<String> obtenerCompetencias(Long id) {
        return jdbc.queryForList("""
            SELECT c.desc_competencia
            FROM dt_requisicion_competencias rc
            JOIN cat_competencias c ON c.id_competencia = rc.id_competencia
            WHERE rc.id_requisicion = :id
        """, Map.of("id", id), String.class);
    }

    public List<String> obtenerEscolaridad(Long id) {
        return jdbc.queryForList("""
            SELECT e.desc_estudio
            FROM dt_requisicion_escolaridad re
            JOIN cat_estudios_concluidos e
              ON e.id_estudio_concluido = re.id_escolaridad
            WHERE re.id_requisicion = :id
        """, Map.of("id", id), String.class);
    }


    public List<RequisicionListadoDTO> listarRegistradas() {
        String sql = """
        SELECT id_requisicion, folio, cve_dependencia, nomsecretaria, nombredep, fecha_registro, estatus
        FROM dt_requisicion
        WHERE estatus IS DISTINCT FROM 5
        ORDER BY id_requisicion DESC
    """;

        return jdbc.query(sql, (rs, rowNum) ->
                new RequisicionListadoDTO(
                        rs.getLong("id_requisicion"),
                        rs.getString("folio"),
                        rs.getString("cve_dependencia"),
                        rs.getString("nomsecretaria"),
                        rs.getString("nombredep"),
                        rs.getString("fecha_registro"),
                        rs.getObject("estatus") == null ? null : rs.getLong("estatus")
                )
        );
    }

    public List<RequisicionListadoDTO> listarCubiertas() {
        String sql = """
        SELECT id_requisicion, folio, cve_dependencia, nomsecretaria, nombredep, fecha_registro, estatus
        FROM dt_requisicion
        WHERE estatus = 5
        ORDER BY id_requisicion DESC
    """;

        return jdbc.query(sql, (rs, rowNum) ->
                new RequisicionListadoDTO(
                        rs.getLong("id_requisicion"),
                        rs.getString("folio"),
                        rs.getString("cve_dependencia"),
                        rs.getString("nomsecretaria"),
                        rs.getString("nombredep"),
                        rs.getString("fecha_registro"),
                        rs.getLong("estatus")
                )
        );
    }

}
