package utils;

public class RepositoriesCleaner {

    private CustomerRestClient customerRestClient = new CustomerRestClient();
    private MerchantRestClient merchantRestClient = new MerchantRestClient();

    public void clean() {
        customerRestClient.clean();
        merchantRestClient.clean();
    }
}
