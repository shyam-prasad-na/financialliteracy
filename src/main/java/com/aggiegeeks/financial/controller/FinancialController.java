package com.aggiegeeks.financial.controller;

import com.aggiegeeks.financial.pojo.Recommendation;
import com.aggiegeeks.financial.services.FinancialService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FinancialController {

    @Autowired
    private FinancialService financialService;

    @PostMapping("/recommendations")
    public ResponseEntity<Recommendation> getVideoRecommendations(@RequestParam String userId) throws JsonProcessingException {
        Recommendation recommendation = financialService.getRecommendations(userId);
        return new ResponseEntity<>(recommendation, HttpStatus.OK);
    }

    @PostMapping("/callback")
    public ResponseEntity<String> getVideoCallback(@RequestBody String body) throws JsonProcessingException {
        financialService.processCallback(body);
        return new ResponseEntity<>("Successfully processed callback", HttpStatus.ACCEPTED);
    }

    @PostMapping("/initialize")
    public ResponseEntity<String> initializeUser(@RequestBody String body) throws JsonProcessingException {
        financialService.initializeUser(body);
        return new ResponseEntity<>( "Successfully initialized User", HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String body) throws JsonProcessingException{
        boolean result =  financialService.login(body);
        if(result)
            return new ResponseEntity<>("Successfully logged in user",HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed Authentication", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/videos")
    public ResponseEntity<String> getRecommendations() {
        return new ResponseEntity<>("Hey Pravija.....Hey Sahiti", HttpStatus.OK);
    }
}
