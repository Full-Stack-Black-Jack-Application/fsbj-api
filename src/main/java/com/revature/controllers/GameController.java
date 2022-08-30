package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Game;
import com.revature.exceptions.GameNotFoundException;
import com.revature.services.GameService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/games")
public class GameController {

    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Game> findGameById(@PathVariable("id") long id) {
        Game game;

        // Try to find the game with the given id
        try {
            game = gameService.getGameById(id);
        } catch (GameNotFoundException e) {
            return new ResponseEntity<Game>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(game);
    }

    @GetMapping("/get_all/{user_id}")
    public ResponseEntity<List<Game>> findGamesByUserId(@PathVariable("user_id") int userId) {
        List<Game> games = new ArrayList<Game>(gameService.getGamesByUserId(userId));
        return ResponseEntity.ok(games);
    }

    @PostMapping
    public ResponseEntity<Game> addGame(@Valid @RequestBody Game game) {
        return ResponseEntity.ok(gameService.addGame(game));
    }

}
