package com.majorMedia.BackOfficeDashboard.exception;

public class EntityNotFoundException extends  RuntimeException{
    public EntityNotFoundException(Integer id, Class<?> entity) {
        super(" The " + entity.getSimpleName().toLowerCase() + " with id '" + id + "' does not exist in our records");
    }
    public EntityNotFoundException(Class<?> entity) {
        super(" The " + entity.getSimpleName().toLowerCase() + " entity is not found ");
    }
    public EntityNotFoundException(String searchKey, Class<?> entity) {
        super(" The " + entity.getSimpleName().toLowerCase() + " with key: " + searchKey +" does not exist in our records ");
    }
}
