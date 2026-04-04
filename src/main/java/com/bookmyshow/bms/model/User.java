package com.bookmyshow.bms.model;

import java.util.List;
import java.util.UUID;

import com.bookmyshow.bms.Enums.UserType;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String username;
    private String password;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private Long phoneNumber;
    private String address;
    private String city;
    private String pincode;
    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    @JsonManagedReference
    @OneToMany(mappedBy = "owner")
    private List<Theater> theaters;
}
