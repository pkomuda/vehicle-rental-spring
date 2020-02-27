package pl.lodz.p.it.vehiclerental.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.vehiclerental.model.Account;
import pl.lodz.p.it.vehiclerental.repositories.AccountRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MongoUserDetailsService implements UserDetailsService {

    private AccountRepository accountRepo;

    @Autowired
    public MongoUserDetailsService(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String loginOrEmail) throws UsernameNotFoundException {
        if (accountRepo.findByLoginOrEmail(loginOrEmail, loginOrEmail).isEmpty()) {
            throw new UsernameNotFoundException("User not found.");
        } else {
            Account account = accountRepo.findByLoginOrEmail(loginOrEmail, loginOrEmail).get();
            List<SimpleGrantedAuthority> authorities = Arrays.stream(account.getPermissions())
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            return new User(account.getLogin(), account.getPassword(), authorities);
        }
    }
}
