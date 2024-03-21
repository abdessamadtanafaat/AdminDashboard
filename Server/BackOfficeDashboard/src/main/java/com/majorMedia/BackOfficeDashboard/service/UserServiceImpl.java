package com.majorMedia.BackOfficeDashboard.service;

import com.majorMedia.BackOfficeDashboard.entity.user.User;
import com.majorMedia.BackOfficeDashboard.exception.EntityNotFoundException;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;
import com.majorMedia.BackOfficeDashboard.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class UserServiceImpl implements IUserService {

    private UserRepository userRepository;
    public List<UserResponse> getAllUsers(String sortBy ,String searchKey) {
        List<UserResponse> userResponses = new ArrayList<>();

        List<User> users;
        if ("status".equals(sortBy)) {
             users = userRepository.findAll(Sort.by("isActive").descending());
        } else if ("date".equals(sortBy)) {
             users = userRepository.findAll(Sort.by("createdAt").descending());
        } else {
             users = userRepository.findAll();
        }

            if(users.isEmpty()){
                throw new EntityNotFoundException(User.class);
            }

        if (searchKey != null && !searchKey.isEmpty()) {
            userResponses = users.stream()
                    .filter(user -> user.getFullName().contains(searchKey) || user.getEmail().contains(searchKey))
                    .map(this::mapToUserResponse)
                    .collect(Collectors.toList());
            if (userResponses.isEmpty()) {
                throw new EntityNotFoundException(searchKey,User.class);
            }
        } else {
            userResponses = users.stream()
                    .map(this::mapToUserResponse)
                    .collect(Collectors.toList());
        }


        return userResponses;
    }
    private UserResponse mapToUserResponse (User user){
                UserResponse userResponse = new UserResponse();
                userResponse.setFullname(user.getFullName());
                userResponse.setEmail(user.getEmail());
                userResponse.setPhone(user.getPhone());
                userResponse.setStatus(user.isActive() ? "Active" : "Inactive");
                LocalDate createdAtDate = user.getCreatedAt().toLocalDate();
                userResponse.setCreatedAt(createdAtDate);

                return userResponse;
            }



        }

