package com.dulara.figure_controller.repository;

import com.dulara.figure_controller.entity.BranchEntity;
import com.dulara.figure_controller.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<BranchEntity,Long> {
    Optional<BranchEntity> findByBranchManager_Id(Long id);
}
