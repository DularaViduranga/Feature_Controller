package com.dulara.figure_controller.service.impl;

import com.dulara.figure_controller.dto.user.*;
import com.dulara.figure_controller.entity.BranchEntity;
import com.dulara.figure_controller.entity.RegionEntity;
import com.dulara.figure_controller.entity.Role;
import com.dulara.figure_controller.entity.UserEntity;
import com.dulara.figure_controller.exception.UserAlreadyExistsException;
import com.dulara.figure_controller.repository.BranchRepository;
import com.dulara.figure_controller.repository.RegionRepository;
import com.dulara.figure_controller.repository.UserRepository;
import com.dulara.figure_controller.security.JwtUtil;
import com.dulara.figure_controller.security.MD5PasswordEncoder;
import com.dulara.figure_controller.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RegionRepository regionRepository;
    private final BranchRepository branchRepository;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final MD5PasswordEncoder md5PasswordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RegionRepository regionRepository,
                           BranchRepository branchRepository,
                           JwtUtil jwtUtil,
                           UserDetailsService userDetailsService,
                           MD5PasswordEncoder md5PasswordEncoder) {
        this.userRepository = userRepository;
        this.regionRepository = regionRepository;
        this.branchRepository = branchRepository;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.md5PasswordEncoder = md5PasswordEncoder;
    }



    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        UserEntity user = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found" ));

        String hashedInputPassword = md5PasswordEncoder.encode(loginRequestDTO.getPassword());


        if (!user.getPassword().equals(hashedInputPassword)) {
            throw new RuntimeException("Invalid password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDTO.getUsername());

        Long branchId = user.getBranchId();
        Long regionId = user.getRegionId();

        // Handle null values - convert to 0 or appropriate default values
        long branchIdValue = branchId != null ? branchId : 0L;
        long regionIdValue = regionId != null ? regionId : 0L;

        String token = jwtUtil.generateToken(userDetails, user.getId(), user.getRole().name(), regionIdValue, branchIdValue);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(
                token, LocalDateTime.now(), user.getRole().name(), branchId, regionId
        );

        return loginResponseDTO;
    }

    @Override
    public UserRegisterResponseDTO rmRegister(RMRegisterRequestDTO rmRegisterRequestDTO) {
        if(userRepository.existsByUsername(rmRegisterRequestDTO.getUsername())){
            throw new UserAlreadyExistsException("Username already exists");
        }

        RegionEntity region = regionRepository.findById(rmRegisterRequestDTO.getRegionId())
                .orElseThrow(() -> new RuntimeException("Region not found with ID: " + rmRegisterRequestDTO.getRegionId()));

        if(region.getRegionalManager() == null){
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(rmRegisterRequestDTO.getUsername());
            userEntity.setPassword(md5PasswordEncoder.encode(rmRegisterRequestDTO.getPassword()));
            userEntity.setRole(Role.RM);
            userEntity.setRegion(region);

            UserEntity savedUser = userRepository.save(userEntity);

            region.setRegionalManager(savedUser);
            regionRepository.save(region);

            regionRepository.save(region);

            return new UserRegisterResponseDTO("RM registered successfully", null);
        }else {
            throw new RuntimeException("Region already has a Regional Manager assigned");
        }


    }

    @Override
    public UserRegisterResponseDTO bmRegister(BMRegisterRequestDTO bmRegisterRequestDTO) {
        if(userRepository.existsByUsername(bmRegisterRequestDTO.getUsername())){
            throw new UserAlreadyExistsException("Username already exists");
        }


        BranchEntity branch = branchRepository.findById(bmRegisterRequestDTO.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found with ID: " + bmRegisterRequestDTO.getBranchId()));

        if(branch.getBranchManager() == null){
            RegionEntity region = branch.getRegion();

            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(bmRegisterRequestDTO.getUsername());
            userEntity.setPassword(md5PasswordEncoder.encode(bmRegisterRequestDTO.getPassword()));
            userEntity.setRole(Role.BM);
            userEntity.setBranch(branch);
            userEntity.setRegion(region);


            UserEntity savedUser = userRepository.save(userEntity);

            branch.setBranchManager(savedUser);
            branchRepository.save(branch);

            return new UserRegisterResponseDTO("BM registered successfully", null);
        } else {
            return new UserRegisterResponseDTO(null, "Branch already has a Branch Manager assigned");
        }
    }

    @Override
    public UserRegisterResponseDTO salesOfficerRegister(SalesOfficerSaveRequestDTO salesOfficerSaveRequestDTO) {
        if(userRepository.existsByUsername(salesOfficerSaveRequestDTO.getUsername())){
            throw new UserAlreadyExistsException("Username already exists");
        }

        BranchEntity branch = branchRepository.findById(salesOfficerSaveRequestDTO.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found with ID: " + salesOfficerSaveRequestDTO.getBranchId()));

        RegionEntity region = branch.getRegion();

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(salesOfficerSaveRequestDTO.getUsername());
        userEntity.setPassword(md5PasswordEncoder.encode(salesOfficerSaveRequestDTO.getPassword()));
        userEntity.setRole(Role.SALES_OFFICER);
        userEntity.setBranch(branch);
        userEntity.setRegion(region);


        UserEntity savedUser = userRepository.save(userEntity);

        branch.setBranchManager(savedUser);
        branchRepository.save(branch);

        return new UserRegisterResponseDTO("Sales Officer registered successfully", null);
    }

    @Override
    public List<GetAllRMsResponseDTO> getAllRMs() {
        List<UserEntity> rmEntities = userRepository.findByRole(Role.RM);
        return rmEntities.stream()
                .map(rm -> new GetAllRMsResponseDTO(
                        rm.getId(),
                        rm.getUsername(),
                        rm.getRegion() != null ? rm.getRegion().getName() : null
                ))
                .toList();
    }

    @Override
    public List<GetAllBMsResponseDTO> getAllBMs() {
        List<UserEntity> rmEntities = userRepository.findByRole(Role.BM);
        return rmEntities.stream()
                .map(rm -> new GetAllBMsResponseDTO(
                        rm.getId(),
                        rm.getUsername(),
                        rm.getBranch() != null ? rm.getBranch().getName() : null,
                        rm.getRegion() != null ? rm.getRegion().getName() : null
                ))
                .toList();
    }

    @Override
    public List<GetAllSalesOfficerResponseDTO> getAllSalesOfficers() {
        List<UserEntity> rmEntities = userRepository.findByRole(Role.SALES_OFFICER);
        return rmEntities.stream()
                .map(rm -> new GetAllSalesOfficerResponseDTO(
                        rm.getId(),
                        rm.getUsername(),
                        rm.getBranch() != null ? rm.getBranch().getName() : null,
                        rm.getRegion() != null ? rm.getRegion().getName() : null
                ))
                .toList();
    }

    @Override
    public void deleteRM(Long id) {
        if(!userRepository.existsById(id)){
            throw new UsernameNotFoundException("User not found ");
        }else  {

            // Find the region that this RM manages
            RegionEntity region = regionRepository.findByRegionalManager_Id(id)
                    .orElse(null);
            if (region != null) {
                region.setRegionalManager(null);
                regionRepository.save(region);
            }
            userRepository.deleteById(id);
        }
    }

    @Override
    public void deleteBM(Long id) {
        if(!userRepository.existsById(id)){
            throw new UsernameNotFoundException("User not found ");
        }else  {

            // Find the branch that this RM manages
            BranchEntity branch = branchRepository.findByBranchManager_Id(id)
                    .orElse(null);
            if (branch != null) {
                branch.setBranchManager(null);
                branchRepository.save(branch);
            }
            userRepository.deleteById(id);
        }
    }

    @Override
    public void deleteSalesOfficer(Long id) {
        if(!userRepository.existsById(id)){
            throw new UsernameNotFoundException("User not found ");
        }else  {
            userRepository.deleteById(id);
        }
    }



    @Override
    public PasswordChangeResponseDTO updatePassword(Long id, PasswordChangeRequestDTO passwordChangeRequestDTO) {
        try {
            UserEntity user = userRepository.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            String hashedOldPassword = md5PasswordEncoder.encode(passwordChangeRequestDTO.getOldPassword());

            if (!user.getPassword().equals(hashedOldPassword)) {
                return new PasswordChangeResponseDTO(null, "Old password is incorrect");
            }

            if (!passwordChangeRequestDTO.getNewPassword().equals(passwordChangeRequestDTO.getConfirmNewPassword())) {
                return new PasswordChangeResponseDTO(null, "New password and Confirm new password do not match");
            }

            user.setPassword(md5PasswordEncoder.encode(passwordChangeRequestDTO.getNewPassword()));
            userRepository.save(user);

            return new PasswordChangeResponseDTO("Password updated successfully", null);
        } catch (RuntimeException e) {
            log.error("Error in updatePassword service: ", e);
            throw e; // Re-throw to be caught by controller
        }
    }


}
