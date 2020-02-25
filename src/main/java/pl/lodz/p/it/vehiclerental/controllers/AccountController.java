package pl.lodz.p.it.vehiclerental.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.vehiclerental.model.Account;
import pl.lodz.p.it.vehiclerental.repositories.AccountRepository;

import java.util.List;

@RestController
public class AccountController {

    private AccountRepository accountRepo;

    @Autowired
    public AccountController(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @PostMapping("/api/account")
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
            accountRepo.insert(account);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("User with login: " + account.getLogin() + " added successfully.");
        }
    }

    @GetMapping("/api/account/{login}")
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

    @GetMapping("/api/accounts")
    @Transactional
    public ResponseEntity<List<Account>> getAccounts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountRepo.findAll());
    }

    @PutMapping("/api/account/{login}")
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

    @DeleteMapping("/api/account/{login}")
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

    @GetMapping("/api/accounts/{filter}")
    @Transactional
    public ResponseEntity<List<Account>> filterAccounts(@PathVariable String filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountRepo.findAllByLoginContainsIgnoreCaseOrEmailContainsIgnoreCaseOrFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCase(filter, filter, filter, filter));
    }
}
