package com.bookmyshow.bms.RequestDto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequestDto {
    private UUID userId;
    private UUID showId;
    private List<UUID> seatIds;
}
