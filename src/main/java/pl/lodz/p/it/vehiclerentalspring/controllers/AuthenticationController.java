package pl.lodz.p.it.vehiclerentalspring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.vehiclerentalspring.model.AuthenticationRequest;
import pl.lodz.p.it.vehiclerentalspring.model.AuthenticationResponse;
import pl.lodz.p.it.vehiclerentalspring.services.JwtService;
import pl.lodz.p.it.vehiclerentalspring.services.MongoUserDetailsService;
import pl.lodz.p.it.vehiclerentalspring.services.interfaces.AccountService;

@CrossOrigin
@RestController
public class AuthenticationController {

    private AuthenticationManager authManager;
    private MongoUserDetailsService userDetailsService;
    private JwtService jwtService;
    private AccountService accountService;

    @Autowired
    public AuthenticationController(AuthenticationManager authManager, MongoUserDetailsService userDetailsService, JwtService jwtService, AccountService accountService) {
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.accountService = accountService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authRequest) throws Exception {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Incorrect credentials.");
        }
        if (!accountService.getAccountByUsernameOrEmail(authRequest.getUsername()).isActive()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("This account is inactive.");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtService.generateToken(userDetails);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new AuthenticationResponse(jwt));
    }
}
