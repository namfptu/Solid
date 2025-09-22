# 🔍 Service vs Repository - Sự khác biệt rõ ràng

## 📋 Tổng quan

Trong project SOLID này, chúng ta có 2 layer chính:
- **Repository Layer**: Quản lý dữ liệu (Data Access Layer)
- **Service Layer**: Quản lý logic nghiệp vụ (Business Logic Layer)

## 🏗️ Repository Layer

### Vai trò:
- **Chỉ quản lý dữ liệu** (CRUD operations)
- **Không chứa logic nghiệp vụ**
- **Trừu tượng hóa việc truy cập dữ liệu**

### Ví dụ trong project:
```java
public class InMemoryBookRepository implements BookRepository {
    private final Map<String, Book> books = new HashMap<>();
    
    @Override
    public void save(Book book) {
        books.put(book.getId(), book);  // Chỉ lưu dữ liệu
    }
    
    @Override
    public Optional<Book> findById(String id) {
        return Optional.ofNullable(books.get(id));  // Chỉ tìm dữ liệu
    }
    
    @Override
    public List<Book> findByAuthor(String author) {
        return books.values().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());  // Chỉ lọc dữ liệu
    }
}
```

### Đặc điểm:
- ✅ Chỉ thao tác với dữ liệu
- ✅ Không có validation
- ✅ Không có business rules
- ✅ Có thể thay đổi implementation (InMemory → Database)

## 🧠 Service Layer

### Vai trò:
- **Chứa logic nghiệp vụ** (Business Logic)
- **Validation và business rules**
- **Điều phối giữa các Repository**
- **Xử lý các tình huống phức tạp**

### Ví dụ trong project:
```java
public class BookService {
    private final BookRepository bookRepository;
    
    public void addBook(Book book) {
        // 1. Validation logic (Business rules)
        if (book == null || book.getId() == null || book.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Book ID cannot be null or empty");
        }
        
        // 2. Business rule: Check if book already exists
        if (bookRepository.findById(book.getId()).isPresent()) {
            throw new IllegalStateException("Book with ID " + book.getId() + " already exists");
        }
        
        // 3. Business rule: Validate ISBN format
        if (!isValidISBN(book.getIsbn())) {
            throw new IllegalArgumentException("Invalid ISBN format: " + book.getIsbn());
        }
        
        // 4. Business rule: Set default availability
        book.setAvailable(true);
        
        // 5. Delegate to repository
        bookRepository.save(book);
        System.out.println("✅ Book added successfully: " + book.getTitle());
    }
    
    public void borrowBook(String bookId) {
        // 1. Business logic: Find book
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isEmpty()) {
            throw new IllegalArgumentException("Book not found with ID: " + bookId);
        }
        
        Book book = bookOpt.get();
        
        // 2. Business rule: Check availability
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book '" + book.getTitle() + "' is not available for borrowing");
        }
        
        // 3. Business rule: Check if book is not damaged
        if (isBookDamaged(book)) {
            throw new IllegalStateException("Book '" + book.getTitle() + "' is damaged and cannot be borrowed");
        }
        
        // 4. Business logic: Update book status
        book.setAvailable(false);
        bookRepository.update(book);
        
        System.out.println("📖 Book borrowed successfully: " + book.getTitle());
    }
    
    // Business logic methods
    private boolean isValidISBN(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return false;
        }
        return isbn.matches("\\d{3}-\\d{10}");
    }
    
    private boolean isBookDamaged(Book book) {
        return book.getTitle().toLowerCase().contains("damaged");
    }
    
    // Business logic: Get statistics
    public BookStatistics getBookStatistics() {
        List<Book> allBooks = bookRepository.findAll();
        long totalBooks = allBooks.size();
        long availableBooks = allBooks.stream().filter(Book::isAvailable).count();
        long borrowedBooks = totalBooks - availableBooks;
        
        return new BookStatistics(totalBooks, availableBooks, borrowedBooks);
    }
}
```

### Đặc điểm:
- ✅ Chứa logic nghiệp vụ phức tạp
- ✅ Validation và business rules
- ✅ Xử lý các tình huống đặc biệt
- ✅ Điều phối giữa các Repository
- ✅ Có thể gọi các Service khác

## 🔄 So sánh trực tiếp

| Aspect | Repository | Service |
|--------|------------|---------|
| **Mục đích** | Quản lý dữ liệu | Quản lý logic nghiệp vụ |
| **Validation** | ❌ Không có | ✅ Có validation phức tạp |
| **Business Rules** | ❌ Không có | ✅ Có business rules |
| **Error Handling** | ❌ Đơn giản | ✅ Xử lý lỗi phức tạp |
| **Dependencies** | Chỉ phụ thuộc vào Model | Phụ thuộc vào Repository + Service khác |
| **Testing** | Dễ test (chỉ CRUD) | Phức tạp hơn (cần mock) |
| **Thay đổi** | Ít thay đổi | Thay đổi thường xuyên |

## 🎯 Ví dụ thực tế

### Scenario: Thêm sách mới

#### Repository (Chỉ lưu dữ liệu):
```java
public void save(Book book) {
    books.put(book.getId(), book);  // Chỉ lưu, không kiểm tra gì
}
```

#### Service (Logic nghiệp vụ):
```java
public void addBook(Book book) {
    // Kiểm tra ID có tồn tại không
    if (bookRepository.findById(book.getId()).isPresent()) {
        throw new IllegalStateException("Book already exists");
    }
    
    // Kiểm tra ISBN có hợp lệ không
    if (!isValidISBN(book.getIsbn())) {
        throw new IllegalArgumentException("Invalid ISBN");
    }
    
    // Set trạng thái mặc định
    book.setAvailable(true);
    
    // Lưu vào repository
    bookRepository.save(book);
    
    // Log thành công
    System.out.println("Book added successfully");
}
```

## 🏆 Lợi ích của việc tách biệt

### 1. **Single Responsibility Principle (SRP)**
- Repository: Chỉ quản lý dữ liệu
- Service: Chỉ quản lý logic nghiệp vụ

### 2. **Dễ bảo trì**
- Thay đổi logic nghiệp vụ → Chỉ sửa Service
- Thay đổi cách lưu trữ → Chỉ sửa Repository

### 3. **Dễ test**
- Test Repository: Chỉ cần test CRUD
- Test Service: Mock Repository, test business logic

### 4. **Tái sử dụng**
- Repository có thể được dùng bởi nhiều Service
- Service có thể kết hợp nhiều Repository

## 🚀 Kết luận

**Repository** và **Service** có vai trò hoàn toàn khác nhau:

- **Repository** = "Kho dữ liệu" - Chỉ lưu trữ và truy xuất
- **Service** = "Bộ não" - Xử lý logic, quyết định, validation

Việc tách biệt rõ ràng giúp code:
- ✅ Dễ hiểu và bảo trì
- ✅ Tuân thủ SOLID principles
- ✅ Dễ test và debug
- ✅ Có thể mở rộng linh hoạt

---

**Lưu ý**: Trong project này, Service layer được thiết kế để minh họa rõ ràng sự khác biệt với Repository layer thông qua các business rules và validation phức tạp.
