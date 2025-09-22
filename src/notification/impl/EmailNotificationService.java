package notification.impl;

import notification.NotificationService;

/**
 * Implementation gửi thông báo qua email
 * Tuân thủ Open/Closed Principle - có thể mở rộng mà không sửa đổi code hiện có
 */
public class EmailNotificationService implements NotificationService {
    @Override
    public void sendNotification(String message, String recipient) {
        System.out.println("📧 EMAIL NOTIFICATION");
        System.out.println("To: " + recipient);
        System.out.println("Message: " + message);
        System.out.println("---");
    }
}

