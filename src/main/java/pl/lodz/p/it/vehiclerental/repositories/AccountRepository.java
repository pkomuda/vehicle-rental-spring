package pl.lodz.p.it.vehiclerental.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.lodz.p.it.vehiclerental.model.Account;

public interface AccountRepository extends MongoRepository<Account, String> {

}
