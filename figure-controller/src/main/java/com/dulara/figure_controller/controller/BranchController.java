package com.dulara.figure_controller.controller;


import com.dulara.figure_controller.dto.branch.BranchCreateRequestDTO;
import com.dulara.figure_controller.dto.branch.BranchCreateResponseDTO;
import com.dulara.figure_controller.dto.branch.BranchUpdateRequestDTO;
import com.dulara.figure_controller.dto.branch.GetBranchesDTO;
import com.dulara.figure_controller.service.BranchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/branches")
public class BranchController {
    private BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping("/saveBranch")
    public ResponseEntity<BranchCreateResponseDTO> saveBranch(@RequestBody BranchCreateRequestDTO branchCreateRequestDTO) {
        BranchCreateResponseDTO response = branchService.saveBranch(branchCreateRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("getAllBranches")
    public ResponseEntity<List<GetBranchesDTO>> getAllBranches() {
        List<GetBranchesDTO> response = branchService.getAllBranches();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getBranchByRegionId/{id}")
    public ResponseEntity<List<GetBranchesDTO>> getBranchByRegionId(@PathVariable Long id) {
        List<GetBranchesDTO> response = branchService.getBranchesByRegionId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateBranch/{id}")
    public ResponseEntity<BranchCreateResponseDTO> updateBranch(@PathVariable Long id, @RequestBody BranchUpdateRequestDTO branchUpdateRequestDTO) {
        BranchCreateResponseDTO response = branchService.updateBranch(id, branchUpdateRequestDTO);
        return ResponseEntity.ok(response);
    }

}
