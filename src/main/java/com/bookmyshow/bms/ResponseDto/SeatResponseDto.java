package com.bookmyshow.bms.ResponseDto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatResponseDto {
    private UUID seatId;
    private String seatNo;
    private int price;
    private boolean isBooked; // Critical for the UI to know what color to render the chair!
}
