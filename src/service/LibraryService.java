package service;

import model.Book;
import model.Member;
import notification.NotificationService;
import payment.PaymentProcessor;
import repository.BookRepository;
import repository.MemberRepository;
import report.ReportGenerator;

import java.util.List;

/**
 * Service chính của thư viện
 * Tuân thủ Dependency Inversion Principle - phụ thuộc vào abstraction, không phụ thuộc vào concrete class
 */
public class LibraryService {
    private final BookService bookService;
    private final MemberService memberService;
    private final NotificationService notificationService;
    private final PaymentProcessor paymentProcessor;
    private final ReportGenerator reportGenerator;
    
    public LibraryService(BookService bookService, 
                         MemberService memberService,
                         NotificationService notificationService,
                         PaymentProcessor paymentProcessor,
                         ReportGenerator reportGenerator) {
        this.bookService = bookService;
        this.memberService = memberService;
        this.notificationService = notificationService;
        this.paymentProcessor = paymentProcessor;
        this.reportGenerator = reportGenerator;
    }
    
    public void borrowBook(String memberId, String bookId) {
        // Kiểm tra thành viên
        var member = memberService.getMemberById(memberId);
        if (member.isEmpty()) {
            throw new IllegalArgumentException("Member not found");
        }
        
        // Mượn sách
        bookService.borrowBook(bookId);
        
        // Gửi thông báo
        notificationService.sendNotification(
            "You have successfully borrowed book with ID: " + bookId,
            member.get().getEmail()
        );
        
        System.out.println("✅ Book borrowed successfully!");
    }
    
    public void returnBook(String memberId, String bookId) {
        // Kiểm tra thành viên
        var member = memberService.getMemberById(memberId);
        if (member.isEmpty()) {
            throw new IllegalArgumentException("Member not found");
        }
        
        // Trả sách
        bookService.returnBook(bookId);
        
        // Gửi thông báo
        notificationService.sendNotification(
            "You have successfully returned book with ID: " + bookId,
            member.get().getEmail()
        );
        
        System.out.println("✅ Book returned successfully!");
    }
    
    public void processFinePayment(String memberId, double amount) {
        // Kiểm tra thành viên
        var member = memberService.getMemberById(memberId);
        if (member.isEmpty()) {
            throw new IllegalArgumentException("Member not found");
        }
        
        // Xử lý thanh toán
        boolean success = paymentProcessor.processPayment(amount, memberId);
        if (success) {
            notificationService.sendNotification(
                "Fine payment of $" + amount + " processed successfully",
                member.get().getEmail()
            );
            System.out.println("✅ Fine payment processed successfully!");
        } else {
            System.out.println("❌ Payment failed!");
        }
    }
    
    public void generateLibraryReport() {
        List<Book> books = bookService.getAllBooks();
        List<Member> members = memberService.getAllMembers();
        
        System.out.println("📚 LIBRARY REPORT");
        System.out.println("=================");
        System.out.println("Total Books: " + books.size());
        System.out.println("Total Members: " + members.size());
        System.out.println("Available Books: " + bookService.getAvailableBooks().size());
        System.out.println();
        
        // Sử dụng report generator
        reportGenerator.generateReport(books);
    }
}

