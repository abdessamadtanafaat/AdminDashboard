package com.majorMedia.BackOfficeDashboard.Exception;

public class InvalidRoleException extends RuntimeException {
    public InvalidRoleException(String role){
        super("Role '" + role + "' not found");
    }
}
