package repository;

import model.Book;
import java.util.List;
import java.util.Optional;

/**
 * Interface cho việc quản lý dữ liệu sách
 * Tuân thủ Dependency Inversion Principle - phụ thuộc vào abstraction
 */
public interface BookRepository {
    void save(Book book);
    Optional<Book> findById(String id);
    List<Book> findAll();
    List<Book> findByAuthor(String author);
    List<Book> findAvailableBooks();
    void update(Book book);
    void delete(String id);
}

