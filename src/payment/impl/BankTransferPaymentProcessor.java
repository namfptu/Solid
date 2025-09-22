package payment.impl;

import payment.PaymentProcessor;

/**
 * Xử lý thanh toán chuyển khoản ngân hàng
 * Tuân thủ Liskov Substitution Principle - có thể thay thế PaymentProcessor
 */
public class BankTransferPaymentProcessor implements PaymentProcessor {
    @Override
    public boolean processPayment(double amount, String accountNumber) {
        System.out.println("🏦 Processing bank transfer of $" + amount + " to account: " + accountNumber);
        // Giả lập xử lý thanh toán
        return amount > 0 && accountNumber != null && !accountNumber.trim().isEmpty();
    }
    
    @Override
    public String getPaymentMethod() {
        return "Bank Transfer";
    }
}

