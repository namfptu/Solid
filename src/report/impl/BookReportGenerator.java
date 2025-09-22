package report.impl;

import model.Book;
import report.ReportGenerator;
import java.util.List;

/**
 * Báo cáo sách - chỉ implement ReportGenerator
 * Tuân thủ Interface Segregation Principle - không bị buộc implement các method không cần thiết
 */
public class BookReportGenerator implements ReportGenerator {
    @Override
    public void generateReport(List<?> data) {
        System.out.println("📊 BOOK REPORT");
        System.out.println("==============");
        for (Object item : data) {
            if (item instanceof Book) {
                Book book = (Book) item;
                System.out.println("Title: " + book.getTitle());
                System.out.println("Author: " + book.getAuthor());
                System.out.println("Available: " + (book.isAvailable() ? "Yes" : "No"));
                System.out.println("---");
            }
        }
    }
    
    @Override
    public String getReportType() {
        return "Book Report";
    }
}

