package payment.impl;

import payment.PaymentProcessor;

/**
 * Xử lý thanh toán qua PayPal
 * Tuân thủ Liskov Substitution Principle - có thể thay thế PaymentProcessor
 */
public class PayPalPaymentProcessor implements PaymentProcessor {
    @Override
    public boolean processPayment(double amount, String accountNumber) {
        System.out.println("💰 Processing PayPal payment of $" + amount + " for account: " + accountNumber);
        // Giả lập xử lý thanh toán
        return amount > 0 && accountNumber != null && !accountNumber.trim().isEmpty();
    }
    
    @Override
    public String getPaymentMethod() {
        return "PayPal";
    }
}

