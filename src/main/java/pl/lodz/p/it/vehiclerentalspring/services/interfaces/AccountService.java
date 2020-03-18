package pl.lodz.p.it.vehiclerentalspring.services.interfaces;

import pl.lodz.p.it.vehiclerentalspring.model.Account;

import java.util.List;

public interface AccountService {

    String addAccount(Account account, boolean isAdmin) throws Exception;
    Account getAccount(String username, boolean byEmail) throws Exception;
    String updateAccount(String username, Account account, boolean isAdmin) throws Exception;
    String deleteAccount(String username) throws Exception;
    List<Account> getAccounts();
    List<Account> filterAccounts(String filter);
}
