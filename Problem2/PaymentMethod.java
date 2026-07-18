import java.util.concurrent.atomic.AtomicInteger;

abstract class PaymentMethod {

    private static final AtomicInteger counter = new AtomicInteger(0);

    protected String generateTransactionId() {
        int id = counter.incrementAndGet();
        long timestamp = System.currentTimeMillis();
        return "TXN-" + timestamp + "-" + id;
    }

    
    protected boolean validateAccountDetails(String accountNumber) {
        return accountNumber != null && accountNumber.matches("\\d{8,17}");
    }

    abstract boolean authorize(double amount);
    abstract String processPayment(double amount);
    abstract String generateReceipt();
}

interface Refundable {
    boolean refund(double amount);
}

interface RecurringBillable {
    void setupRecurringBilling(double amount, int intervalDays);
}

interface LoyaltyPointsEarner {
    int earnPoints(double amount);
}

class CreditCardPayment extends PaymentMethod
        implements Refundable, RecurringBillable, LoyaltyPointsEarner {

    private String cardNumber;

    CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    boolean authorize(double amount) {
        return validateAccountDetails(cardNumber) && amount > 0;
    }

    @Override
    String processPayment(double amount) {
        String txnId = generateTransactionId();
        return "Processed $" + amount + " via Credit Card. Txn ID: " + txnId;
    }

    @Override
    String generateReceipt() {
        return "Receipt: Credit Card payment completed.";
    }

    @Override
    public boolean refund(double amount) {
        return true; // refund logic
    }

    @Override
    public void setupRecurringBilling(double amount, int intervalDays) {
        System.out.println("Recurring billing set up every " + intervalDays + " days.");
    }

    @Override
    public int earnPoints(double amount) {
        return (int) (amount / 10); // 1 point per $10
    }
}

class PayPalPayment extends PaymentMethod
        implements Refundable, RecurringBillable, LoyaltyPointsEarner {

    private String email;

    PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    boolean authorize(double amount) {
        return email != null && email.contains("@") && amount > 0;
    }

    @Override
    String processPayment(double amount) {
        String txnId = generateTransactionId();
        return "Processed $" + amount + " via PayPal. Txn ID: " + txnId;
    }

    @Override
    String generateReceipt() {
        return "Receipt: PayPal payment completed.";
    }

    @Override
    public boolean refund(double amount) {
        return true;
    }

    @Override
    public void setupRecurringBilling(double amount, int intervalDays) {
        System.out.println("PayPal recurring billing set up every " + intervalDays + " days.");
    }

    @Override
    public int earnPoints(double amount) {
        return (int) (amount / 15);
    }
}

class BankTransferPayment extends PaymentMethod {

    private String routingNumber;

    BankTransferPayment(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    @Override
    boolean authorize(double amount) {
        return validateAccountDetails(routingNumber) && amount > 0;
    }

    @Override
    String processPayment(double amount) {
        String txnId = generateTransactionId();
        return "Processed $" + amount + " via Bank Transfer. Txn ID: " + txnId;
    }

    @Override
    String generateReceipt() {
        return "Receipt: Bank Transfer payment completed.";
    }
   
}

class CryptoPayment extends PaymentMethod implements LoyaltyPointsEarner {

    private String walletAddress;

    CryptoPayment(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    @Override
    boolean authorize(double amount) {
        return walletAddress != null && amount > 0;
    }

    @Override
    String processPayment(double amount) {
        String txnId = generateTransactionId();
        return "Processed $" + amount + " via Crypto. Txn ID: " + txnId;
    }

    @Override
    String generateReceipt() {
        return "Receipt: Crypto payment completed (irreversible).";
    }

    @Override
    public int earnPoints(double amount) {
        return (int) (amount / 20);
    }
    
}