package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {

    // Fields
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int wins;

    private int losses;

    private Double balance;

    @Column(name = "net_profits")
    private Double netProfits;

    @NotBlank
    @Length(min = 2)
    private String firstName;

    @NotBlank
    @Length(min = 2)
    private String lastName;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")
    private String pswd;

    @NotBlank
    private String referralCode;

    // Id-less Constructor
    public User(int wins, int losses, Double balance, Double netProfits, String firstName, String lastName,
            String email, String pswd, String referralCode) {
        this.wins = wins;
        this.losses = losses;
        this.balance = balance;
        this.netProfits = netProfits;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pswd = pswd;
        this.referralCode = referralCode;
    }

}