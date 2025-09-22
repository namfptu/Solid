package repository.impl;

import model.Book;
import repository.BookRepository;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation của BookRepository sử dụng Database
 * Minh họa Dependency Inversion Principle - có thể thay thế InMemoryBookRepository
 */
public class DatabaseBookRepository implements BookRepository {
    // Giả lập database connection
    private final Map<String, Book> database = new HashMap<>();
    
    @Override
    public void save(Book book) {
        System.out.println("💾 Saving book to database: " + book.getTitle());
        database.put(book.getId(), book);
    }
    
    @Override
    public Optional<Book> findById(String id) {
        System.out.println("🔍 Querying database for book ID: " + id);
        return Optional.ofNullable(database.get(id));
    }
    
    @Override
    public List<Book> findAll() {
        System.out.println("📋 Querying all books from database");
        return new ArrayList<>(database.values());
    }
    
    @Override
    public List<Book> findByAuthor(String author) {
        System.out.println("👤 Querying database for author: " + author);
        return database.values().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Book> findAvailableBooks() {
        System.out.println("📚 Querying available books from database");
        return database.values().stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }
    
    @Override
    public void update(Book book) {
        System.out.println("🔄 Updating book in database: " + book.getTitle());
        if (database.containsKey(book.getId())) {
            database.put(book.getId(), book);
        }
    }
    
    @Override
    public void delete(String id) {
        System.out.println("🗑️ Deleting book from database: " + id);
        database.remove(id);
    }
}
