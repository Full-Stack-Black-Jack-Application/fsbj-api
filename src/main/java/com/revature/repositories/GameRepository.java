package com.revature.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.models.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    // .save(), .findAll(), .update(), .deleteById() are already created

    Optional<Game> findById(long gameId);

    @Query("SELECT g FROM games g WHERE g.userId = :userId")
    List<Game> findByUserID(@Param("userId") int userId);

}
