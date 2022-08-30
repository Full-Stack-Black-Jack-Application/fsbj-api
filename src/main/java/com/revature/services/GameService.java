package com.revature.services;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.Game;
import com.revature.exceptions.GameNotFoundException;
import com.revature.repositories.GameRepository;

@Service
public class GameService {

    private GameRepository gameRepo;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public GameService(GameRepository gameRepo) {
        this.gameRepo = gameRepo;
    }

    @Transactional(readOnly = true)
    public Game getGameById(long id) {
        if (id <= 0) {
            log.warn("Invalid ID, ID cannot be less than 0. ID passed in was: {}", id);
            return null;
        } else {
            return gameRepo.findById(id).orElseThrow(() -> new GameNotFoundException("No Game Found with ID: " + id));
        }
    }

    @Transactional(readOnly = true)
    public Collection<Game> getGamesByUserId(int userId) {
        log.info("All the games played by the user with Id {} have been retrieved! ", userId);
        return gameRepo.findByUserID(userId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Game addGame(Game game) {
        log.info("New game has been successfully created! ");
        return gameRepo.save(game);
    }
}
