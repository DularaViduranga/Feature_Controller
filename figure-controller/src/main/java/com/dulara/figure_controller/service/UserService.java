package com.dulara.figure_controller.service;

import com.dulara.figure_controller.dto.user.*;

import java.util.List;

public interface UserService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    UserRegisterResponseDTO rmRegister(RMRegisterRequestDTO rmRegisterRequestDTO);

    UserRegisterResponseDTO bmRegister(BMRegisterRequestDTO bmRegisterRequestDTO);

    UserRegisterResponseDTO salesOfficerRegister(SalesOfficerSaveRequestDTO salesOfficerSaveRequestDTO);

    void deleteRM(Long id);

    void deleteBM(Long id);

    List<GetRMsResponseDTO> getAllRMs();

    List<GetBMsResponseDTO> getAllBMs();

    List<GetSalesOfficerResponseDTO> getAllSalesOfficers();

    void deleteSalesOfficer(Long id);

    PasswordChangeResponseDTO updatePassword(Long id, PasswordChangeRequestDTO passwordChangeRequestDTO);

    List<GetBMsResponseDTO> getBMbyRegion(Long regionId);

    List<GetSalesOfficerResponseDTO> getSalesOfficerbyRegion(Long regionId);

    List<GetSalesOfficerResponseDTO> getSalesOfficerbyBranch(Long branchId);
}
