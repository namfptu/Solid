package model;

/**
 * Lớp Book đại diện cho một cuốn sách trong thư viện
 * Tuân thủ Single Responsibility Principle - chỉ quản lý thông tin sách
 */
public class Book {
    private String id;
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;
    
    public Book(String id, String title, String author, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
    }
    
    // Getters và Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
    
    @Override
    public String toString() {
        return String.format("Book{id='%s', title='%s', author='%s', isbn='%s', available=%s}", 
                           id, title, author, isbn, isAvailable);
    }
}

