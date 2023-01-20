package dk.dtu.pay.accountmanagement;

import dk.dtu.pay.utils.models.AccountId;
import dk.dtu.pay.utils.models.User;

/**
 * @author Ali
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Repository {

    private Map<User, AccountId> users = new HashMap<>();
    private Logger logger = Logger.getLogger(Repository.class.getName());

    public void addUser(User user, AccountId accountId) {
        users.put(user, accountId);
    }

    public AccountId getAccountId(User user) {
        logger.log(Level.INFO, "Persisted user(" + users + ") requested user(" + user + ")");
        return users.get(user);
    }

    public boolean accountIsRegistered(AccountId accountId) {
        return getUsers(accountId).size() == 1;
    }

    private List<User> getUsers(AccountId accountId) {
        return users.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue().getId(), accountId.getId()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

}
