package com.dulara.figure_controller.service.impl;

import com.dulara.figure_controller.dto.branch.BranchCreateRequestDTO;
import com.dulara.figure_controller.dto.branch.BranchCreateResponseDTO;
import com.dulara.figure_controller.dto.branch.BranchUpdateRequestDTO;
import com.dulara.figure_controller.dto.branch.GetBranchesDTO;
import com.dulara.figure_controller.dto.region.RegionCreateResponseDTO;
import com.dulara.figure_controller.entity.BranchEntity;
import com.dulara.figure_controller.entity.RegionEntity;
import com.dulara.figure_controller.repository.BranchRepository;
import com.dulara.figure_controller.repository.RegionRepository;
import com.dulara.figure_controller.service.BranchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final RegionRepository regionRepository;


    public BranchServiceImpl(BranchRepository branchRepository, RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
        this.branchRepository = branchRepository;
    }

    @Override
    public BranchCreateResponseDTO saveBranch(BranchCreateRequestDTO branchCreateRequestDTO) {
        RegionEntity regionEntity = regionRepository.findById(branchCreateRequestDTO.getRegionId())
                .orElseThrow(() -> new IllegalArgumentException("Region with id " + branchCreateRequestDTO.getRegionId() + " not found"));

        BranchEntity branchEntity = null;
        if (branchCreateRequestDTO.getName() != null && branchCreateRequestDTO.getCode() != null) {
            if (branchRepository.existsByName(branchCreateRequestDTO.getName().toUpperCase())) {
                throw new IllegalArgumentException("Branch with name " + branchCreateRequestDTO.getName() + " already exists");
            } else {
                branchEntity = new BranchEntity();
                branchEntity.setName(branchCreateRequestDTO.getName().toUpperCase());
                branchEntity.setCode(branchCreateRequestDTO.getCode());
                branchEntity.setRegion(regionEntity);
            }
        }
        branchRepository.save(branchEntity);

        return new BranchCreateResponseDTO("Branch created successfully", null);
    }

    @Override
    public List<GetBranchesDTO> getAllBranches() {
        List<BranchEntity> branchEntities = branchRepository.findAll();
        return branchEntities.stream()
                .map(branch -> new GetBranchesDTO(
                        branch.getId(),
                        branch.getName(),
                        branch.getCode()
                ))
                .toList();
    }

    @Override
    public List<GetBranchesDTO> getBranchesByRegionId(Long id) {
        List<BranchEntity> branchEntities = branchRepository.findByBranchByRegionId(id);
        return branchEntities.stream()
                .map(branch -> new GetBranchesDTO(
                        branch.getId(),
                        branch.getName(),
                        branch.getCode()
                ))
                .toList();
    }

    @Override
    public BranchCreateResponseDTO updateBranch(Long id, BranchUpdateRequestDTO branchUpdateRequestDTO) {
        BranchEntity branchEntity = branchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Branch with id " + id + " not found"));

        if (branchUpdateRequestDTO.getName() != null && branchUpdateRequestDTO.getCode() != null) {
            if (branchRepository.existsByName(branchUpdateRequestDTO.getName().toUpperCase())) {
                throw new IllegalArgumentException("Branch with name " + branchUpdateRequestDTO.getName() + " already exists");
            } else {
                branchEntity.setName(branchUpdateRequestDTO.getName().toUpperCase());
                branchEntity.setCode(branchUpdateRequestDTO.getCode());
            }
        }
        branchRepository.save(branchEntity);

        return new BranchCreateResponseDTO("Branch updated successfully", null);
    }


}
