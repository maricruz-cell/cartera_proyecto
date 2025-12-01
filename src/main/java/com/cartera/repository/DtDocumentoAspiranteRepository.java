package com.cartera.repository;

import com.cartera.model.DtDocumentosAspirante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DtDocumentoAspiranteRepository extends JpaRepository<DtDocumentosAspirante, Long> {
    List<DtDocumentosAspirante> findByIdPersona(Long idPersona);
}
