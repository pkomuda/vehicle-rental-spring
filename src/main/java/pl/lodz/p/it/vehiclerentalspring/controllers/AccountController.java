package pl.lodz.p.it.vehiclerental.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.vehiclerental.model.Account;
import pl.lodz.p.it.vehiclerental.repositories.AccountRepository;

import java.util.List;

@CrossOrigin
@RestController
public class AccountController {

    private AccountRepository accountRepo;
    private PasswordEncoder bCrypt;

    @Autowired
    public AccountController(AccountRepository accountRepo, PasswordEncoder bCrypt) {
        this.accountRepo = accountRepo;
        this.bCrypt = bCrypt;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/account")
    @Transactional
    public ResponseEntity<String> addAccount(@RequestBody Account account) {
        if (accountRepo.findById(account.getLogin()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User with login: " + account.getLogin() + " already exists.");
        } else if (accountRepo.findByEmail(account.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User with email: " + account.getEmail() + " already exists.");
        } else {
            account.setPassword(bCrypt.encode(account.getPassword()));
            accountRepo.insert(account);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("User with login: " + account.getLogin() + " added successfully.");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or #login == authentication.principal.username")
    @GetMapping("/account/{login}")
    @Transactional
    public ResponseEntity<?> getAccount(@PathVariable String login) {
        if (accountRepo.findById(login).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(accountRepo.findById(login));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User with login: " + login + " not found.");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/accounts")
    @Transactional
    public ResponseEntity<List<Account>> getAccounts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountRepo.findAll());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/account/{login}")
    @Transactional
    public ResponseEntity<String> updateAccount(@PathVariable String login, @RequestBody Account account) {
        if (accountRepo.findById(login).isPresent()) {
            if (!login.equals(account.getLogin()) && accountRepo.findById(account.getLogin()).isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("User with login: " + account.getLogin() + " already exists.");
            } else if (accountRepo.findByEmail(account.getEmail()).isPresent()
                    && !accountRepo.findById(login).get().getEmail().equals(account.getEmail())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("User with email: " + account.getEmail() + " already exists.");
            } else {
                if (!login.equals(account.getLogin())) {
                    accountRepo.deleteById(login);
                }
                account.setPassword(bCrypt.encode(account.getPassword()));
                accountRepo.save(account);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("User with login: " + login + " updated successfully.");
            }
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User with login: " + login + " not found.");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or #login == authentication.principal.username")
    @DeleteMapping("/account/{login}")
    @Transactional
    public ResponseEntity<String> deleteAccount(@PathVariable String login) {
        if (accountRepo.findById(login).isPresent()) {
            accountRepo.deleteById(login);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("User with login: " + login + " deleted successfully.");
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User with login: " + login + " not found.");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/accounts/{filter}")
    @Transactional
    public ResponseEntity<List<Account>> filterAccounts(@PathVariable String filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountRepo.findAllByLoginContainsIgnoreCaseOrEmailContainsIgnoreCaseOrFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCase(filter, filter, filter, filter));
    }
}
