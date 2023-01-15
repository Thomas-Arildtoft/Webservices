
package dtu.ws.fastmoney;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the dtu.ws.fastmoney package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CreateAccountWithBalance_QNAME = new QName("http://fastmoney.ws.dtu/", "createAccountWithBalance");
    private final static QName _CreateAccountWithBalanceResponse_QNAME = new QName("http://fastmoney.ws.dtu/", "createAccountWithBalanceResponse");
    private final static QName _GetAccount_QNAME = new QName("http://fastmoney.ws.dtu/", "getAccount");
    private final static QName _GetAccountResponse_QNAME = new QName("http://fastmoney.ws.dtu/", "getAccountResponse");
    private final static QName _GetAccounts_QNAME = new QName("http://fastmoney.ws.dtu/", "getAccounts");
    private final static QName _GetAccountsResponse_QNAME = new QName("http://fastmoney.ws.dtu/", "getAccountsResponse");
    private final static QName _RetireAccount_QNAME = new QName("http://fastmoney.ws.dtu/", "retireAccount");
    private final static QName _RetireAccountResponse_QNAME = new QName("http://fastmoney.ws.dtu/", "retireAccountResponse");
    private final static QName _TransferMoneyFromTo_QNAME = new QName("http://fastmoney.ws.dtu/", "transferMoneyFromTo");
    private final static QName _TransferMoneyFromToResponse_QNAME = new QName("http://fastmoney.ws.dtu/", "transferMoneyFromToResponse");
    private final static QName _BankServiceException_QNAME = new QName("http://fastmoney.ws.dtu/", "BankServiceException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: dtu.ws.fastmoney
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateAccountWithBalance }
     * 
     */
    public CreateAccountWithBalance createCreateAccountWithBalance() {
        return new CreateAccountWithBalance();
    }

    /**
     * Create an instance of {@link CreateAccountWithBalanceResponse }
     * 
     */
    public CreateAccountWithBalanceResponse createCreateAccountWithBalanceResponse() {
        return new CreateAccountWithBalanceResponse();
    }

    /**
     * Create an instance of {@link GetAccount }
     * 
     */
    public GetAccount createGetAccount() {
        return new GetAccount();
    }

    /**
     * Create an instance of {@link GetAccountResponse }
     * 
     */
    public GetAccountResponse createGetAccountResponse() {
        return new GetAccountResponse();
    }

    /**
     * Create an instance of {@link GetAccounts }
     * 
     */
    public GetAccounts createGetAccounts() {
        return new GetAccounts();
    }

    /**
     * Create an instance of {@link GetAccountsResponse }
     * 
     */
    public GetAccountsResponse createGetAccountsResponse() {
        return new GetAccountsResponse();
    }

    /**
     * Create an instance of {@link RetireAccount }
     * 
     */
    public RetireAccount createRetireAccount() {
        return new RetireAccount();
    }

    /**
     * Create an instance of {@link RetireAccountResponse }
     * 
     */
    public RetireAccountResponse createRetireAccountResponse() {
        return new RetireAccountResponse();
    }

    /**
     * Create an instance of {@link TransferMoneyFromTo }
     * 
     */
    public TransferMoneyFromTo createTransferMoneyFromTo() {
        return new TransferMoneyFromTo();
    }

    /**
     * Create an instance of {@link TransferMoneyFromToResponse }
     * 
     */
    public TransferMoneyFromToResponse createTransferMoneyFromToResponse() {
        return new TransferMoneyFromToResponse();
    }

    /**
     * Create an instance of {@link BankServiceException }
     * 
     */
    public BankServiceException createBankServiceException() {
        return new BankServiceException();
    }

    /**
     * Create an instance of {@link Account }
     * 
     */
    public Account createAccount() {
        return new Account();
    }

    /**
     * Create an instance of {@link Transaction }
     * 
     */
    public Transaction createTransaction() {
        return new Transaction();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link AccountInfo }
     * 
     */
    public AccountInfo createAccountInfo() {
        return new AccountInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAccountWithBalance }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateAccountWithBalance }{@code >}
     */
    @XmlElementDecl(namespace = "http://fastmoney.ws.dtu/", name = "createAccountWithBalance")
    public JAXBElement<CreateAccountWithBalance> createCreateAccountWithBalance(CreateAccountWithBalance value) {
        return new JAXBElement<CreateAccountWithBalance>(_CreateAccountWithBalance_QNAME, CreateAccountWithBalance.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAccountWithBalanceResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateAccountWithBalanceResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://fastmoney.ws.dtu/", name = "createAccountWithBalanceResponse")
    public JAXBElement<CreateAccountWithBalanceResponse> createCreateAccountWithBalanceResponse(CreateAccountWithBalanceResponse value) {
        return new JAXBElement<CreateAccountWithBalanceResponse>(_CreateAccountWithBalanceResponse_QNAME, CreateAccountWithBalanceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccount }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAccount }{@code >}
     */
    @XmlElementDecl(namespace = "http://fastmoney.ws.dtu/", name = "getAccount")
    public JAXBElement<GetAccount> createGetAccount(GetAccount value) {
        return new JAXBElement<GetAccount>(_GetAccount_QNAME, GetAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAccountResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://fastmoney.ws.dtu/", name = "getAccountResponse")
    public JAXBElement<GetAccountResponse> createGetAccountResponse(GetAccountResponse value) {
        return new JAXBElement<GetAccountResponse>(_GetAccountResponse_QNAME, GetAccountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccounts }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAccounts }{@code >}
     */
    @XmlElementDecl(namespace = "http://fastmoney.ws.dtu/", name = "getAccounts")
    public JAXBElement<GetAccounts> createGetAccounts(GetAccounts value) {
        return new JAXBElement<GetAccounts>(_GetAccounts_QNAME, GetAccounts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountsResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAccountsResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://fastmoney.ws.dtu/", name = "getAccountsResponse")
    public JAXBElement<GetAccountsResponse> createGetAccountsResponse(GetAccountsResponse value) {
        return new JAXBElement<GetAccountsResponse>(_GetAccountsResponse_QNAME, GetAccountsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetireAccount }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RetireAccount }{@code >}
     */
    @XmlElementDecl(namespace = "http://fastmoney.ws.dtu/", name = "retireAccount")
    public JAXBElement<RetireAccount> createRetireAccount(RetireAccount value) {
        return new JAXBElement<RetireAccount>(_RetireAccount_QNAME, RetireAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetireAccountResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RetireAccountResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://fastmoney.ws.dtu/", name = "retireAccountResponse")
    public JAXBElement<RetireAccountResponse> createRetireAccountResponse(RetireAccountResponse value) {
        return new JAXBElement<RetireAccountResponse>(_RetireAccountResponse_QNAME, RetireAccountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransferMoneyFromTo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TransferMoneyFromTo }{@code >}
     */
    @XmlElementDecl(namespace = "http://fastmoney.ws.dtu/", name = "transferMoneyFromTo")
    public JAXBElement<TransferMoneyFromTo> createTransferMoneyFromTo(TransferMoneyFromTo value) {
        return new JAXBElement<TransferMoneyFromTo>(_TransferMoneyFromTo_QNAME, TransferMoneyFromTo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransferMoneyFromToResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TransferMoneyFromToResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://fastmoney.ws.dtu/", name = "transferMoneyFromToResponse")
    public JAXBElement<TransferMoneyFromToResponse> createTransferMoneyFromToResponse(TransferMoneyFromToResponse value) {
        return new JAXBElement<TransferMoneyFromToResponse>(_TransferMoneyFromToResponse_QNAME, TransferMoneyFromToResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BankServiceException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BankServiceException }{@code >}
     */
    @XmlElementDecl(namespace = "http://fastmoney.ws.dtu/", name = "BankServiceException")
    public JAXBElement<BankServiceException> createBankServiceException(BankServiceException value) {
        return new JAXBElement<BankServiceException>(_BankServiceException_QNAME, BankServiceException.class, null, value);
    }

}
