package com.cartera.service.unidad;

import com.cartera.dto.unidad.RequisicionFormDTO;
import com.cartera.model.CatEstatusPlaza;
import com.cartera.model.DtRequisicion;
import com.cartera.model.unidad.*;
import com.cartera.repository.CatEstatusPlazaRepository;
import com.cartera.repository.unidad.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UnidadRequisicionService {

    private final DtRequisicionRepository requisicionRepo;
    private final DtRequisicionIdiomaRepository idiomaRepo;
    private final DtRequisicionInformaticaRepository informaticaRepo;
    private final DtRequisicionCompetenciasRepository competenciasRepo;
    private final DtRequisicionEscolaridadRepository escolaridadRepo;
    private final DtRequisicionFuncionesRepository funcionesRepo;
    private final CatEstatusPlazaRepository estatusRepo;


    @Transactional
    public Long crear(RequisicionFormDTO f) {

        Long idPersona = SecurityUtils.idPersonaLogueada();

        // 1️⃣ CONSTRUIR LA REQUISICIÓN
        DtRequisicion r = DtRequisicion.builder()
                .idPersona(idPersona)
                .folio(f.getFolio())
                .cveDependencia(f.getCveDependencia())
                .nomsecretaria(f.getNomsecretaria())
                .nombredep(f.getNombredep())
                .fechaRegistro(LocalDateTime.now())

                .unidadsolicitante(f.getUnidadsolicitante())
                .solicitante(f.getSolicitante())
                .diaentrevista(f.getDiaentrevista())
                .horaentrevista(f.getHoraentrevista())
                .presentarsecon(f.getPresentarsecon())

                .idEstadoSol(f.getIdEstadoSol())
                .idMunicipioSol(f.getIdMunicipioSol())
                .idLocalidadSol(f.getIdLocalidadSol())
                .coloniaSol(f.getColoniaSol())
                .calleSol(f.getCalleSol())
                .exteSol(f.getExteSol())
                .inteSol(f.getInteSol())
                .codigoPostalSol(f.getCodigoPostalSol())
                .correoSol(f.getCorreoSol())
                .telSol(f.getTelSol())

                .desPuesto(f.getDesPuesto())
                .tipoPlaza(f.getTipoPlaza())
                .tipoContrato(f.getTipoContrato())

                .idEstadoVac(f.getIdEstadoVac())
                .idMunicipioVac(f.getIdMunicipioVac())
                .idLocalidadVac(f.getIdLocalidadVac())
                .coloniaVac(f.getColoniaVac())
                .calleVac(f.getCalleVac())
                .exteriorVac(f.getExteriorVac())
                .interiorVac(f.getInteriorVac())
                .postalVac(f.getPostalVac())
                .diasLaborar(f.getDiasLaborar())
                .horaEntrada(f.getHoraEntrada())
                .horaSalida(f.getHoraSalida())

                .numerovacantes(
                        f.getNumeroVacantes() == null
                                ? null
                                : String.valueOf(f.getNumeroVacantes())
                )

                .sueldoVac(f.getSueldoVac())
                .viaje(f.getViaje())

                .documentoObtenido(f.getDocumentoObtenido())
                .carreraprof(f.getCarreraprof())
                .idExperiencia(
                        f.getIdExperiencia() == null ? null : f.getIdExperiencia().longValue()
                )

                .aniosExperiencia(f.getAniosExperiencia())
                .informaticaTexto(f.getInformaticaTxt())
                .especificos(f.getEspecificos())
                .build();   //  AQUÍ TERMINA EL BUILDER

        //  ASIGNAR ESTATUS (FUERA DEL BUILDER)
        CatEstatusPlaza estatus = estatusRepo.getReferenceById(1);
        r.setEstatusPlaza(estatus);

        //  GUARDAR REQUISICIÓN
        requisicionRepo.save(r);

        Long idReq = r.getIdRequisicion();

        //  IDIOMAS
        for (Long id : f.getIdiomas()) {
            idiomaRepo.save(DtRequisicionIdioma.builder()
                    .idRequisicion(idReq)
                    .idIdioma(id)
                    .build());
        }

        // INFORMÁTICA
        for (Long id : f.getInformaticas()) {
            informaticaRepo.save(DtRequisicionInformatica.builder()
                    .idRequisicion(idReq)
                    .idInformatica(id)
                    .build());
        }

        // COMPETENCIAS
        for (Long id : f.getCompetencias()) {
            competenciasRepo.save(DtRequisicionCompetencias.builder()
                    .idRequisicion(idReq)
                    .idCompetencia(id)
                    .build());
        }

        // ESCOLARIDAD
        for (Long id : f.getEscolaridades()) {
            escolaridadRepo.save(DtRequisicionEscolaridad.builder()
                    .idRequisicion(idReq)
                    .idEscolaridad(id)
                    .build());
        }

        //  FUNCIONES
        for (Long id : f.getFunciones()) {
            funcionesRepo.save(DtRequisicionFunciones.builder()
                    .idRequisicion(idReq)
                    .funcion(String.valueOf(id))
                    .build());
        }

        return idReq;
    }
}