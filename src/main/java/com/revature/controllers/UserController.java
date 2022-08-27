package com.revature.controllers;

import com.revature.dtos.UserDTO;
import com.revature.dtos.UserDTO2;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/users")
public class UserController {

    private ModelMapper modelMapper = new ModelMapper();
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") int id) {
        User user;

        // Try to find the user with the given id in the database
        try {
            user = userService.getUserById(id);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> findUserByEmail(@PathVariable("email") String email) {
        User user;

        // Try to find the user with the given email in the database
        try {
            user = userService.getUserByEmail(email);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return ResponseEntity.ok(userService.addUser(user));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @Valid @RequestBody UserDTO userDTO) {
        User newUser = modelMapper.map(userDTO, User.class);
        User oldUser = userService.getUserById(id);
        newUser.setId(id);
        newUser.setWins(oldUser.getWins());
        newUser.setLosses(oldUser.getLosses());
        newUser.setNetProfits(oldUser.getNetProfits());
        newUser.setReferralCode(oldUser.getReferralCode());
        return ResponseEntity.ok(userService.updateUser(newUser));
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity<User> updateStats(@PathVariable("id") int id, @Valid @RequestBody UserDTO2 userDTO) {
        User newUser = modelMapper.map(userDTO, User.class);
        User oldUser = userService.getUserById(id);
        double netProfit = newUser.getBalance() - oldUser.getBalance();
        return ResponseEntity.ok(userService.updateStats(id, netProfit));
    }

}
