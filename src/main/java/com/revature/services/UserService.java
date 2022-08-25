package com.revature.services;

import com.revature.dtos.Credentials;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepo;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User getByCredentials(Credentials creds){

        Optional<User> userInDb = userRepo.findByEmailAndPassword(creds.getEmail(), creds.getPswd());

        if (userInDb.isPresent()) {
            log.info("Found user with email {}", creds.getEmail());
            return userInDb.get();
        } else {
            log.warn("Email and password combination did not match for user {}", creds.getEmail());
            return null;
        }
    }

    @Transactional (readOnly = true)
    public List<User> getAll(){
        return userRepo.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User addUser(User user){
        return userRepo.save(user);
    }

    @Transactional
    public void deleteUser(int id){
        userRepo.deleteById(id);
    }

    @Transactional
    public User updateUser(User user){
        return userRepo.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("No user found with email: " + email));
    }

    @Transactional(readOnly = true)
    public User getUserById(int id){
        if (id <= 0){
            log.warn("Invalid ID, ID cannot be less than 0. ID passed in was: {}", id);
            return null;
        } else {
            return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("No user found with ID: " + id));
        }
    }

    @Transactional
    public User updateStats(int id, double netProfit){
        if (netProfit < 0){
            userRepo.incrementLosses(id);
        } else {
            userRepo.incrementWins(id);
        }
        userRepo.addToNetProfits(id, netProfit);
        return userRepo.findById(id).get();
    }
}
