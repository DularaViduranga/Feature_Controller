package com.dulara.figure_controller.service;

import com.dulara.figure_controller.dto.branch.BranchCreateRequestDTO;
import com.dulara.figure_controller.dto.branch.BranchCreateResponseDTO;
import com.dulara.figure_controller.dto.branch.BranchUpdateRequestDTO;
import com.dulara.figure_controller.dto.branch.GetBranchesDTO;

import java.util.List;

public interface BranchService {
    BranchCreateResponseDTO saveBranch(BranchCreateRequestDTO branchCreateRequestDTO);


    List<GetBranchesDTO> getAllBranches();

    List<GetBranchesDTO> getBranchesByRegionId(Long id);

    BranchCreateResponseDTO updateBranch(Long id, BranchUpdateRequestDTO branchUpdateRequestDTO);
}
