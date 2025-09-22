package service;

import model.Book;
import repository.BookRepository;
import java.util.List;
import java.util.Optional;

/**
 * Service quản lý sách
 * Tuân thủ Single Responsibility Principle - chỉ xử lý logic nghiệp vụ liên quan đến sách
 */
public class BookService {
    private final BookRepository bookRepository;
    
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    public void addBook(Book book) {
        // Validation logic (Business rules)
        if (book == null || book.getId() == null || book.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Book ID cannot be null or empty");
        }
        
        // Business rule: Check if book already exists
        if (bookRepository.findById(book.getId()).isPresent()) {
            System.out.println("  🚫 Duplicate ID detected: " + book.getId());
            throw new IllegalStateException("Book with ID " + book.getId() + " already exists");
        }
        
        // Business rule: Validate ISBN format
        if (!isValidISBN(book.getIsbn())) {
            throw new IllegalArgumentException("Invalid ISBN format: " + book.getIsbn());
        }
        
        // Business rule: Set default availability
        book.setAvailable(true);
        
        // Delegate to repository
        bookRepository.save(book);
        System.out.println("✅ Book added successfully: " + book.getTitle());
    }
    
    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }
    
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public List<Book> getBooksByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty");
        }
        return bookRepository.findByAuthor(author);
    }
    
    public List<Book> getAvailableBooks() {
        return bookRepository.findAvailableBooks();
    }
    
    public void updateBook(Book book) {
        if (book == null || book.getId() == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        bookRepository.update(book);
    }
    
    public void deleteBook(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Book ID cannot be null or empty");
        }
        bookRepository.delete(id);
    }
    
    public void borrowBook(String bookId) {
        // Business logic: Find book
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isEmpty()) {
            throw new IllegalArgumentException("Book not found with ID: " + bookId);
        }
        
        Book book = bookOpt.get();
        
        // Business rule: Check availability
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book '" + book.getTitle() + "' is not available for borrowing");
        }
        
        // Business rule: Check if book is not damaged
        if (isBookDamaged(book)) {
            throw new IllegalStateException("Book '" + book.getTitle() + "' is damaged and cannot be borrowed");
        }
        
        // Business logic: Update book status
        book.setAvailable(false);
        bookRepository.update(book);
        
        System.out.println("📖 Book borrowed successfully: " + book.getTitle());
    }
    
    public void returnBook(String bookId) {
        // Business logic: Find book
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isEmpty()) {
            throw new IllegalArgumentException("Book not found with ID: " + bookId);
        }
        
        Book book = bookOpt.get();
        
        // Business rule: Check if book is already available
        if (book.isAvailable()) {
            throw new IllegalStateException("Book '" + book.getTitle() + "' is already available");
        }
        
        // Business logic: Update book status
        book.setAvailable(true);
        bookRepository.update(book);
        
        System.out.println("📚 Book returned successfully: " + book.getTitle());
    }
    
    // Business logic methods
    private boolean isValidISBN(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return false;
        }
        
        // Remove hyphens and spaces
        String cleanISBN = isbn.replaceAll("[\\s-]", "");
        
        // Check if it's ISBN-13 (13 digits)
        if (cleanISBN.length() == 13 && cleanISBN.matches("\\d{13}")) {
            return validateISBN13(cleanISBN);
        }
        
        // Check if it's ISBN-10 (10 digits)
        if (cleanISBN.length() == 10 && cleanISBN.matches("\\d{9}[\\dX]")) {
            return validateISBN10(cleanISBN);
        }
        
        return false;
    }
    
    private boolean validateISBN13(String isbn) {
        // ISBN-13 validation using check digit
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        int checkDigit = (10 - (sum % 10)) % 10;
        return checkDigit == Character.getNumericValue(isbn.charAt(12));
    }
    
    private boolean validateISBN10(String isbn) {
        // ISBN-10 validation using check digit
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(isbn.charAt(i)) * (10 - i);
        }
        int checkDigit = sum % 11;
        char lastChar = isbn.charAt(9);
        if (checkDigit == 10) {
            return lastChar == 'X';
        }
        return checkDigit == Character.getNumericValue(lastChar);
    }
    
    private boolean isBookDamaged(Book book) {
        // Business rule: Simulate damage check
        // In real application, this would check book condition
        return book.getTitle().toLowerCase().contains("damaged");
    }
    
    // Business logic: Get book statistics
    public BookStatistics getBookStatistics() {
        List<Book> allBooks = bookRepository.findAll();
        long totalBooks = allBooks.size();
        long availableBooks = allBooks.stream().filter(Book::isAvailable).count();
        long borrowedBooks = totalBooks - availableBooks;
        
        return new BookStatistics(totalBooks, availableBooks, borrowedBooks);
    }
    
    // Inner class for business data
    public static class BookStatistics {
        private final long totalBooks;
        private final long availableBooks;
        private final long borrowedBooks;
        
        public BookStatistics(long totalBooks, long availableBooks, long borrowedBooks) {
            this.totalBooks = totalBooks;
            this.availableBooks = availableBooks;
            this.borrowedBooks = borrowedBooks;
        }
        
        public long getTotalBooks() { return totalBooks; }
        public long getAvailableBooks() { return availableBooks; }
        public long getBorrowedBooks() { return borrowedBooks; }
        
        @Override
        public String toString() {
            return String.format("BookStatistics{total=%d, available=%d, borrowed=%d}", 
                               totalBooks, availableBooks, borrowedBooks);
        }
    }
}

