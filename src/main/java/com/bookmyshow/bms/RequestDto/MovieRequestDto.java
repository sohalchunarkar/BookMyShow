package com.bookmyshow.bms.RequestDto;

import java.util.UUID;

import com.bookmyshow.bms.Enums.MoiveGener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MovieRequestDto {
    private String Moviename;
    private MoiveGener gener;
    private String Description;
    private Integer MovieDuration;
    private UUID idofAdminWhoAddedThisMove;

}
