package com.cartera.repository;

import com.cartera.model.CatInstitucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CatInstitucionRepository extends JpaRepository<CatInstitucion, Integer> {
}
