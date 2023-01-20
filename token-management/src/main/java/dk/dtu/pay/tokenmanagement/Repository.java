package dk.dtu.pay.tokenmanagement;

import dk.dtu.pay.utils.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Muhammad
 */
public class Repository {

    private Map<User, List<String>> userTokens = new HashMap<>();
    private Logger logger = Logger.getLogger(Repository.class.getName());

    public void put(User user, List<String> tokens) {
        userTokens.put(user, tokens);
    }

    public User findUserAndRemoveToken(String token) {
        for (var entry : userTokens.entrySet()) {
            User user = entry.getKey();
            List<String> tokens = entry.getValue();
            logger.log(Level.INFO, "Repository generated tokens(" + tokens + ") for user(" + user + ")");
            if (tokens.contains(token)) {
                tokens.remove(token);
                return user;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return userTokens.isEmpty();
    }

}
