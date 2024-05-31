package com.majorMedia.BackOfficeDashboard.service.adminService;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import com.majorMedia.BackOfficeDashboard.entity.user.User;

import com.majorMedia.BackOfficeDashboard.exception.EntityNotFoundException;
import com.majorMedia.BackOfficeDashboard.exception.InvalidCurrentPasswordException;
import com.majorMedia.BackOfficeDashboard.exception.InvalidTokenException;
import com.majorMedia.BackOfficeDashboard.exception.NotFoundEmailException;
import com.majorMedia.BackOfficeDashboard.model.requests.ResetPasswordRequest;

import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.repository.BusinessRepository;
import com.majorMedia.BackOfficeDashboard.repository.CampagneRepository;
import com.majorMedia.BackOfficeDashboard.repository.UserRepository;
import com.majorMedia.BackOfficeDashboard.security.SecurityConstants;
import com.majorMedia.BackOfficeDashboard.util.ImageUtils;
import com.majorMedia.BackOfficeDashboard.util.JwtUtils;
import com.majorMedia.BackOfficeDashboard.util.ServiceUtils;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import static com.majorMedia.BackOfficeDashboard.security.SecurityConstants.AVATAR_URL;

@Service
@AllArgsConstructor

public class AdminServiceImpl implements IAdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final BusinessRepository  businessRepository;
    private final CampagneRepository campagneRepository;
    private ServiceUtils adminService;

    @Override
    public String verifyToken(String token) {
        DecodedJWT decodedJWT;
        try {
            decodedJWT = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY.getEncoded()))
                    .build()
                    .verify(token);
        } catch (TokenExpiredException e) {
            return "expiredToken";
        }

        Date expiresAt = decodedJWT.getExpiresAt();
        if (expiresAt.before(new Date())) {
            return "expiredToken";
        }
        String email = decodedJWT.getSubject();

        List<String> tokenRoles = decodedJWT.getClaim("roles").asList(String.class);
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundEmailException(email));
        List<String> adminRoles = admin.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        // Compare each roleName in adminRoles with roles from the token
        Set<String> tokenRolesSet = new HashSet<>(tokenRoles);
        Set<String> adminRolesSet = new HashSet<>(adminRoles);

        if (!tokenRolesSet.equals(adminRolesSet)) {
            return JWT.create()
                    .withSubject(admin.getEmail())
                    .withClaim("id", admin.getId())
                    .withClaim("firstname", admin.getFirstname())
                    .withClaim("lastname", admin.getLastname())
                    .withClaim("email", admin.getEmail())
                    .withClaim("roles", adminRoles)
                    .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
                    .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY.getEncoded()));
        }

        // If you need to do something if the roles match, you can continue here
        // For example, log a success message or proceed with other logic
        return "goodToken" ;

    }

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
    public String resetPassword(String currentPasssword , String email, String password) {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundEmailException(email));
        // Check if the current password matches
        if (!passwordEncoder.matches(currentPasssword, admin.getPassword())) {
            throw new InvalidCurrentPasswordException("Current password is incorrect");
        }

        admin.setPassword(passwordEncoder.encode(password));
//        admin.setActive(false);
        adminRepository.save(admin);
        return "Password changed successfully";
    }


//    @Override
//    public String deactivateAccount(Long ownerId) throws BadRequestException {
//        return null;
//    }


//    @Override
//    public String resetPassword(String email, String password) {
//        return null;
//    }

    @Override
    public byte[] getImageData(Long adminId) {

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException(adminId,Admin.class));

        byte[] imageData = ImageUtils.decompressImage(admin.getImageByte());

        return imageData;
    }
/*    @Override
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
    }*/



/*
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
*/


}
