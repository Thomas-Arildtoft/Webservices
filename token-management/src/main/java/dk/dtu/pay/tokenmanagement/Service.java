package dk.dtu.pay.tokenmanagement;

import dk.dtu.pay.utils.messaging.Event;
import dk.dtu.pay.utils.messaging.MessageQueue;
import dk.dtu.pay.utils.messaging.QueueNames;
import dk.dtu.pay.utils.models.AccountId;
import dk.dtu.pay.utils.models.TokenRequest;
import dk.dtu.pay.utils.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Service {

    private final MessageQueue messageQueue;
    private Repository repository = new Repository();
    int numOfTokens;

    public Service(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
        subscribeCleanAccountManagementRequest();
        addTokenRequestedSubscriber();
        addAccountFromTokenRequestedSubscriber();
    }

    private void subscribeCleanAccountManagementRequest() {
        messageQueue.addHandler(QueueNames.CLEAN_TOKEN_MANAGEMENT_REQUESTED,
                (event) -> {
                    repository = new Repository();
                });
    }

    public void addTokenRequestedSubscriber() {
        messageQueue.addHandler(QueueNames.TOKENS_REQUESTED,
                (event) -> {
                    TokenRequest tokenRequest = event.getArgument(0, TokenRequest.class);
                    numOfTokens = tokenRequest.getNumberOfTokens();
                    if (numOfTokens < 0 || numOfTokens > 6) {
                        messageQueue.publish(QueueNames.TOKENS_RETURNED, new Event(new Object[]{null}));
                    } else {
                        messageQueue.publish(QueueNames.TM_ACCOUNT_REQUESTED, new Event(new Object[]{tokenRequest.getUser()}));
                        addGetAccountSubscriber(tokenRequest.getUser());
                    }
                });
    }

    public void addAccountFromTokenRequestedSubscriber() {
        messageQueue.addHandler(QueueNames.USER_REQUESTED,
                (event) -> {
                    String token = event.getArgument(0, String.class);
                    User user = repository.findUserAndRemoveToken(token);
                    messageQueue.publish(QueueNames.USER_RETURNED, new Event(new Object[]{ user }));
                });
    }

    private void addGetAccountSubscriber(User user) {
        messageQueue.addHandler(QueueNames.TM_ACCOUNT_RETURNED,
                (event) -> {
                    AccountId accountId = event.getArgument(0, AccountId.class);
                    if (accountId == null) {
                        messageQueue.publish(QueueNames.TOKENS_RETURNED, new Event(new Object[]{null}));
                    } else {
                        List<String> tokens = generateTokens(user);
                        messageQueue.publish(QueueNames.TOKENS_RETURNED, new Event(new Object[]{tokens}));
                    }
                });
    }

    private List<String> generateTokens(User user) {
        List<String> tokens = new ArrayList<>();
        for (int i = 0; i < numOfTokens; i++) {
            tokens.add(UUID.randomUUID().toString());
        }
        repository.put(user, tokens);
        return tokens;
    }
}
