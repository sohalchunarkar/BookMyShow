package com.bookmyshow.bms.RequestDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowRequestDto {
    private UUID ownerId;    // To verify ownership
    private UUID hallId;     // The hall where the show is played
    private UUID movieId;    // The movie to be played
    private LocalDate showDate;
    private LocalTime startTime;
    private int basePrice;
}
