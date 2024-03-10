package com.majorMedia.BackOfficeDashboard.Exception;

public class EmailServiceException extends  RuntimeException{
    public EmailServiceException(){
        super("Failed to send password reset email. Please try again later.");
    }
}
