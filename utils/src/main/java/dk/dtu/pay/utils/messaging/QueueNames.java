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
    public static final String USER_FROM_TOKEN_REQUESTED = "UserRequested";
    public static final String USER_FROM_TOKEN_RETURNED = "UserReturned";

    // TODO: need to be added in Miro (it replaces the connection between token management and account management)
    // Payment management --- > Account management
    public static final String ACCOUNT_REQUESTED = "PMAccountRequested";
    public static final String ACCOUNT_RETURNED = "PMAccountReturned";

    // Utility queues - they are not part of the architecture, just for test purposes to clean the repositories
    public static final String CLEAN_ACCOUNT_MANAGEMENT_REQUESTED = "TMAccountRequested";
    public static final String CLEAN_TOKEN_MANAGEMENT_REQUESTED = "TMAccountRequested";

}
