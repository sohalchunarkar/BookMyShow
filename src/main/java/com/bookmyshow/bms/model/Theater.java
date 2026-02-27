package com.bookmyshow.bms.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="Theater")
public class Theater {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true , nullable = false)
    private String theaterName;
    private String city;
    private String state;
    private int pincode;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "owner_id" , nullable =  false)
    private User owner;

    @JsonManagedReference
    @OneToMany(mappedBy = "theater")
    private List<Hall> halls;

} 
