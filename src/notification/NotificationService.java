package notification;

/**
 * Interface cho dịch vụ thông báo
 * Tuân thủ Open/Closed Principle - mở để mở rộng, đóng để sửa đổi
 */
public interface NotificationService {
    void sendNotification(String message, String recipient);
}

