abstract class Payment {
    private static int transactionCounter = 0;
    protected String transactionId;

    public boolean authorize(double amount) {
        if (amount <= 0) {
            System.out.println("Authorization failed: invalid amount");
            return false;
        }
        System.out.println("Authorized amount: " + amount);
        return true;
    }

    public abstract void processPayment(double amount);

    public abstract String generateReceipt();

    // used by credit card and bank transfer
    protected boolean validateAccountDetails(String details) {
        boolean ok = details != null && details.matches("\\d{8,17}");
        System.out.println("Account details valid: " + ok);
        return ok;
    }

    // counter + timestamp for unique id
    protected String generateTransactionId() {
        transactionCounter++;
        return "TXN-" + System.currentTimeMillis() + "-" + transactionCounter;
    }
}

interface Refundable {
    boolean refund(double amount);
}

interface RecurringBillable {
    void setupRecurringBilling(int intervalDays);
    void cancelRecurringBilling();
}

interface LoyaltyPointsEarner {
    int calculateLoyaltyPoints(double amount);
}

class CreditCardPayment extends Payment
        implements Refundable, RecurringBillable, LoyaltyPointsEarner {

    private String cardNumber;

    CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void processPayment(double amount) {
        if (authorize(amount) && validateAccountDetails(cardNumber)) {
            transactionId = generateTransactionId();
            System.out.println("Credit card payment of " + amount + " processed. ID: " + transactionId);
        }
    }

    @Override
    public String generateReceipt() {
        return "[Receipt] Credit Card | TxnID: " + transactionId;
    }

    @Override
    public boolean refund(double amount) {
        System.out.println("Refunding " + amount + " to credit card");
        return true;
    }

    @Override
    public void setupRecurringBilling(int intervalDays) {
        System.out.println("Credit card recurring billing every " + intervalDays + " days");
    }

    @Override
    public void cancelRecurringBilling() {
        System.out.println("Credit card recurring billing cancelled");
    }

    @Override
    public int calculateLoyaltyPoints(double amount) {
        return (int) (amount / 10);
    }
}

class PayPalPayment extends Payment
        implements Refundable, RecurringBillable, LoyaltyPointsEarner {

    private String email;

    PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void processPayment(double amount) {
        if (authorize(amount)) {
            transactionId = generateTransactionId();
            System.out.println("PayPal payment of " + amount + " processed for " + email + ". ID: " + transactionId);
        }
    }

    @Override
    public String generateReceipt() {
        return "[Receipt] PayPal | TxnID: " + transactionId;
    }

    @Override
    public boolean refund(double amount) {
        System.out.println("Refunding " + amount + " via PayPal");
        return true;
    }

    @Override
    public void setupRecurringBilling(int intervalDays) {
        System.out.println("PayPal subscription every " + intervalDays + " days");
    }

    @Override
    public void cancelRecurringBilling() {
        System.out.println("PayPal subscription cancelled");
    }

    @Override
    public int calculateLoyaltyPoints(double amount) {
        return (int) (amount / 20);
    }
}

class BankTransferPayment extends Payment {

    private String accountNumber;

    BankTransferPayment(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public void processPayment(double amount) {
        if (authorize(amount) && validateAccountDetails(accountNumber)) {
            transactionId = generateTransactionId();
            System.out.println("Bank transfer of " + amount + " processed. ID: " + transactionId);
        }
    }

    @Override
    public String generateReceipt() {
        return "[Receipt] Bank Transfer | TxnID: " + transactionId;
    }
}

class CryptoPayment extends Payment implements LoyaltyPointsEarner {

    private String walletAddress;

    CryptoPayment(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    @Override
    public void processPayment(double amount) {
        if (authorize(amount)) {
            transactionId = generateTransactionId();
            System.out.println("Crypto payment of " + amount + " sent to " + walletAddress + ". ID: " + transactionId);
        }
    }

    @Override
    public String generateReceipt() {
        return "[Receipt] Crypto (irreversible) | TxnID: " + transactionId;
    }

    @Override
    public int calculateLoyaltyPoints(double amount) {
        return (int) (amount / 50);
    }
    // no refund for crypto
}

public class PaymentSystem {
    public static void main(String[] args) {
        CreditCardPayment cc = new CreditCardPayment("12345678901234");
        cc.processPayment(500);
        System.out.println(cc.generateReceipt());
        cc.refund(200);
        cc.setupRecurringBilling(30);
        System.out.println("CC points: " + cc.calculateLoyaltyPoints(500));

        System.out.println();

        PayPalPayment pp = new PayPalPayment("user@example.com");
        pp.processPayment(300);
        System.out.println(pp.generateReceipt());
        pp.refund(300);

        System.out.println();

        BankTransferPayment bt = new BankTransferPayment("987654321");
        bt.processPayment(1000);
        System.out.println(bt.generateReceipt());

        System.out.println();

        CryptoPayment crypto = new CryptoPayment("0xABC123");
        crypto.processPayment(2000);
        System.out.println(crypto.generateReceipt());
        System.out.println("Crypto points: " + crypto.calculateLoyaltyPoints(2000));
    }
}
