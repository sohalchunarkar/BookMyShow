package com.bookmyshow.bms.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hall")
public class Hall {
    @Id
    @GeneratedValue (strategy =  GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String hallname;
    private int hallcapacity;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "theater_id" , nullable = false)
    private Theater theater;
}
