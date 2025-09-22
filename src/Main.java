import model.Book;
import model.Member;
import repository.BookRepository;
import repository.MemberRepository;
import repository.impl.InMemoryBookRepository;
import repository.impl.InMemoryMemberRepository;
import repository.impl.DatabaseBookRepository;
import service.BookService;
import service.LibraryService;
import service.MemberService;
import notification.NotificationService;
import notification.impl.EmailNotificationService;
import notification.impl.SMSNotificationService;
import notification.impl.PushNotificationService;
import payment.PaymentProcessor;
import payment.impl.CreditCardPaymentProcessor;
import payment.impl.BankTransferPaymentProcessor;
import payment.impl.PayPalPaymentProcessor;
import report.ReportGenerator;
import report.ExportableReport;
import report.PrintableReport;
import report.impl.BookReportGenerator;
import report.impl.MemberReportGenerator;

/**
 * Demo class minh họa tất cả 5 nguyên tắc SOLID
 * 
 * S - Single Responsibility Principle (SRP)
 * O - Open/Closed Principle (OCP) 
 * L - Liskov Substitution Principle (LSP)
 * I - Interface Segregation Principle (ISP)
 * D - Dependency Inversion Principle (DIP)
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("🏛️ SOLID PRINCIPLES DEMO -   LIBRARY MANAGEMENT SYSTEM");
        System.out.println("=====================================================");
        System.out.println();
        
        // Khởi tạo dependencies - có thể thay đổi implementation dễ dàng
        BookRepository bookRepository = new InMemoryBookRepository();
        MemberRepository memberRepository = new InMemoryMemberRepository();
        
        BookService bookService = new BookService(bookRepository);
        MemberService memberService = new MemberService(memberRepository);
        
        // Demo Dependency Inversion Principle - thay đổi implementation
        System.out.println("🔄 DEMO: Thay đổi từ InMemory sang Database Repository");
        System.out.println("=====================================================");
        BookRepository databaseRepo = new DatabaseBookRepository();
        BookService databaseBookService = new BookService(databaseRepo);
        
        // Cùng một BookService, nhưng với implementation khác
        databaseBookService.addBook(new Book("DB001", "Database Book", "Author", "978-0-13-235088-4"));
        System.out.println();
        
       
        
        // Demo Open/Closed Principle - có thể thay đổi implementation mà không sửa code
        NotificationService notificationService = new EmailNotificationService();
        PaymentProcessor paymentProcessor = new CreditCardPaymentProcessor();
        ReportGenerator reportGenerator = new BookReportGenerator();
        
        LibraryService libraryService = new LibraryService(
            bookService, memberService, notificationService, 
            paymentProcessor, reportGenerator
        );
        
        // Demo Single Responsibility Principle
        demonstrateSRP(bookService, memberService);
        
        // Demo Open/Closed Principle
        demonstrateOCP(notificationService, paymentProcessor);
        
        // Demo Liskov Substitution Principle
        demonstrateLSP();
        
        // Demo Interface Segregation Principle
        demonstrateISP();
        
        // Demo Dependency Inversion Principle
        demonstrateDIP(libraryService);
        
        System.out.println("\n🎉 Demo hoàn thành! Tất cả nguyên tắc SOLID đã được minh họa.");
    }
    
    /**
     * Demo Single Responsibility Principle (SRP)
     * Mỗi class chỉ có một lý do để thay đổi
     */
    private static void demonstrateSRP(BookService bookService, MemberService memberService) {
        System.out.println("1️⃣ SINGLE RESPONSIBILITY PRINCIPLE (SRP)");
        System.out.println("=========================================");
        System.out.println("✓ Repository: Chỉ quản lý dữ liệu (CRUD)");
        System.out.println("✓ Service: Chứa logic nghiệp vụ, validation, business rules");
        System.out.println("✓ Mỗi class có một trách nhiệm duy nhất");
        System.out.println();
        
        System.out.println("📚 Testing BookService (Business Logic):");
        // Thêm dữ liệu mẫu với validation
        try {
            bookService.addBook(new Book("B001", "Clean Code", "Robert Martin", "978-0132350884"));
            bookService.addBook(new Book("B002", "Design Patterns", "Gang of Four", "978-0201633610"));
            bookService.addBook(new Book("B003", "SOLID Principles", "Uncle Bob", "978-0-13-235088-4"));
            
            // Test business rule: Duplicate ID
            System.out.println("  🔍 Testing duplicate ID validation...");
            try {
                bookService.addBook(new Book("B001", "Duplicate Book", "Author", "978-0000000000"));
            } catch (IllegalStateException e) {
                System.out.println("  ❌ Business rule enforced: " + e.getMessage());
                System.out.println("  📝 This exception was thrown by BookService.addBook() method");
            } catch (Exception e) {
                System.out.println("  ❌ Unexpected error: " + e.getMessage());
            }
            
            // Test business rule: Invalid ISBN
            System.out.println("  🔍 Testing ISBN validation...");
            try {
                bookService.addBook(new Book("B004", "Invalid ISBN Book", "Author", "invalid-isbn"));
            } catch (IllegalArgumentException e) {
                System.out.println("  ❌ Business rule enforced: " + e.getMessage());
                System.out.println("  📝 ISBN validation failed - invalid format");
            } catch (Exception e) {
                System.out.println("  ❌ Unexpected error: " + e.getMessage());
            }
            
            // Test valid ISBN
            System.out.println("  🔍 Testing valid ISBN...");
            try {
                bookService.addBook(new Book("B005", "Valid ISBN Book", "Author", "978-0-13-235088-4"));
                System.out.println("  ✅ Valid ISBN accepted");
            } catch (Exception e) {
                System.out.println("  ❌ Error: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        
        System.out.println("\n👥 Testing MemberService (Business Logic):");
        try {
            memberService.registerMember(new Member("M001", "Nguyễn Văn A", "nguyenvana@email.com", "0123456789"));
            memberService.registerMember(new Member("M002", "Trần Thị B", "tranthib@email.com", "0987654321"));
            
            // Test business rule: Duplicate email
            try {
                memberService.registerMember(new Member("M003", "Duplicate Email", "nguyenvana@email.com", "0123456789"));
            } catch (Exception e) {
                System.out.println("❌ Business rule enforced: " + e.getMessage());
            }
            
            // Test business rule: Invalid phone
            try {
                memberService.registerMember(new Member("M004", "Invalid Phone", "test@email.com", "123"));
            } catch (Exception e) {
                System.out.println("❌ Business rule enforced: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        
        // Show statistics (Business logic)
        System.out.println("\n📊 Business Statistics:");
        System.out.println("Book Statistics: " + bookService.getBookStatistics());
        System.out.println("Member Statistics: " + memberService.getMemberStatistics());
        System.out.println();
    }
    
    /**
     * Demo Open/Closed Principle (OCP)
     * Mở để mở rộng, đóng để sửa đổi
     */
    private static void demonstrateOCP(NotificationService notificationService, PaymentProcessor paymentProcessor) {
        System.out.println("2️⃣ OPEN/CLOSED PRINCIPLE (OCP)");
        System.out.println("===============================");
        System.out.println("✓ Có thể thêm NotificationService mới mà không sửa code hiện có");
        System.out.println("✓ Có thể thêm PaymentProcessor mới mà không sửa code hiện có");
        System.out.println();
        
        // Demo các loại notification khác nhau
        NotificationService[] notificationServices = {
            new EmailNotificationService(),
            new SMSNotificationService(),
            new PushNotificationService()
        };
        
        System.out.println("📧 Testing different notification services:");
        for (NotificationService service : notificationServices) {
            service.sendNotification("Test message", "user@example.com");
        }
        
        // Demo các loại payment khác nhau
        PaymentProcessor[] paymentProcessors = {
            new CreditCardPaymentProcessor(),
            new BankTransferPaymentProcessor(),
            new PayPalPaymentProcessor()
        };
        
        System.out.println("💳 Testing different payment processors:");
        for (PaymentProcessor processor : paymentProcessors) {
            processor.processPayment(100.0, "ACC123456");
        }
        System.out.println();
    }
    
    /**
     * Demo Liskov Substitution Principle (LSP)
     * Các object của subclass có thể thay thế object của superclass
     */
    private static void demonstrateLSP() {
        System.out.println("3️⃣ LISKOV SUBSTITUTION PRINCIPLE (LSP)");
        System.out.println("=======================================");
        System.out.println("✓ Các PaymentProcessor có thể thay thế lẫn nhau");
        System.out.println("✓ Client code không cần biết implementation cụ thể");
        System.out.println();
        
        PaymentProcessor[] processors = {
            new CreditCardPaymentProcessor(),
            new BankTransferPaymentProcessor(),
            new PayPalPaymentProcessor()
        };
        
        System.out.println("🔄 Testing payment processors interchangeably:");
        for (PaymentProcessor processor : processors) {
            boolean result = processor.processPayment(50.0, "TEST123");
            System.out.println("Payment method: " + processor.getPaymentMethod() + 
                             " - Result: " + (result ? "Success" : "Failed"));
        }
        System.out.println();
    }
    
    /**
     * Demo Interface Segregation Principle (ISP)
     * Client không nên phụ thuộc vào interface mà họ không sử dụng
     */
    private static void demonstrateISP() {
        System.out.println("4️⃣ INTERFACE SEGREGATION PRINCIPLE (ISP)");
        System.out.println("==========================================");
        System.out.println("✓ Tách interface thành các phần nhỏ, cụ thể");
        System.out.println("✓ Client chỉ implement những gì cần thiết");
        System.out.println();
        
        // BookReportGenerator chỉ implement ReportGenerator
        ReportGenerator bookReport = new BookReportGenerator();
        System.out.println("📊 Book Report (only basic reporting):");
        bookReport.generateReport(java.util.Arrays.asList("Sample data"));
        
        // MemberReportGenerator implement nhiều interface
        MemberReportGenerator memberReport = new MemberReportGenerator();
        System.out.println("👥 Member Report (with export and print capabilities):");
        memberReport.generateReport(java.util.Arrays.asList("Sample data"));
        
        // Demo export capability
        if (memberReport instanceof ExportableReport) {
            ExportableReport exportable = (ExportableReport) memberReport;
            System.out.println("Supported formats: " + exportable.getSupportedFormats());
            exportable.exportToFile(new java.io.File("member_report.pdf"));
        }
        
        // Demo print capability
        if (memberReport instanceof PrintableReport) {
            PrintableReport printable = (PrintableReport) memberReport;
            printable.setPrintSettings("High Quality, Color");
            printable.printReport();
        }
        System.out.println();
    }
    
    /**
     * Demo Dependency Inversion Principle (DIP)
     * Phụ thuộc vào abstraction, không phụ thuộc vào concrete class
     */
    private static void demonstrateDIP(LibraryService libraryService) {
        System.out.println("5️⃣ DEPENDENCY INVERSION PRINCIPLE (DIP)");
        System.out.println("========================================");
        System.out.println("✓ LibraryService phụ thuộc vào interface, không phụ thuộc vào concrete class");
        System.out.println("✓ Dễ dàng thay đổi implementation mà không sửa LibraryService");
        System.out.println();
        
        System.out.println("🏛️ Library operations using injected dependencies:");
        
        try {
            // Demo mượn sách
            libraryService.borrowBook("M001", "B001");
            
            // Demo trả sách
            libraryService.returnBook("M001", "B001");
            
            // Demo thanh toán phạt
            libraryService.processFinePayment("M001", 25.0);
            
            // Demo tạo báo cáo
            libraryService.generateLibraryReport();
            
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
