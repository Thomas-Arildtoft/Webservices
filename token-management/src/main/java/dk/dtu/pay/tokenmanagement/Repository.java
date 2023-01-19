package dk.dtu.pay.tokenmanagement;

import dk.dtu.pay.utils.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repository {

    private Map<User, List<String>> userTokens = new HashMap<>();

    public void put(User user, List<String> tokens) {
        userTokens.put(user, tokens);
    }

    public User findUserAndRemoveToken(String token) {
        for (var entry : userTokens.entrySet()) {
            User user = entry.getKey();
            List<String> tokens = entry.getValue();
            System.out.println("Persisted tokens for user " + user + " are " + tokens);
            if (tokens.contains(token)) {
                tokens.remove(token);
                return user;
            }
        }
        return null;
    }

    /***
     *
     *
     *
     *
     * */

}
