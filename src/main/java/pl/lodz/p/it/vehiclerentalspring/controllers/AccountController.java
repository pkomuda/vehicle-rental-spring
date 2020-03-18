package pl.lodz.p.it.vehiclerentalspring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.vehiclerentalspring.model.Account;
import pl.lodz.p.it.vehiclerentalspring.services.interfaces.AccountService;

import java.util.List;

@CrossOrigin
@RestController
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/account")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addAccount(@RequestBody Account account) throws Exception {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountService.addAccount(account, true));
    }

    @PostMapping("/register")
    public ResponseEntity<String> addAccountNonAdmin(@RequestBody Account account) throws Exception {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountService.addAccount(account, false));
    }

    @GetMapping("/account/{username}")
    @PreAuthorize("hasAuthority('ADMIN') or #username == authentication.principal.username")
    public ResponseEntity<?> getAccount(@PathVariable String username) throws Exception {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountService.getAccount(username, false));
    }

    @PutMapping("/account/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> updateAccount(@PathVariable String username, @RequestBody Account account) throws Exception {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountService.updateAccount(username, account, true));
    }

    @PutMapping("/editprofile/{username}")
    @PreAuthorize("#username == authentication.principal.username")
    public ResponseEntity<String> updateAccountNonAdmin(@PathVariable String username, @RequestBody Account account) throws Exception {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountService.updateAccount(username, account, false));
    }

    @DeleteMapping("/account/{username}")
    @PreAuthorize("hasAuthority('ADMIN') or #username == authentication.principal.username")
    public ResponseEntity<String> deleteAccount(@PathVariable String username) throws Exception {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountService.deleteAccount(username));
    }

    @GetMapping("/accounts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Account>> getAccounts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountService.getAccounts());
    }

    @GetMapping("/accounts/{filter}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Account>> filterAccounts(@PathVariable String filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountService.filterAccounts(filter));
    }
}
