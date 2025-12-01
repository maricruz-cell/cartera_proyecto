package com.cartera.repository;

import com.cartera.model.CatTipoContratacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatTipoContratacionRepository extends JpaRepository<CatTipoContratacion, Integer> {}
