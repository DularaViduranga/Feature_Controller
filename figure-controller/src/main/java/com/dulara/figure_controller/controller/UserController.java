package com.dulara.figure_controller.controller;

import com.dulara.figure_controller.dto.user.*;
import com.dulara.figure_controller.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO response = userService.login(loginRequestDTO);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/rmRegister")
    public ResponseEntity<UserRegisterResponseDTO> rmRegister(@RequestBody RMRegisterRequestDTO rmRegisterRequestDTO) {
        UserRegisterResponseDTO response = userService.rmRegister(rmRegisterRequestDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/bmRegister")
    public ResponseEntity<UserRegisterResponseDTO> bmRegister(@RequestBody BMRegisterRequestDTO bmRegisterRequestDTO) {
        UserRegisterResponseDTO response = userService.bmRegister(bmRegisterRequestDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/salesOfficerRegister")
    public ResponseEntity<UserRegisterResponseDTO> salesOfficerRegister(@RequestBody SalesOfficerSaveRequestDTO salesOfficerSaveRequestDTO) {
        UserRegisterResponseDTO response = userService.salesOfficerRegister(salesOfficerSaveRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllRMs")
    public ResponseEntity<List<GetAllRMsResponseDTO>> getAllRMs() {
        List<GetAllRMsResponseDTO> response = userService.getAllRMs();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllBMs")
    public ResponseEntity<List<GetAllBMsResponseDTO>> getAllBMs() {
        List<GetAllBMsResponseDTO> response = userService.getAllBMs();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllSalesOfficers")
    public ResponseEntity<List<GetAllSalesOfficerResponseDTO>> getAllSalesOfficers() {
        List<GetAllSalesOfficerResponseDTO> response = userService.getAllSalesOfficers();
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/deleteRM/{id}")
    public ResponseEntity<String> deleteRM(@PathVariable Long id) {
        userService.deleteRM(id);
        return ResponseEntity.ok("RM deleted successfully");
    }

    @DeleteMapping("/deleteBM/{id}")
    public ResponseEntity<String> deleteBM(@PathVariable Long id) {
        userService.deleteBM(id);
        return ResponseEntity.ok("BM deleted successfully");
    }

    @DeleteMapping("/deleteSalesOfficer/{id}")
    public ResponseEntity<String> deleteSalesOfficer(@PathVariable Long id) {
        userService.deleteSalesOfficer(id);
        return ResponseEntity.ok("SalesOfficer deleted successfully");
    }

    @PutMapping("/updatePassword/{id}")
    public ResponseEntity<PasswordChangeResponseDTO> updatePassword(@PathVariable Long id, @RequestBody PasswordChangeRequestDTO passwordChangeRequestDTO) {
        PasswordChangeResponseDTO response = userService.updatePassword(id, passwordChangeRequestDTO);

        if (response.getErrorMessage() != null) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);

    }
}
