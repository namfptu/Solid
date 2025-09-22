package notification.impl;

import notification.NotificationService;

/**
 * Implementation gửi thông báo qua SMS
 * Tuân thủ Open/Closed Principle - có thể mở rộng mà không sửa đổi code hiện có
 */
public class SMSNotificationService implements NotificationService {
    @Override
    public void sendNotification(String message, String recipient) {
        System.out.println("📱 SMS NOTIFICATION");
        System.out.println("To: " + recipient);
        System.out.println("Message: " + message);
        System.out.println("---");
    }
}

