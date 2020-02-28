package pl.lodz.p.it.vehiclerentalspring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.lodz.p.it.vehiclerentalspring.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {

    Optional<Account> findByEmail(String email);
    Optional<Account> findByLoginOrEmail(String login, String email);
    List<Account> findAllByLoginContainsIgnoreCaseOrEmailContainsIgnoreCaseOrFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCase(String login, String email, String firstName, String lastName);
}
