package com.majorMedia.BackOfficeDashboard.authentification_module.service;

import com.majorMedia.BackOfficeDashboard.authentification_module.Exception.InvalidEmailException;
import com.majorMedia.BackOfficeDashboard.authentification_module.entity.User;
import com.majorMedia.BackOfficeDashboard.authentification_module.model.ForgotPasswordToken;
import com.majorMedia.BackOfficeDashboard.authentification_module.repository.ForgotPasswordRepository;
import com.majorMedia.BackOfficeDashboard.authentification_module.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {
    private final UserRepository userRepository;
    private final ForgotPasswordRepository forgotPasswordRepository;

    @Autowired
    JavaMailSender javaMailSender;

    private final int MINUTES = 10;

/*    public void sendEmail(String to, String subject, String emailLink
    ) throws MessagingException,
             UnsupportedEncodingException{
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String emailContent = "<p>Hello</p>"
                                + "Click the link the below to reset password"
                                + "<p><a href=\""+ emailLink + "\">Change My Password</a></p>"
                                + "<br>"
                                + "Ignore this Email if you did not made the request";
        helper.setText(emailContent, true);
        helper.setFrom("tanafaat.rca.16@gmail.com", "TANAFAAT SENDING MESSAGE");
        helper.setSubject(subject);
        helper.setTo(to);
        javaMailSender.send(message);

    }*/
    public String forgotPassword (String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidEmailException("Email is not found."));

        ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
        forgotPasswordToken.setExpreTime(expireTimeRange());
        forgotPasswordToken.setToken(generateToken());
        forgotPasswordToken.setUser(user);
        forgotPasswordToken.setUsed(false);

        forgotPasswordRepository.save(forgotPasswordToken);

        String emailLink = "http://localhost:3000/reset-password?token=" + forgotPasswordToken.getToken();
        try {
            sendEmail(user.getUsername(), "Password Reset Link", emailLink);

        }catch (UnsupportedEncodingException | MessagingException e){
            e.printStackTrace(); // You can log the error
        }

        //return "redirect:/password-request?success";
        //return forgotPasswordToken.getToken();
        return emailLink;
    }

    private void sendEmail(String to, String subject, String emailLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String emailContent = "<p>Hello</p>"
                + "Click the link below to reset your password:<br>"
                + "<a href=\"" + emailLink + "\">Reset Password</a>"
                + "<br><br>"
                + "Ignore this email if you did not request a password reset.";

        helper.setText(emailContent, true);
        helper.setFrom("tvta3ichannel@gmail.com", "SmtpSatisnap");
        helper.setSubject(subject);
        helper.setTo(to);

        javaMailSender.send(message);
    }


    public String generateToken(){
        return UUID.randomUUID().toString();
    }
    public LocalDateTime expireTimeRange(){
        return LocalDateTime.now().plusMinutes(MINUTES);
    }
    public boolean isExpired (ForgotPasswordToken forgotPasswordToken, Model model)
    {
            return LocalDateTime.now().isAfter(forgotPasswordToken.getExpreTime());
    }
    public String chekValidity (ForgotPasswordToken forgotPasswordToken, Model model)
    {
            if(forgotPasswordToken == null)
            {
                model.addAttribute("error", "Invalid Token");
                return "error-page";

            }
            else if(forgotPasswordToken.isUsed())
            {
                model.addAttribute("error", "the token is already used");
                return "error-page";
            }
            else{
                return "reset-password";
            }
        }
}
