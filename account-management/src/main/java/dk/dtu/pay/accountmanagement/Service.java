package dk.dtu.pay.accountmanagement;

import dk.dtu.pay.utils.messaging.Event;
import dk.dtu.pay.utils.messaging.MessageQueue;
import dk.dtu.pay.utils.messaging.QueueNames;
import dk.dtu.pay.utils.models.AccountId;
import dk.dtu.pay.utils.models.Role;
import dk.dtu.pay.utils.models.User;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;
import lombok.SneakyThrows;

import java.util.UUID;

public class Service {

    private final MessageQueue messageQueue;
    private BankService bankService = new BankServiceService().getBankServicePort();
    private Repository repository = new Repository();

    public Service(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
        subscribeCleanAccountManagementRequest();
        addRegisterSubscriber(QueueNames.REGISTER_CUSTOMER_REQUESTED, QueueNames.REGISTER_CUSTOMER_RETURNED, Role.CUSTOMER);
        addRegisterSubscriber(QueueNames.REGISTER_MERCHANT_REQUESTED, QueueNames.REGISTER_MERCHANT_RETURNED, Role.MERCHANT);
        addGetAccountSubscriber(QueueNames.PAYMENT_MANAGEMENT_ACCOUNT_REQUESTED, QueueNames.PAYMENT_MANAGEMENT_ACCOUNT_RETURNED);
    }

    private void subscribeCleanAccountManagementRequest() {
        messageQueue.addHandler(QueueNames.CLEAN_ACCOUNT_MANAGEMENT_REQUESTED,
                (event) -> {
                    repository = new Repository();
                });
    }

    private void addRegisterSubscriber(String subscribeQueue, String publishQueue, Role role) {
        messageQueue.addHandler(subscribeQueue,
            (event) -> {
                AccountId accountId = event.getArgument(0, AccountId.class);
                User user = registerUser(accountId, role);
                messageQueue.publish(publishQueue, new Event(new Object[]{user}));
            });
    }

    private void addGetAccountSubscriber(String subscribeQueue, String publishQueue) {
        messageQueue.addHandler(subscribeQueue,
                (event) -> {
                    User user = event.getArgument(0, User.class);
                    AccountId accountId = repository.getAccountId(user);
                    messageQueue.publish(publishQueue, new Event(new Object[]{ accountId }));
                });
    }

    private User registerUser(AccountId accountId, Role role) {
        try {
            validateAccountId(accountId);
            User user = new User(UUID.randomUUID().toString(), role);
            repository.addUser(user, accountId);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @SneakyThrows
    private void validateAccountId(AccountId accountId) {
        bankService.getAccount(accountId.getId());
        if (repository.accountIsRegistered(accountId))
            throw new Exception("Account already in use");
    }
}
