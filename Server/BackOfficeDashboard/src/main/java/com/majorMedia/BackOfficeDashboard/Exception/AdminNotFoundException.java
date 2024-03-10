package com.majorMedia.BackOfficeDashboard.Exception;

public class AdminNotFoundException extends RuntimeException{
    public AdminNotFoundException(){
        super("No admin found with the given token.");
    }
}
