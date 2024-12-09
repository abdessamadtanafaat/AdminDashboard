package com.majorMedia.BackOfficeDashboard.service.whiteBox;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.service.superAdminService.SuperAdminImpl;
import com.majorMedia.BackOfficeDashboard.util.EmailUtils;
import com.majorMedia.BackOfficeDashboard.exception.AlreadyExistEmailException;
import jakarta.mail.MessagingException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ISuperAdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private EmailUtils emailUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SuperAdminImpl superAdminServiceImpl; // Assume this is the implementation class

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAdmin_AdminExists() {
        // Prepare test data
        Admin adminRequest = new Admin();
        adminRequest.setEmail("admin@example.com");

        // Mock the adminRepository to return an admin when searching by email
        when(adminRepository.findByEmail(adminRequest.getEmail())).thenReturn(Optional.of(adminRequest));

        // Verify that the exception is thrown when an admin already exists with the same email
        assertThrows(AlreadyExistEmailException.class, () -> {
            superAdminServiceImpl.createAdmin(adminRequest);
        });

        // Ensure that adminRepository.save is never called
        verify(adminRepository, never()).save(any(Admin.class));
    }

    @Test
    void testCreateAdmin_Success() throws MessagingException, UnsupportedEncodingException {
        // Prepare test data
        Admin adminRequest = new Admin();
        adminRequest.setEmail("newadmin@example.com");

        // Mock the adminRepository to return an empty Optional, meaning no admin exists with that email
        when(adminRepository.findByEmail(adminRequest.getEmail())).thenReturn(Optional.empty());

        // Mock password encoding and email sending
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword"); // Mock encoding
        doNothing().when(emailUtils).sendEmailToAdmin(anyString(), anyString()); // Mock sending email

        // Call the method
        Admin createdAdmin = superAdminServiceImpl.createAdmin(adminRequest);

        // Capture the actual arguments passed to sendEmailToAdmin
        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);

        verify(emailUtils, times(1)).sendEmailToAdmin(emailCaptor.capture(), passwordCaptor.capture());

        // Assert that the email was correct
        assertEquals("newadmin@example.com", emailCaptor.getValue());

        // Verify that the generated password is captured and matches expectations
        assertNotNull(passwordCaptor.getValue()); // Password should not be null
        assertNotEquals("encodedPassword", passwordCaptor.getValue()); // Ensure the captured password is the random one generated
        // Optionally, you can check if the password meets certain criteria (length, alphanumeric, etc.)
    }

    @Test
    void testCreateAdmin_PasswordGenerated() throws MessagingException, UnsupportedEncodingException {
        // Prepare test data
        Admin adminRequest = new Admin();
        adminRequest.setEmail("testadmin@example.com");

        // Mock the adminRepository to return an empty Optional (admin doesn't exist)
        when(adminRepository.findByEmail(adminRequest.getEmail())).thenReturn(Optional.empty());
        // Mock the password encoding and email sending
        String generatedPassword = RandomStringUtils.randomAlphabetic(10);  // Dynamically generated password
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        // Mock saving the admin and returning the admin object with a password
        when(adminRepository.save(any(Admin.class))).thenReturn(adminRequest);

        // Capture the generated password passed to sendEmailToAdmin
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        doNothing().when(emailUtils).sendEmailToAdmin(eq(adminRequest.getEmail()), passwordCaptor.capture());
        // Call the method
        Admin createdAdmin = superAdminServiceImpl.createAdmin(adminRequest);

        // Verify that the password was encoded and set in the createdAdmin object
        assertNotNull(createdAdmin); // Ensure that createdAdmin is not null
        assertEquals("encodedPassword", createdAdmin.getPassword());

        // Verify that the password was captured correctly
        String capturedPassword = passwordCaptor.getValue();
        assertNotNull(capturedPassword); // Ensure the password is not null
        assertNotEquals("", capturedPassword); // Ensure the password is not empty
    }
}