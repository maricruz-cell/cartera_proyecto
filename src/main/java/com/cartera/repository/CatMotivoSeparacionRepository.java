package com.cartera.repository;

import com.cartera.model.CatMotivoSeparacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatMotivoSeparacionRepository extends JpaRepository<CatMotivoSeparacion, Integer> {}
