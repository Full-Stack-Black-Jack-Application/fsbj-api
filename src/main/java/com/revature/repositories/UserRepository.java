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

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByReferralCode(String referralCode);

    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE users SET net_profits = net_profits + :addend WHERE user_id = :user_id")
    Optional<User> addToNetProfits(@Param("user_id") int user_id, @Param("addend") double addend);

    @Modifying
    @Query("UPDATE users SET wins = wins + 1 WHERE user_id = :user_id")
    Optional<User> incrementWins(@Param("user_id") int user_id);

    @Modifying
    @Query("UPDATE users SET losses = losses + 1 WHERE user_id = :user_id")
    Optional<User> incrementLosses(@Param("user_id") int user_id);
}
