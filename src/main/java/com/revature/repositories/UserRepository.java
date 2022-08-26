package com.revature.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // .save(), .findAll(), .update(), .deleteById() are already created

    Optional<User> findByEmailAndPswd(String email, String pswd);

    Optional<User> findByReferralCode(String referralCode);

    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE users u SET u.netProfits = u.netProfits + :addend WHERE u.id = :user_id")
    // @Query("UPDATE users SET net_profits = net_profits + :addend WHERE user_id =
    // :user_id")
    int addToNetProfits(@Param("user_id") int user_id, @Param("addend") double addend);

    @Modifying
    @Query("UPDATE users u SET u.wins = u.wins + 1 WHERE u.id = :user_id")
    // @Query("UPDATE users SET net_profits = net_profits + :addend WHERE user_id =
    // :user_id")
    int incrementWins(@Param("user_id") int user_id);

    @Modifying
    @Query("UPDATE users u SET u.losses = u.losses + 1 WHERE u.id = :user_id")
    // @Query("UPDATE users SET net_profits = net_profits + :addend WHERE user_id =
    // :user_id")
    int incrementLosses(@Param("user_id") int user_id);

    @Modifying
    @Query("UPDATE users u SET u.balance = u.balance + :addend WHERE u.id = :user_id")
    // @Query("UPDATE users SET net_profits = net_profits + :addend WHERE user_id =
    // :user_id")
    int incrementBalance(@Param("user_id") int user_id, @Param("addend") double addend);

    @Modifying
    @Query("UPDATE users u SET u.balance = u.balance - :subtrahend WHERE u.id = :user_id")
    // @Query("UPDATE users SET net_profits = net_profits + :addend WHERE user_id =
    // :user_id")
    int decrementBalance(@Param("user_id") int user_id, @Param("subtrahend") double subtrahend);

}
