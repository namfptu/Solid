package repository.impl;

import model.Book;
import repository.BookRepository;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation của BookRepository sử dụng bộ nhớ
 * Tuân thủ Single Responsibility Principle - chỉ quản lý lưu trữ sách
 */
public class InMemoryBookRepository implements BookRepository {
    private final Map<String, Book> books = new HashMap<>();
    
    @Override
    public void save(Book book) {
        books.put(book.getId(), book);
    }
    
    @Override
    public Optional<Book> findById(String id) {
        return Optional.ofNullable(books.get(id));
    }
    
    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }
    
    @Override
    public List<Book> findByAuthor(String author) {
        return books.values().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Book> findAvailableBooks() {
        return books.values().stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }
    
    @Override
    public void update(Book book) {
        if (books.containsKey(book.getId())) {
            books.put(book.getId(), book);
        }
    }
    
    @Override
    public void delete(String id) {
        books.remove(id);
    }
}

