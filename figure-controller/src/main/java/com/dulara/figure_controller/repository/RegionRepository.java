package com.dulara.figure_controller.repository;

import com.dulara.figure_controller.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.ScopedValue;
import java.util.Optional;


@Repository
public interface RegionRepository extends JpaRepository<RegionEntity, Long> {

    Optional<RegionEntity> findByRegionalManager_Id(Long id);
}
