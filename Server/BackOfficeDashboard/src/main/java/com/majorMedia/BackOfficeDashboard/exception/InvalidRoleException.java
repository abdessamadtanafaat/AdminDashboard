package com.majorMedia.BackOfficeDashboard.exception;

public class InvalidRoleException extends RuntimeException {
    public InvalidRoleException(String role){
        super("Role '" + role + "' not found");
    }
}
