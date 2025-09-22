package payment;

/**
 * Interface cho xử lý thanh toán
 * Tuân thủ Liskov Substitution Principle - các implementation có thể thay thế lẫn nhau
 */
public interface PaymentProcessor {
    boolean processPayment(double amount, String accountNumber);
    String getPaymentMethod();
}

