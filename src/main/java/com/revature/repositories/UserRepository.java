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
    @Query("UPDATE users u SET u.netProfits = u.netProfits + :addend WHERE u.id = :userId")
    // @Query("UPDATE users SET net_profits = net_profits + :addend WHERE userId =
    // :userId")
    int addToNetProfits(@Param("userId") int userId, @Param("addend") double addend);

    @Modifying
    @Query("UPDATE users u SET u.wins = u.wins + 1 WHERE u.id = :userId")
    // @Query("UPDATE users SET net_profits = net_profits + :addend WHERE userId =
    // :userId")
    int incrementWins(@Param("userId") int userId);

    @Modifying
    @Query("UPDATE users u SET u.losses = u.losses + 1 WHERE u.id = :userId")
    // @Query("UPDATE users SET net_profits = net_profits + :addend WHERE userId =
    // :userId")
    int incrementLosses(@Param("userId") int userId);

    @Modifying
    @Query("UPDATE users u SET u.balance = u.balance + :addend WHERE u.id = :userId")
    // @Query("UPDATE users SET net_profits = net_profits + :addend WHERE userId =
    // :userId")
    int incrementBalance(@Param("userId") int userId, @Param("addend") double addend);

    @Modifying
    @Query("UPDATE users u SET u.balance = u.balance - :subtrahend WHERE u.id = :userId")
    // @Query("UPDATE users SET net_profits = net_profits + :addend WHERE userId =
    // :userId")
    int decrementBalance(@Param("userId") int userId, @Param("subtrahend") double subtrahend);

}
