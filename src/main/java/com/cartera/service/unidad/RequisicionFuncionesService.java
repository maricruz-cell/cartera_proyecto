package com.cartera.service.unidad;

import com.cartera.repository.unidad.DtRequisicionFuncionesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RequisicionFuncionesService {

    private final DtRequisicionFuncionesRepository funcionesRepo;

    // 🔹 Obtener funciones ligadas a la requisición
    public List<Long> obtenerFunciones(Long idRequisicion) {
        return funcionesRepo.findFuncionesByRequisicion(idRequisicion);
    }

    // 🔹 Guardar funciones (reset + insert)
    public void guardarFunciones(Long idRequisicion, List<Long> funciones) {
        funcionesRepo.deleteByIdRequisicion(idRequisicion);

        for (Long idFuncion : funciones) {
            funcionesRepo.insert(idRequisicion, idFuncion);
        }
    }
}
