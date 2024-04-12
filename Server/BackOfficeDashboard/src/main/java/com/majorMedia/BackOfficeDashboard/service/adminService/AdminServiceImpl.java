package com.majorMedia.BackOfficeDashboard.service.adminService;

import com.majorMedia.BackOfficeDashboard.entity.business.Business;
import com.majorMedia.BackOfficeDashboard.entity.user.User;
import com.majorMedia.BackOfficeDashboard.exception.*;
import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.model.requests.ResetPasswordRequest;
import com.majorMedia.BackOfficeDashboard.model.responses.BusinessResponse;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.repository.BusinessRepository;
import com.majorMedia.BackOfficeDashboard.repository.RoleRepository;
import com.majorMedia.BackOfficeDashboard.repository.UserRepository;
import com.majorMedia.BackOfficeDashboard.security.SecurityConstants;
import com.majorMedia.BackOfficeDashboard.util.EmailUtils;
import com.majorMedia.BackOfficeDashboard.util.ImageUtils;
import com.majorMedia.BackOfficeDashboard.util.JwtUtils;
import com.majorMedia.BackOfficeDashboard.util.ServiceUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.majorMedia.BackOfficeDashboard.security.SecurityConstants.*;

@Service
@AllArgsConstructor

public class AdminServiceImpl implements IAdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private ServiceUtils adminService;
    @Override
    public String updateAccountSettings(MultipartFile file,
                                        String firstname,
                                        String lastname,
                                        String email) throws IOException {

        if (StringUtils.isEmpty(lastname) || StringUtils.isEmpty(firstname)) {
            throw new IllegalArgumentException("firstname and lastname must not be null or empty");
        }
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundEmailException(email));

        admin.setFirstname(firstname);
        admin.setLastname(lastname);

//        String encodedImage = request.getEncodedImage();
//
//        byte[] imageData = encodedImage.getBytes();
//        byte[] compressedImageData = ImageUtils.compressImage(imageData);
//        admin.setImageByte(compressedImageData);
//        admin.setAvatarUrl(request.getAvatarUrl());
        adminRepository.save(uploadAdminAvatar(admin.getId() , file));

        return admin.getAvatarUrl();
    }
    @Override
    public Admin uploadAdminAvatar(Long adminId, MultipartFile file) throws IOException {

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException(adminId, Admin.class));

        if(file ==null){
            admin.setAvatarUrl(null);
            admin.setImageByte(null);

        }
        else{
            ImageUtils.makeDirectoryIfNotExist(SecurityConstants.AVATAR_DIRECTORY);

//            String fileName = admin.getFirstname() + admin.getLastname() +
//                    "Avatar." +
//                    FilenameUtils.getExtension(file.getOriginalFilename());
//            Path filePath = Paths.get(AVATAR_DIRECTORY, fileName);
//
//            Files.write(filePath, file.getBytes());

            String avatarUrl = AVATAR_URL + admin.getId();

            byte[] imageData = file.getBytes();

            byte[] compressedImageData = ImageUtils.compressImage(imageData);

            admin.setImageByte(compressedImageData);

            admin.setAvatarUrl(avatarUrl);
        }
        return admin;

    }
    @Override
    public String changePassword(ResetPasswordRequest resetPasswordRequest) {

        if (!JwtUtils.isValidJwt(resetPasswordRequest.getToken())) {
            throw new InvalidTokenException();
        }

        String email = JwtUtils.extractEmailFromToken(resetPasswordRequest.getToken());

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundEmailException(email));

        admin.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        admin.setActive(false);
        adminRepository.save(admin);
        return "Password changed successfully";
    }
    @Override
    public byte[] getImageData(Long adminId) {

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException(adminId,Admin.class));

        byte[] imageData = ImageUtils.decompressImage(admin.getImageByte());

        return imageData;
    }
    @Override
    public List<UserResponse> getAllOwners(String sortBy , String searchKey) {
        List<UserResponse> userResponses = new ArrayList<>();

        List<User> users = adminService.fetchUsers(sortBy);


        // Map and combine users and admins
        List<UserResponse> userResponsesFromUsers = users.stream()
                .map(user -> adminService.mapToUserResponse(user, "Owner"))
                .collect(Collectors.toList());


        userResponses.addAll(userResponsesFromUsers);
        userResponses.sort(Comparator.comparing(UserResponse::getLastLogout).reversed());

        if(userResponses.isEmpty()){
            throw new EntityNotFoundException(User.class);
        }

        // Apply filtering by profile if requested
        if (searchKey != null && !searchKey.isEmpty()) {
            userResponses = userResponses.stream()
                    .filter(userResponse -> userResponse.getFirstname().contains(searchKey) || userResponse.getLastname().contains(searchKey)|| userResponse.getEmail().contains(searchKey))
                    .collect(Collectors.toList());
            if (userResponses.isEmpty()) {
                throw new EntityNotFoundException(searchKey, User.class);
            }
        }

        return userResponses;
    }

    @Override
    @Transactional
    public String deactivateAccount(Long ownerId) {
        if (ownerId == null) {
            throw new IllegalArgumentException("Owner are required");
        }

        User owner  = userRepository.findById(ownerId)
                .orElseThrow(()->new EntityNotFoundException(ownerId, User.class));

        owner.set_deactivated(true);
        userRepository.save(owner);
        return "Account : "+owner.getLastName() +" deactivated Successfully";
    }

    @Override
    @Transactional
    public String activateAccount(Long ownerId) {
        if (ownerId == null) {
            throw new IllegalArgumentException("Owner are required");
        }
        User owner  = userRepository.findById(ownerId)
                .orElseThrow(()->new EntityNotFoundException(ownerId, User.class));

        owner.set_deactivated(true);
        userRepository.save(owner);
        return "Account : "+owner.getLastName() +" activated Successfully";
    }

    @Override
    public List<BusinessResponse> getAllBusiness(String sortBy, String searchKey) {

        List<BusinessResponse> businessResponses = new ArrayList<>();

        List<Business> businesses = adminService.fetchBusiness(sortBy);

        // Map and combine users and admins
        List<BusinessResponse> businessResponseFromBusiness = businesses.stream()
                .map(business -> adminService.mapToBusinessResponse(business))
                .collect(Collectors.toList());


        businessResponses.addAll(businessResponseFromBusiness);
        businessResponses.sort(Comparator.comparing(BusinessResponse::getCreatedDate).reversed());

        if(businessResponses.isEmpty()){
            throw new EntityNotFoundException(Business.class);
        }

        // Apply filtering by profile if requested
        if (searchKey != null && !searchKey.isEmpty()) {
            businessResponses = businessResponses.stream()
                    .filter(businessResponse -> businessResponse.getBusinessName().contains(searchKey) || businessResponse.getUser().getFullName().contains(searchKey)|| businessResponse.getEmail().contains(searchKey))
                    .collect(Collectors.toList());
            if (businessResponses.isEmpty()) {
                throw new EntityNotFoundException(searchKey, Business.class);
            }
        }

        return businessResponses;
    }


}
