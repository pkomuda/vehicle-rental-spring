package pl.lodz.p.it.vehiclerentalspring.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.vehiclerentalspring.exceptions.DuplicateItemException;
import pl.lodz.p.it.vehiclerentalspring.exceptions.NoSuchItemException;
import pl.lodz.p.it.vehiclerentalspring.model.Account;
import pl.lodz.p.it.vehiclerentalspring.repositories.AccountRepository;
import pl.lodz.p.it.vehiclerentalspring.services.interfaces.AccountService;

import java.util.Arrays;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepo;
    private PasswordEncoder bCrypt;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepo, PasswordEncoder bCrypt) {
        this.accountRepo = accountRepo;
        this.bCrypt = bCrypt;
    }

    @Override
    @Transactional
    public String addAccount(Account account, boolean isAdmin) throws DuplicateItemException {
        if (accountRepo.findById(account.getUsername()).isPresent()) {
            throw new DuplicateItemException("User " + account.getUsername() + " already exists.");
        } else if (accountRepo.findByEmail(account.getEmail()).isPresent()) {
            throw new DuplicateItemException("User with email: " + account.getEmail() + " already exists.");
        } else {
            if (account.getPassword() != null) {
                account.setPassword(bCrypt.encode(account.getPassword()));
            }
            if (!isAdmin) {
                if (!Arrays.equals(account.getPermissions(), new String[]{"CLIENT"})) {
                    account.setPermissions(new String[]{"CLIENT"});
                }
                accountRepo.insert(account);
                return "Registered successfully";
            } else {
                accountRepo.insert(account);
                return "User " + account.getUsername() + " added successfully.";
            }
        }
    }

    @Override
    @Transactional
    public Account getAccount(String username) throws NoSuchItemException {
        if (accountRepo.findById(username).isPresent()) {
            return accountRepo.findById(username).get();
        } else {
            throw new NoSuchItemException("User " + username + " not found.");
        }
    }

    @Override
    @Transactional
    public Account getAccountByUsernameOrEmail(String usernameOrEmail) throws NoSuchItemException {
        if (accountRepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).isPresent()) {
            return accountRepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).get();
        } else {
            throw new NoSuchItemException("User " + usernameOrEmail + " not found.");
        }
    }

    @Override
    @Transactional
    public String updateAccount(String username, Account account, boolean isAdmin) throws DuplicateItemException, NoSuchItemException {
        if (accountRepo.findById(username).isPresent()) {
            if (!username.equals(account.getUsername()) && accountRepo.findById(account.getUsername()).isPresent()) {
                throw new DuplicateItemException("User " + account.getUsername() + " already exists.");
            } else if (accountRepo.findByEmail(account.getEmail()).isPresent()
                    && !accountRepo.findById(username).get().getEmail().equals(account.getEmail())) {
                throw new DuplicateItemException("User with email: " + account.getEmail() + " already exists.");
            } else {
                if (!username.equals(account.getUsername())) {
                    accountRepo.deleteById(username);
                }
//                if (account.getPassword() != null) {
//                    account.setPassword(bCrypt.encode(account.getPassword()));
//                }
                if (!isAdmin && !Arrays.equals(account.getPermissions(), new String[]{"CLIENT"})) {
                    account.setPermissions(new String[]{"CLIENT"});
                }
                accountRepo.save(account);
                return "User " + username + " updated successfully.";
            }
        } else {
            throw new NoSuchItemException("User " + username + " not found.");
        }
    }

    @Override
    @Transactional
    public String deleteAccount(String username) throws NoSuchItemException {
        if (accountRepo.findById(username).isPresent()) {
            accountRepo.deleteById(username);
            return "User " + username + " deleted successfully.";
        } else {
            throw new NoSuchItemException("User " + username + " not found.");
        }
    }

    @Override
    @Transactional
    public List<Account> getAccounts() {
        return accountRepo.findAll();
    }

    @Override
    @Transactional
    public List<Account> filterAccounts(String filter) {
        return accountRepo.findAllByUsernameContainsIgnoreCaseOrEmailContainsIgnoreCaseOrFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCase(filter, filter, filter, filter);
    }
}
