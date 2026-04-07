package com.bookmyshow.bms.RequestDto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HallRequestDto {
    private String hallname;
    private int hallcapacity;
    private int Hallrows;
    private int seatsInEachRow;
    private UUID TheaterId;
    private UUID ownerId;
    private int baseSeatPrice;

}
