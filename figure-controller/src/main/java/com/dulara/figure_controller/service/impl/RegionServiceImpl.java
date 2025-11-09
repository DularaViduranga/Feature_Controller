package com.dulara.figure_controller.service.impl;

import com.dulara.figure_controller.dto.region.GetRegionsDTO;
import com.dulara.figure_controller.dto.region.RegionCreateRequestDTO;
import com.dulara.figure_controller.dto.region.RegionCreateResponseDTO;
import com.dulara.figure_controller.entity.RegionEntity;
import com.dulara.figure_controller.repository.RegionRepository;
import com.dulara.figure_controller.service.RegionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;

    public RegionServiceImpl(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }


    @Override
    public RegionCreateResponseDTO saveRegions(RegionCreateRequestDTO regionCreateRequestDTO) {
        if(regionCreateRequestDTO.getName() == null || regionCreateRequestDTO.getCode() == null){
            throw new IllegalArgumentException("Region name and code must not be null");
        }
        if(regionRepository.existsByName(regionCreateRequestDTO.getName().toUpperCase())){
            throw new IllegalArgumentException("Region with name " + regionCreateRequestDTO.getName() + " already exists");
        }

        RegionEntity regionEntity = new RegionEntity(
                regionCreateRequestDTO.getName().toUpperCase(),
                regionCreateRequestDTO.getCode()
        );

        regionRepository.save(regionEntity);

        return new RegionCreateResponseDTO("Region created successfully",null);
    }

    @Override
    public List<GetRegionsDTO> getAllRegions() {
        List<RegionEntity> regionEntities = regionRepository.findAll();
        return regionEntities.stream()
                .map(region -> new GetRegionsDTO(region.getId(), region.getName(), region.getCode()))
                .toList();
    }

    @Override
    public RegionCreateResponseDTO updateRegion(Long id, RegionCreateRequestDTO regionCreateRequestDTO) {
        RegionEntity regionEntity = regionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Region with id " + id + " not found"));

        if(regionCreateRequestDTO.getName() != null && regionCreateRequestDTO.getCode() != null){
            if(regionRepository.existsByName(regionCreateRequestDTO.getName().toUpperCase())){
                throw new IllegalArgumentException("Region with name " + regionCreateRequestDTO.getName() + " already exists");
            }else{
                regionEntity.setName(regionCreateRequestDTO.getName().toUpperCase());
                regionEntity.setCode(regionCreateRequestDTO.getCode());
            }
        }
        regionRepository.save(regionEntity);

        return new RegionCreateResponseDTO("Region updated successfully", null);
    }


}
