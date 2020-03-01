package pl.lodz.p.it.vehiclerentalspring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.lodz.p.it.vehiclerentalspring.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {

    Optional<Account> findByEmail(String email);
    Optional<Account> findByUsernameOrEmail(String username, String email);
    List<Account> findAllByUsernameContainsIgnoreCaseOrEmailContainsIgnoreCaseOrFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCase(String username, String email, String firstName, String lastName);
}
