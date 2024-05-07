package com.majorMedia.BackOfficeDashboard.util;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.exception.InvalidTokenException;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

import static com.majorMedia.BackOfficeDashboard.security.SecurityConstants.TOKEN_EXPIRATION_EMAIL;
import static com.majorMedia.BackOfficeDashboard.security.SecurityConstants.TOKEN_LENGTH;

@Component
@AllArgsConstructor
public class EmailUtils {
    private final AdminRepository adminRepository ;
    private final JavaMailSender javaMailSender;
    private static final SecureRandom secureRandom = new SecureRandom();
    public void sendEmail(String to, String emailLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String emailContent = "<p>Hello</p>"
                + "Click the link below to reset your password:<br>"
                + "<a href=\"" + emailLink + "\">Reset Password</a>"
                + "<br><br>"
                + "Ignore this email if you did not request a password reset.";

        helper.setText(emailContent, true);
        helper.setFrom("tvta3ichannel@gmail.com", "SmtpSatisnap");
        helper.setSubject("Password Reset Link");
        helper.setTo(to);

        javaMailSender.send(message);
    }
    public void sendEmailActivation(String to, String newPassword) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String emailContent = "<div style=\"font-family: Arial, sans-serif;\">" +
                "<h3>Hello,</h3>" +
                "<p>Your password has been reset successfully. Below is your new password:</p>" +
                "<p style=\"background-color: #f0f0f0; padding: 10px; border-radius: 5px;\">" + newPassword + "</p>" +
                "<p>Your account has been reactivated. You can now use this password to log in again.</p>" +
                "<p>If you did not request a password reset or have any concerns, please contact us immediately.</p>" +
                "<p>Best regards,<br>The Team</p>" +
                "</div>";

        helper.setText(emailContent, true);
        helper.setFrom("tvta3ichannel@gmail.com", "SmtpSatisnap");
        helper.setSubject("Password Reset and Account Activation");
        helper.setTo(to);

        javaMailSender.send(message);
    }


    public static String generateToken() {
        byte[] tokenBytes = new byte[TOKEN_LENGTH];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
    public static LocalDateTime expireTimeRange() {
        return LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_EMAIL);
    }
    public boolean isExpiredTokenEmail(String token) {
        Admin admin = adminRepository.findByTokenEmail(token)
                .orElseThrow(InvalidTokenException::new);

        return LocalDateTime.now().isAfter(admin.getExpiredTimeEmail());
    }
    public String checkValidity(String token) {
        Admin admin = adminRepository.findByTokenEmail(token)
                .orElseThrow(InvalidTokenException::new);

        if (isExpiredTokenEmail(token)) {
            throw new InvalidTokenException();
        }
        if (admin.isUsedTokenEmail()) {
            throw new InvalidTokenException();
        }
        return "The token is valid";

    }
}
