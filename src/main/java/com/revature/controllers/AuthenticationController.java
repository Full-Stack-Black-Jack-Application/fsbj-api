package com.revature.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dtos.Credentials;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.utils.JWTManager;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/login") // http://localhost:5000/api/login
public class AuthenticationController {

    UserService userServ;
    JWTManager tokenManger;

    @Autowired
    public AuthenticationController(UserService userServ, JWTManager tokenManger) {
        this.userServ = userServ;
        this.tokenManger = tokenManger;
    }

    @PostMapping
    public User login(@RequestBody Credentials creds, HttpServletResponse response) {

        // Line below will throw an exception if the user doesn't exist
        User user = userServ.getUserByCredentials(creds);

        // If the user is in the DataBase...
        if (user != null) {

            // ...Grant them a Json Web Token - generate the token xxxxxx.yyyyyy.zzzzzz
            String token = tokenManger.issueToken(user);

            // Append the token to the header of the RESPONSE
            response.addHeader("full-stack-black-jack-token", token);

            // Indicate that the above response header should be...
            // ...made available to scripts running in the browser
            response.addHeader("Access-Control-Expose-Headers", "full-stack-black-jack-token");

            // Indicate a successful login
            response.setStatus(200);

            // Return the user as a JSON
            return user;

        }

        // Otherwise deny and send the 401 status

        // 401 is an UNAUTHORIZED status
        response.setStatus(401);

        return null;
    }

}
