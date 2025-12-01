package com.cartera.repository;

import com.cartera.model.CatLocalidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatLocalidadesRepository extends JpaRepository<CatLocalidad, Integer> {

    List<CatLocalidad> findByIdMunicipio(Integer idMunicipio);
}
