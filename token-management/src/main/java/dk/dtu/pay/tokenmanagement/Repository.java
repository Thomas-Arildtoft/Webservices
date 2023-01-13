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

}
