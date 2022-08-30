package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "games")
@Table(name = "games")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Game {

    // Fields
    @Id
    @Column(name = "game_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties({ "hibernateLazyInitializer" })
    private User user;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Integer userId;

    private int userScore;

    private int dealerScore;

    private Double balanceBefore;

    private Double balanceAfter;

    private Double netProfit;

    private long timeOfPlay;

    // ID-less Constructor
    public Game(User user, int userScore, int dealerScore, Double balanceBefore, Double balanceAfter,
            Double netProfit, long timeOfPlay) {
        this.user = user;
        this.userScore = userScore;
        this.dealerScore = dealerScore;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
        this.netProfit = netProfit;
        this.timeOfPlay = timeOfPlay;
    }

}
