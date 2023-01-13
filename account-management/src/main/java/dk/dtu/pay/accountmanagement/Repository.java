package dk.dtu.pay.accountmanagement;

import dk.dtu.pay.utils.models.AccountId;
import dk.dtu.pay.utils.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Repository {

    private Map<User, AccountId> users = new HashMap<>();

    public void addUser(User user, AccountId accountId) {
        users.put(user, accountId);
    }

    public AccountId getAccountId(User user) {
        return users.get(user);
    }

    public User getUser(AccountId accountId) {
        return getUsers(accountId).get(0);
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
