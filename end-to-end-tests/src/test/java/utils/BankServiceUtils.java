package utils;

import dk.dtu.pay.utils.models.AccountId;
import dtu.ws.fastmoney.*;
import lombok.SneakyThrows;

import java.math.BigDecimal;

/**
 * @author Piotr
 */
 
public class BankServiceUtils {

    private BankService bankService = new BankServiceService().getBankServicePort();

    public String registerOrGetAccountId(User user, BigDecimal amount) {
        try {
            AccountInfo accountInfo = findUser(user);
            if (accountInfo != null)
                return accountInfo.getAccountId();
            return bankService.createAccountWithBalance(user, amount);
        } catch (Exception e) {
            return null;
        }
    }

    public AccountInfo findUser(User user) {
        return bankService.getAccounts().stream()
                .filter(acc -> acc.getUser().getCprNumber().equals(user.getCprNumber()))
                .findFirst()
                .orElse(null);
    }

    @SneakyThrows
    public Account getAccount(AccountId accountId) {
        return bankService.getAccount(accountId.getId());
    }

}
