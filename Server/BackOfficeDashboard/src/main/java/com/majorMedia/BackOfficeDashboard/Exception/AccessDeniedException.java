package com.majorMedia.BackOfficeDashboard.Exception;

public class AccessDeniedException extends RuntimeException{
    public AccessDeniedException (String email){
        super(email+" does not have the necessary permissions");
    }
}
