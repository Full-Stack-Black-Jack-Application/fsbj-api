package com.revature.dtos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.revature.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class GameTo {

    // Fields
    private long id;

    private @NonNull User user;

    private int userScore;

    private int dealerScore;

    private Double balanceBefore;

    private Double balanceAfter;

    private Double netProfit;

    private long timeOfPlay;

    // ID-less Constructor
    public GameTo(User user, int userScore, int dealerScore, Double balanceBefore, Double balanceAfter,
            Double netProfit,
            long timeOfPlay) {
        this.user = user;
        this.userScore = userScore;
        this.dealerScore = dealerScore;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
        this.netProfit = netProfit;
        this.timeOfPlay = timeOfPlay;
    }

}
