package com.nitish.quicktasks.restController;

import com.nitish.quicktasks.token.JwtTokenManager;
import com.nitish.quicktasks.token.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestEndPoints {

    @Autowired
    private JwtTokenManager jwtTokenManager;

    @GetMapping(path = "/token/{name}")
    public String generateToken(@PathVariable String name) {
        return jwtTokenManager.generateToken(new UserDetails(name));
    }
}
