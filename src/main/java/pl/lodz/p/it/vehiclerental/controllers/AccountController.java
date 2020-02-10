package pl.lodz.p.it.vehiclerental.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.vehiclerental.model.Account;
import pl.lodz.p.it.vehiclerental.repositories.AccountRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/api/account")
    @Transactional
    public void addAccount(@RequestBody Account account) {
        accountRepository.insert(account);
    }

    @GetMapping("/api/account/{login}")
    @Transactional
    public Optional<Account> getAccount(@PathVariable String login) {
        return accountRepository.findById(login);
    }

    @GetMapping("/api/accounts")
    @Transactional
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @PutMapping("/api/account/{login}")
    @Transactional
    public void updateAccount(@PathVariable String login, @RequestBody Account account) {
        accountRepository.save(account);
    }

    @DeleteMapping("/api/account/{login}")
    @Transactional
    public void deleteAccount(@PathVariable String login) {
        accountRepository.deleteById(login);
    }
}
