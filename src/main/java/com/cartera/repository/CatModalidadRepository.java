package com.cartera.repository;

import com.cartera.model.CatModalidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CatModalidadRepository extends JpaRepository<CatModalidad, Integer> {
}
