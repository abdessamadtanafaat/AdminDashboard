package com.majorMedia.BackOfficeDashboard.exception;

public class SuperAdminRoleAssignmentException extends RuntimeException{
    public SuperAdminRoleAssignmentException(){super("Cannot assign role to a super admin");}
}
