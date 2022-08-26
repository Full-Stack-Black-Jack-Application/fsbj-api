package com.revature.controllers;

import com.revature.dtos.UserDTO;
import com.revature.models.User;
import com.revature.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/{email}")
    public ResponseEntity<User> findUserByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") int id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody UserDTO userDTO){
        User user = modelMapper.map(userDTO, User.class);
        return ResponseEntity.ok(userService.addUser(user));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") int id){
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @Valid @RequestBody UserDTO userDTO){
        User newUser = modelMapper.map(userDTO, User.class);
        User oldUser = userService.getUserById(id);
        newUser.setId(id);
        newUser.setWins(oldUser.getWins());
        newUser.setLosses(oldUser.getLosses());
        newUser.setNetProfits(oldUser.getNetProfits());
        return ResponseEntity.ok(userService.updateUser(newUser));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateStats(@PathVariable("id") int id, @Valid @RequestBody UserDTO userDTO){
        User newUser = modelMapper.map(userDTO, User.class);
        User oldUser = userService.getUserById(id);
        double netProfit = newUser.getBalance() - oldUser.getBalance();
        return ResponseEntity.ok(userService.updateStats(id, netProfit));
    }

}
