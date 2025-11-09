package com.dulara.figure_controller.service;

import com.dulara.figure_controller.dto.region.GetRegionsDTO;
import com.dulara.figure_controller.dto.region.RegionCreateRequestDTO;
import com.dulara.figure_controller.dto.region.RegionCreateResponseDTO;

import java.util.List;

public interface RegionService {
    RegionCreateResponseDTO saveRegion(RegionCreateRequestDTO regionCreateRequestDTO);

    List<GetRegionsDTO> getAllRegions();

    RegionCreateResponseDTO updateRegion(Long id, RegionCreateRequestDTO regionCreateRequestDTO);
}
