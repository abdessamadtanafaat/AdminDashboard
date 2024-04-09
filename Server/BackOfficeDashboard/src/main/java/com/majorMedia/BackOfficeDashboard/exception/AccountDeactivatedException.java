package com.majorMedia.BackOfficeDashboard.exception;

public class AccountDeactivatedException extends RuntimeException {

    public AccountDeactivatedException (){ super("Your account has been deactivated, call the administration");}

}
