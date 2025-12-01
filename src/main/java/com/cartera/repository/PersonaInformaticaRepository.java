package com.cartera.repository;

import com.cartera.model.PersonaInformatica;
import com.cartera.model.pk.PersonaInformaticaId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PersonaInformaticaRepository extends JpaRepository<PersonaInformatica, PersonaInformaticaId> {

    List<PersonaInformatica> findByIdPersona(Long idPersona);

    void deleteByIdPersona(Long idPersona);
}
