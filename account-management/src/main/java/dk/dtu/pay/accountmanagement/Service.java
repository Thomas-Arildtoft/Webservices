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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Service {

    private final MessageQueue messageQueue;
    private BankService bankService = new BankServiceService().getBankServicePort();
    private Repository repository = new Repository();
    private Logger logger = Logger.getLogger(Service.class.getName());

    public Service(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
        addCleanAccountManagementRequestedSubscriber();
        addRegisterRequestedSubscriber(QueueNames.REGISTER_CUSTOMER_REQUESTED, QueueNames.REGISTER_CUSTOMER_RETURNED, Role.CUSTOMER);
        addRegisterRequestedSubscriber(QueueNames.REGISTER_MERCHANT_REQUESTED, QueueNames.REGISTER_MERCHANT_RETURNED, Role.MERCHANT);
        addAccountRequestedSubscriber();
    }

    private void addCleanAccountManagementRequestedSubscriber() {
        messageQueue.addHandler(QueueNames.CLEAN_ACCOUNT_MANAGEMENT_REQUESTED,
                (event) -> {
                    repository = new Repository();
                });
    }

    private void addRegisterRequestedSubscriber(String subscribeQueue, String publishQueue, Role role) {
        messageQueue.addHandler(subscribeQueue,
                (event) -> {
                    try {
                        AccountId accountId = event.getArgument(0, AccountId.class);
                        logger.log(Level.INFO, subscribeQueue + " account id(" + accountId + ")");
                        User user = registerUser(accountId, role);
                        logger.log(Level.INFO, subscribeQueue + " user(" + user + ")");
                        messageQueue.publish(publishQueue, new Event(new Object[]{user, "Successfully registered"}));
                    } catch (Exception e) {
                        logger.log(Level.INFO, subscribeQueue + " user null");
                        messageQueue.publish(publishQueue, new Event(new Object[]{null, e.getMessage()}));
                    }
                });
    }

    private void addAccountRequestedSubscriber() {
        messageQueue.addHandler(QueueNames.ACCOUNT_REQUESTED,
                (event) -> {
                    try {
                        User user = event.getArgument(0, User.class);
                        logger.log(Level.INFO, "ACCOUNT_REQUESTED user(" + user + ")");
                        AccountId accountId = repository.getAccountId(user);
                        logger.log(Level.INFO, "ACCOUNT_REQUESTED account id(" + accountId + ")");
                        messageQueue.publish(QueueNames.ACCOUNT_RETURNED, new Event(new Object[]{accountId, "User successfully found"}));
                    } catch (Exception e) {
                        logger.log(Level.INFO, "ACCOUNT_REQUESTED account id null");
                        messageQueue.publish(QueueNames.ACCOUNT_RETURNED, new Event(new Object[]{null, "User does not exist"}));
                    }
                });
    }

    private User registerUser(AccountId accountId, Role role) {
        validateAccountId(accountId);
        User user = new User(UUID.randomUUID().toString(), role);
        repository.addUser(user, accountId);
        return user;
    }

    @SneakyThrows
    private void validateAccountId(AccountId accountId) {
        bankService.getAccount(accountId.getId());
        if (repository.accountIsRegistered(accountId))
            throw new Exception("Account already in use");
    }
}
