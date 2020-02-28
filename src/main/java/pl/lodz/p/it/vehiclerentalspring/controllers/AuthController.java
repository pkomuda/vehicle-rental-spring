package pl.lodz.p.it.vehiclerentalspring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.vehiclerentalspring.model.Account;
import pl.lodz.p.it.vehiclerentalspring.model.AuthRequest;
import pl.lodz.p.it.vehiclerentalspring.model.AuthResponse;
import pl.lodz.p.it.vehiclerentalspring.repositories.AccountRepository;
import pl.lodz.p.it.vehiclerentalspring.services.JwtService;
import pl.lodz.p.it.vehiclerentalspring.services.MongoUserDetailsService;

import java.util.Arrays;

@CrossOrigin
@RestController
public class AuthController {

    private AuthenticationManager authManager;
    private MongoUserDetailsService userDetailsService;
    private JwtService jwtService;
    private AccountRepository accountRepo;
    private PasswordEncoder bCrypt;

    @Autowired
    public AuthController(AuthenticationManager authManager, MongoUserDetailsService userDetailsService, JwtService jwtService, AccountRepository accountRepo, PasswordEncoder bCrypt) {
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.accountRepo = accountRepo;
        this.bCrypt = bCrypt;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Incorrect credentials.");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getLogin());
        final String jwt = jwtService.generateToken(userDetails);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new AuthResponse(jwt));
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<String> register(@RequestBody Account account) {
        if (accountRepo.findById(account.getLogin()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User with login: " + account.getLogin() + " already exists.");
        } else if (accountRepo.findByEmail(account.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User with email: " + account.getEmail() + " already exists.");
        } else {
            if (!Arrays.equals(account.getPermissions(), new String[]{"CLIENT"})) {
                account.setPermissions(new String[]{"CLIENT"});
            }
            account.setPassword(bCrypt.encode(account.getPassword()));
            accountRepo.insert(account);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Registered successfully.");
        }
    }
}
