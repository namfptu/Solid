# 🏛️ SOLID Principles Demo - Library Management System

Đây là một project demo minh họa tất cả 5 nguyên tắc SOLID trong lập trình hướng đối tượng, được xây dựng bằng Java.

## 📋 Mục lục
- [Giới thiệu về SOLID](#giới-thiệu-về-solid)
- [Cấu trúc Project](#cấu-trúc-project)
- [Chi tiết từng nguyên tắc](#chi-tiết-từng-nguyên-tắc)
- [Cách chạy project](#cách-chạy-project)
- [Kết luận](#kết-luận)

## 🎯 Giới thiệu về SOLID

SOLID là 5 nguyên tắc thiết kế phần mềm được Robert C. Martin (Uncle Bob) đưa ra để giúp lập trình viên viết code dễ đọc, dễ bảo trì và mở rộng:

1. **S** - Single Responsibility Principle (SRP)
2. **O** - Open/Closed Principle (OCP)
3. **L** - Liskov Substitution Principle (LSP)
4. **I** - Interface Segregation Principle (ISP)
5. **D** - Dependency Inversion Principle (DIP)

## 📁 Cấu trúc Project

```
src/
├── Main.java                          # Class chính để demo
├── model/                             # Các model/entity
│   ├── Book.java                      # Model sách
│   └── Member.java                    # Model thành viên
├── repository/                        # Repository pattern
│   ├── BookRepository.java            # Interface repository sách
│   ├── MemberRepository.java          # Interface repository thành viên
│   └── impl/                          # Implementation
│       ├── InMemoryBookRepository.java
│       └── InMemoryMemberRepository.java
├── service/                           # Business logic layer
│   ├── BookService.java               # Service quản lý sách
│   ├── MemberService.java             # Service quản lý thành viên
│   └── LibraryService.java            # Service chính thư viện
├── notification/                      # Notification system
│   ├── NotificationService.java       # Interface thông báo
│   └── impl/                          # Các implementation
│       ├── EmailNotificationService.java
│       ├── SMSNotificationService.java
│       └── PushNotificationService.java
├── payment/                           # Payment system
│   ├── PaymentProcessor.java          # Interface thanh toán
│   └── impl/                          # Các implementation
│       ├── CreditCardPaymentProcessor.java
│       ├── BankTransferPaymentProcessor.java
│       └── PayPalPaymentProcessor.java
└── report/                            # Report system
    ├── ReportGenerator.java           # Interface báo cáo cơ bản
    ├── ExportableReport.java          # Interface xuất file
    ├── PrintableReport.java           # Interface in ấn
    └── impl/                          # Các implementation
        ├── BookReportGenerator.java
        └── MemberReportGenerator.java
```

## 🔍 Chi tiết từng nguyên tắc

### 1️⃣ Single Responsibility Principle (SRP)
**"Một class chỉ nên có một lý do để thay đổi"**

#### Ví dụ trong project:
- `BookService`: Chỉ quản lý logic nghiệp vụ liên quan đến sách
- `MemberService`: Chỉ quản lý logic nghiệp vụ liên quan đến thành viên
- `Book`: Chỉ chứa thông tin và hành vi của một cuốn sách
- `Member`: Chỉ chứa thông tin và hành vi của một thành viên

#### Lợi ích:
- Code dễ hiểu và bảo trì
- Giảm coupling giữa các class
- Dễ dàng test từng phần riêng biệt

### 2️⃣ Open/Closed Principle (OCP)
**"Mở để mở rộng, đóng để sửa đổi"**

#### Ví dụ trong project:
- `NotificationService`: Có thể thêm loại thông báo mới (SMS, Push) mà không sửa code hiện có
- `PaymentProcessor`: Có thể thêm phương thức thanh toán mới (PayPal, Crypto) mà không sửa code hiện có

#### Cách implement:
```java
// Interface mở để mở rộng
public interface NotificationService {
    void sendNotification(String message, String recipient);
}

// Có thể thêm implementation mới
public class WhatsAppNotificationService implements NotificationService {
    // Implementation mới
}
```

#### Lợi ích:
- Code ổn định, ít bug
- Dễ dàng thêm tính năng mới
- Tuân thủ nguyên tắc DRY (Don't Repeat Yourself)

### 3️⃣ Liskov Substitution Principle (LSP)
**"Các object của subclass phải có thể thay thế object của superclass mà không làm thay đổi tính đúng đắn của chương trình"**

#### Ví dụ trong project:
- Tất cả `PaymentProcessor` implementations có thể thay thế lẫn nhau
- Client code không cần biết implementation cụ thể

#### Cách implement:
```java
// Tất cả implementations đều có thể thay thế lẫn nhau
PaymentProcessor[] processors = {
    new CreditCardPaymentProcessor(),
    new BankTransferPaymentProcessor(),
    new PayPalPaymentProcessor()
};

// Client code hoạt động với bất kỳ implementation nào
for (PaymentProcessor processor : processors) {
    processor.processPayment(amount, account);
}
```

#### Lợi ích:
- Code linh hoạt, dễ thay đổi
- Dễ dàng test với mock objects
- Tuân thủ nguyên tắc polymorphism

### 4️⃣ Interface Segregation Principle (ISP)
**"Client không nên phụ thuộc vào interface mà họ không sử dụng"**

#### Ví dụ trong project:
- `ReportGenerator`: Interface cơ bản cho báo cáo
- `ExportableReport`: Interface riêng cho xuất file
- `PrintableReport`: Interface riêng cho in ấn

#### Cách implement:
```java
// Interface nhỏ, cụ thể
public interface ReportGenerator {
    void generateReport(List<?> data);
}

public interface ExportableReport {
    void exportToFile(File file);
}

// Client chỉ implement những gì cần
public class BookReportGenerator implements ReportGenerator {
    // Chỉ implement ReportGenerator
}

public class MemberReportGenerator implements ReportGenerator, ExportableReport, PrintableReport {
    // Implement nhiều interface tùy theo nhu cầu
}
```

#### Lợi ích:
- Interface rõ ràng, dễ hiểu
- Client không bị buộc implement method không cần thiết
- Dễ dàng thay đổi và mở rộng

### 5️⃣ Dependency Inversion Principle (DIP)
**"Phụ thuộc vào abstraction, không phụ thuộc vào concrete class"**

#### Ví dụ trong project:
- `LibraryService` phụ thuộc vào interface, không phụ thuộc vào implementation cụ thể
- Dễ dàng thay đổi implementation mà không sửa `LibraryService`

#### Cách implement:
```java
public class LibraryService {
    // Phụ thuộc vào interface, không phụ thuộc vào concrete class
    private final NotificationService notificationService;
    private final PaymentProcessor paymentProcessor;
    
    public LibraryService(NotificationService notificationService,
                         PaymentProcessor paymentProcessor) {
        this.notificationService = notificationService;
        this.paymentProcessor = paymentProcessor;
    }
}
```

#### Lợi ích:
- Code linh hoạt, dễ test
- Dễ dàng thay đổi implementation
- Giảm coupling giữa các module

## 🚀 Cách chạy project

### Yêu cầu:
- Java 8 trở lên
- IDE hỗ trợ Java (IntelliJ IDEA, Eclipse, VS Code)

### Cách chạy:
1. Clone hoặc download project
2. Mở project trong IDE
3. Chạy file `Main.java`
4. Xem kết quả demo các nguyên tắc SOLID

### Kết quả mong đợi:
```
🏛️ SOLID PRINCIPLES DEMO - LIBRARY MANAGEMENT SYSTEM
=====================================================

1️⃣ SINGLE RESPONSIBILITY PRINCIPLE (SRP)
=========================================
✓ BookService chỉ quản lý sách
✓ MemberService chỉ quản lý thành viên
✓ Mỗi class có một trách nhiệm duy nhất

📚 Books added: 3
👥 Members registered: 2

2️⃣ OPEN/CLOSED PRINCIPLE (OCP)
===============================
✓ Có thể thêm NotificationService mới mà không sửa code hiện có
✓ Có thể thêm PaymentProcessor mới mà không sửa code hiện có

📧 Testing different notification services:
📧 EMAIL NOTIFICATION
To: user@example.com
Message: Test message
---
📱 SMS NOTIFICATION
To: user@example.com
Message: Test message
---
🔔 PUSH NOTIFICATION
To: user@example.com
Message: Test message
---

💳 Testing different payment processors:
💳 Processing credit card payment of $100.0 for account: ACC123456
🏦 Processing bank transfer of $100.0 to account: ACC123456
💰 Processing PayPal payment of $100.0 for account: ACC123456

... (và nhiều kết quả khác)
```

## 🎓 Kết luận

Project này minh họa cách áp dụng 5 nguyên tắc SOLID trong thực tế:

1. **SRP** giúp code dễ hiểu và bảo trì
2. **OCP** giúp dễ dàng mở rộng tính năng
3. **LSP** đảm bảo tính linh hoạt của code
4. **ISP** giúp interface rõ ràng và cụ thể
5. **DIP** giảm coupling và tăng tính linh hoạt

### Lợi ích khi áp dụng SOLID:
- ✅ Code dễ đọc, dễ hiểu
- ✅ Dễ dàng bảo trì và sửa lỗi
- ✅ Dễ dàng mở rộng tính năng mới
- ✅ Dễ dàng test và debug
- ✅ Giảm coupling, tăng cohesion
- ✅ Code có thể tái sử dụng cao

### Lưu ý:
- SOLID không phải là quy tắc cứng nhắc, cần áp dụng linh hoạt
- Cần cân nhắc giữa việc tuân thủ SOLID và độ phức tạp của code
- Áp dụng SOLID từ từ, không cần thay đổi toàn bộ codebase một lúc

---

**Tác giả**: AI Assistant  
**Ngôn ngữ**: Java  
**Mục đích**: Học tập và demo SOLID Principles

