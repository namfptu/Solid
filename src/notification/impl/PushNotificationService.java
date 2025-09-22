package notification.impl;

import notification.NotificationService;

/**
 * Implementation gửi thông báo push
 * Tuân thủ Open/Closed Principle - có thể mở rộng mà không sửa đổi code hiện có
 */
public class PushNotificationService implements NotificationService {
    @Override
    public void sendNotification(String message, String recipient) {
        System.out.println("🔔 PUSH NOTIFICATION");
        System.out.println("To: " + recipient);
        System.out.println("Message: " + message);
        System.out.println("---");
    }
}

