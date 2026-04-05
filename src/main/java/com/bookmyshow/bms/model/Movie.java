package com.bookmyshow.bms.model;

import java.util.UUID;

import com.bookmyshow.bms.Enums.MoiveGener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String Moviename;
    @Enumerated(value = EnumType.STRING)
    private MoiveGener gener;
    private String Description;
    private UUID idofAdminWhoAddedThisMove;
    private Integer MovieDuration;

}
