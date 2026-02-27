package com.bookmyshow.bms.ResponseDto;

import com.bookmyshow.bms.Enums.UserType;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    
    private int id;
    private String username;
    private String email;
    private Long phoneNumber;
    private String address;
    private String city;
    private String pincode;
    private UserType userType;
}
