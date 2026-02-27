package com.bookmyshow.bms.Exception;

public class UserIsNotOwnerException extends RuntimeException {
   public UserIsNotOwnerException(String message){
    super(message);
   }
}