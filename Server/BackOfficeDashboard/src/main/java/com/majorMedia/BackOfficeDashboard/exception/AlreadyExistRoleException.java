package com.majorMedia.BackOfficeDashboard.exception;

public class AlreadyExistRoleException extends RuntimeException {
    public AlreadyExistRoleException(String roleName){
        super(roleName + " already exists.");

    }

}
