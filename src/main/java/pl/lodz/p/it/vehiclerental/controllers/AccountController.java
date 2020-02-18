package pl.lodz.p.it.vehiclerental.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.vehiclerental.StringUtils;
import pl.lodz.p.it.vehiclerental.model.Account;
import pl.lodz.p.it.vehiclerental.repositories.AccountRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/api/account")
    @Transactional
    public ResponseEntity<String> addAccount(@RequestBody Account account) {
        if (accountRepository.findById(account.getLogin()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User with login: " + account.getLogin() + " already exists.");
        } else if (accountRepository.findByEmail(account.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User with email: " + account.getEmail() + " already exists.");
        } else {
            accountRepository.insert(account);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("User with login: " + account.getLogin() + " added successfully.");
        }
    }

    @GetMapping("/api/account/{login}")
    @Transactional
    public ResponseEntity<?> getAccount(@PathVariable String login) {
        if (accountRepository.findById(login).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(accountRepository.findById(login));
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
                .body(accountRepository.findAll());
    }

    @PutMapping("/api/account/{login}")
    @Transactional
    public ResponseEntity<String> updateAccount(@PathVariable String login, @RequestBody Account account) {
        if (accountRepository.findById(login).isPresent()) {
            if (!accountRepository.findById(login).get().getLogin().equals(account.getLogin())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Cannot change user login.");
            } else if (!accountRepository.findById(login).get().getEmail().equals(account.getEmail())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Cannot change user email.");
            } else {
                accountRepository.save(account);
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
        if (accountRepository.findById(login).isPresent()) {
            accountRepository.deleteById(login);
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
                .body(accountRepository
                        .findAll()
                        .stream()
                        .filter(account -> StringUtils.containsIgnoreCase(account.getLogin(), filter)
                        || StringUtils.containsIgnoreCase(account.getEmail(), filter)
                        || StringUtils.containsIgnoreCase(account.getFirstName(), filter)
                        || StringUtils.containsIgnoreCase(account.getLastName(), filter))
                        .collect(Collectors.toList()));
    }
}
