package com.majorMedia.BackOfficeDashboard.exception;

public class AlreadyExistsEntityException extends RuntimeException
{
    public AlreadyExistsEntityException(String name , Class<?> entity){

      super(" The " + entity.getSimpleName().toLowerCase() + " with name " +name+ " is Already Exists  ");
    }
}
