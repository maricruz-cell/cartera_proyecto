package com.cartera.repository;

import com.cartera.model.CatSectorGeneral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatSectorGeneralRepository extends JpaRepository<CatSectorGeneral, Integer> {}
