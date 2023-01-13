package dk.dtu.pay.utils.messaging;

public class QueueNames {

    // Customer --- > Account management
    public static final String REGISTER_CUSTOMER_REQUESTED = "RegisterCustomerRequested";
    public static final String REGISTER_CUSTOMER_RETURNED = "RegisterCustomerReturned";

    // Merchant --- > Account management
    public static final String REGISTER_MERCHANT_REQUESTED = "RegisterMerchantRequested";
    public static final String REGISTER_MERCHANT_RETURNED = "RegisterMerchantReturned";

    // Merchant --- > Payment management
    public static final String INITIATE_PAYMENT_REQUESTED = "InitiatePaymentRequested";
    public static final String INITIATE_PAYMENT_RETURNED = "InitiatePaymentReturned";

    // Customer --- > Token management
    public static final String TOKENS_REQUESTED = "TokensRequested";
    public static final String TOKENS_RETURNED = "TokensReturned";

    // Payment management --- > Token management
    public static final String USER_REQUESTED = "UserRequested";
    public static final String USER_RETURNED = "UserReturned";

    // TODO: need to be added in Miro
    // Payment management --- > Account management
    public static final String PM_ACCOUNT_REQUESTED = "PMAccountRequested";
    public static final String PM_ACCOUNT_RETURNED = "PMAccountReturned";

    // Token management --- > Account management
    public static final String TM_ACCOUNT_REQUESTED = "TMAccountRequested";
    public static final String TM_ACCOUNT_RETURNED = "TMAccountReturned";

}
