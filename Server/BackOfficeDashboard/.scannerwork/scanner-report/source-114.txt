package com.majorMedia.BackOfficeDashboard.service;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.exception.InvalidTokenException;
import com.majorMedia.BackOfficeDashboard.model.requests.ResetPasswordRequest;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.security.manager.CustomAuthenticationManager;
import com.majorMedia.BackOfficeDashboard.util.EmailUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IcustomAuthenticationManagerTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private EmailUtils emailUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomAuthenticationManager customAuthenticationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialise les mocks
    }

    @Test
    void testResetPassword_Success() {
        // Mock des données
        ResetPasswordRequest request = new ResetPasswordRequest("validToken", "currentPassword", "email@example.com", "newPassword123");
        Admin admin = new Admin();
        admin.setTokenEmail("validToken");

        when(emailUtils.checkValidity(request.getToken())).thenReturn(String.valueOf(true)); // Token valide
        when(adminRepository.findByTokenEmail(request.getToken())).thenReturn(Optional.of(admin));
        when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPassword");

        // Appel de la méthode
        String result = customAuthenticationManager.resetPassword(request);

        // Vérifications
        assertEquals("Password successfully reset", result);
        assertEquals("hashedPassword", admin.getPassword());
        verify(adminRepository, times(2)).save(admin); // Vérifier que save() est appelé deux fois
    }

    @Test
    void testResetPassword_InvalidToken() {
        // Mock des données
        ResetPasswordRequest request = new ResetPasswordRequest("invalidToken", "currentPassword", "email@example.com", "newPassword123");

        doThrow(InvalidTokenException.class).when(emailUtils).checkValidity(request.getToken());

        // Vérification de l'exception
        assertThrows(InvalidTokenException.class, () -> customAuthenticationManager.resetPassword(request));
        verify(adminRepository, never()).save(any(Admin.class)); // Vérifie que save() n'est jamais appelé
    }

    @Test
    void testResetPassword_AdminNotFound() {
        // Mock des données
        ResetPasswordRequest request = new ResetPasswordRequest("validToken", "currentPassword", "email@example.com", "newPassword123");

        when(emailUtils.checkValidity(request.getToken())).thenReturn(String.valueOf(true));
        when(adminRepository.findByTokenEmail(request.getToken())).thenReturn(Optional.empty()); // Aucun admin trouvé

        // Vérification de l'exception
        assertThrows(InvalidTokenException.class, () -> customAuthenticationManager.resetPassword(request));
    }
}
