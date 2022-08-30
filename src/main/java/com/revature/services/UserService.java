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
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepo;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User getUserByCredentials(Credentials creds) {
        Optional<User> userInDb = userRepo.findByEmail(creds.getEmail());

        if (!userInDb.isPresent()) {
            log.warn("No user with email \"{}\" was found in the database", creds.getEmail());
            return null;
        }

        userInDb = userRepo.findByEmailAndPswd(creds.getEmail(), creds.getPswd());

        if (userInDb.isPresent()) {
            log.info("Found user with email \"{}\"", creds.getEmail());
            return userInDb.get();
        } else {
            log.warn("Email and password combination did not match for user with email \"{}\"", creds.getEmail());
            return null;
        }

    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User addUser(User newUser) {
        String referralCode = newUser.getReferralCode();
        User oldUser = userRepo.findByReferralCode(referralCode).orElse(null);

        if (oldUser != null) {
            newUser.setBalance(200.0);
            userRepo.incrementBalance(oldUser.getId(), 200.0);
            log.info("User with Referral Code \"{}\" has been found! \nBalances have been increased by $200!",
                    referralCode);
        } else {
            log.info("No user with Referral Code \"{}\" has been found! ", referralCode);
        }

        log.info("New user has been successfully created! ");
        String uuidString = UUID.randomUUID().toString().substring(9, 23);
        newUser.setReferralCode(uuidString);

        return userRepo.save(newUser);
    }

    @Transactional
    public void deleteUser(int id) {
        userRepo.deleteById(id);
        log.info("User has been successfully deleted! ");
    }

    @Transactional
    public User updateUser(User user) {
        log.info("User has been successfully updated! ");
        return userRepo.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserById(int id) {
        if (id <= 0) {
            log.warn("Invalid ID, ID cannot be less than 0. ID passed in was: {}", id);
            return null;
        } else {
            return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("No user found with ID: " + id));
        }
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("No user found with email: " + email));
    }

    @Transactional
    public User updateStats(int id, double netProfit) {

        // Increment wins and losses based on the passed-in netProfit
        if (netProfit < 0) {
            userRepo.incrementLosses(id);
        } else {
            userRepo.incrementWins(id);
        }
        // Add the netProfit to both the netProfits and Balance Fields
        userRepo.addToNetProfits(id, netProfit);
        userRepo.incrementBalance(id, netProfit);

        // Log that user's stats have been updated
        log.info("The stats for user with id {} have been successfully updated! ", id);

        // Get the user with the passed-in ID
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("For Update: No user found with ID: " + id));

        // Below Code is for the User that will be outputed
        if (netProfit < 0) {
            user.setLosses(user.getLosses() + 1);
        } else {
            user.setWins(user.getWins() + 1);
        }
        user.setBalance(user.getBalance() + netProfit);
        user.setNetProfits(user.getNetProfits() + netProfit);
        return user;
    }
}
