package com.cartera.repository;

import com.cartera.model.CatMunicipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatMunicipiosRepository extends JpaRepository<CatMunicipio, Integer> {

    List<CatMunicipio> findByIdEstado(Integer idEstado);

}


