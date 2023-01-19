package dk.dtu.pay.tokenmanagement;

import dk.dtu.pay.utils.messaging.Event;
import dk.dtu.pay.utils.messaging.MessageQueue;
import dk.dtu.pay.utils.messaging.QueueNames;
import dk.dtu.pay.utils.models.TokenRequest;
import dk.dtu.pay.utils.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Service {

    private final static int MINIMUM_NUM_OF_TOKENS = 1;
    private final static int MAXIMUM_NUM_OF_TOKENS = 6;
    private final MessageQueue messageQueue;
    private Repository repository = new Repository();

    public Service(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
        addCleanAccountManagementRequestedSubscriber();
        addTokenRequestedSubscriber();
        addUserFromTokenRequestedSubscriber();
    }

    private void addCleanAccountManagementRequestedSubscriber() {
        messageQueue.addHandler(QueueNames.CLEAN_TOKEN_MANAGEMENT_REQUESTED,
                (event) -> repository = new Repository());
    }

    public void addTokenRequestedSubscriber() {
        messageQueue.addHandler(QueueNames.TOKENS_REQUESTED,
                (event) -> {
                    TokenRequest tokenRequest = event.getArgument(0, TokenRequest.class);
                    int numOfTokens = tokenRequest.getNumberOfTokens();
                    if (numOfTokens < MINIMUM_NUM_OF_TOKENS || numOfTokens > MAXIMUM_NUM_OF_TOKENS) {
                        messageQueue.publish(QueueNames.TOKENS_RETURNED, new Event(new Object[]{null, "Incorrect number of tokens request. Allowed range [1,6]"}));
                    } else {
                        List<String> tokens = generateTokens(tokenRequest);
                        messageQueue.publish(QueueNames.TOKENS_RETURNED, new Event(new Object[]{tokens, "Tokens successfully generated"}));
                    }
                });
    }

    public void addUserFromTokenRequestedSubscriber() {
        messageQueue.addHandler(QueueNames.USER_FROM_TOKEN_REQUESTED,
                (event) -> {
                    String token = event.getArgument(0, String.class);
                    User user = repository.findUserAndRemoveToken(token);
                    if (user != null)
                        messageQueue.publish(QueueNames.USER_FROM_TOKEN_RETURNED, new Event(new Object[]{user, "User successfully retrieved"}));
                    else
                        messageQueue.publish(QueueNames.USER_FROM_TOKEN_RETURNED, new Event(new Object[]{null, "Invalid token"}));
                });
    }

    private List<String> generateTokens(TokenRequest tokenRequest) {
        List<String> tokens = new ArrayList<>();
        for (int i = 0; i < tokenRequest.getNumberOfTokens(); i++) {
            tokens.add(UUID.randomUUID().toString());
        }
        repository.put(tokenRequest.getUser(), tokens);
        return tokens;
    }
}
