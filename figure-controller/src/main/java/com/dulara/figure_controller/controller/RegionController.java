package com.dulara.figure_controller.controller;

import com.dulara.figure_controller.dto.region.GetRegionsDTO;
import com.dulara.figure_controller.dto.region.RegionCreateRequestDTO;
import com.dulara.figure_controller.dto.region.RegionCreateResponseDTO;
import com.dulara.figure_controller.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/regions")
public class RegionController {
    private RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @PostMapping("/saveRegions")
    public ResponseEntity<RegionCreateResponseDTO> saveRegions(@RequestBody RegionCreateRequestDTO regionCreateRequestDTO) {
        RegionCreateResponseDTO response = regionService.saveRegions(regionCreateRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllRegions")
    public ResponseEntity<List<GetRegionsDTO>> getAllRegions() {
        List<GetRegionsDTO> response = regionService.getAllRegions();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateRegion/{id}")
    public ResponseEntity<RegionCreateResponseDTO> updateRegion(@PathVariable Long id, @RequestBody RegionCreateRequestDTO regionCreateRequestDTO) {
        RegionCreateResponseDTO response = regionService.updateRegion(id, regionCreateRequestDTO);
        return ResponseEntity.ok(response);
    }


}
