package com.bookmyshow.bms.RequestDto;

import com.bookmyshow.bms.Enums.UserType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    private String username;
    private String password;
    private String email;
    private Long phoneNumber;
    private String address;
    private String city;
    private String pincode;
    private UserType userType;

}
