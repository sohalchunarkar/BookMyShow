package com.bookmyshow.bms.RequestDto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TheaterRequestDto {

    private String theaterName;
    private String city;
    private String state;
    private int pincode;
    private UUID ownerid;
}
