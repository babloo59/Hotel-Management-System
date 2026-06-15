package com.Icwd.user.service.UserService.exceptions;

public class ResourseNotFoundException extends RuntimeException{

    // extra properties that you want to manage
    public ResourseNotFoundException(){
        super("Resource not found on server !!");
    }

    public ResourseNotFoundException(String message){
        super(message);
    }
}
