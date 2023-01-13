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
        addCustomerSubscriber();
    }

    private void addCustomerSubscriber() {
        messageQueue.addHandler(QueueNames.REGISTER_CUSTOMER_REQUESTED,
            (event) -> {
                AccountId accountId = event.getArgument(0, AccountId.class);
                User user = registerUser(accountId, Role.CUSTOMER);
                messageQueue.publish(QueueNames.REGISTER_CUSTOMER_RETURNED, new Event(new Object[]{user}));
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
