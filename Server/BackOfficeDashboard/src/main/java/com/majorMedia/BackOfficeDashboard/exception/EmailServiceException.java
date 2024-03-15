package com.majorMedia.BackOfficeDashboard.exception;

public class EmailServiceException extends  RuntimeException{
    public EmailServiceException(){
        super("Failed to send password reset email. Please try again later.");
    }
}
