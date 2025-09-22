# 📚 ISBN - International Standard Book Number

## 🎯 ISBN là gì?

**ISBN** (International Standard Book Number) là một mã số duy nhất được gán cho mỗi cuốn sách để:

- **Nhận diện sách** một cách chính xác trên toàn thế giới
- **Phân biệt** các phiên bản khác nhau của cùng một cuốn sách
- **Quản lý** sách trong hệ thống thư viện, nhà sách, và xuất bản
- **Tìm kiếm** và tra cứu sách dễ dàng

## 🔢 Cấu trúc ISBN

### **ISBN-13** (Chuẩn hiện tại):
```
978-0-13-235088-4
│   │ │  │   │   │
│   │ │  │   │   └─ Check digit (số kiểm tra)
│   │ │  │   └───── Book identifier (mã sách)
│   │ │  └───────── Publisher identifier (mã nhà xuất bản)
│   │ └──────────── Group identifier (mã nhóm ngôn ngữ/vùng)
│   └────────────── Prefix (tiền tố)
└────────────────── EAN prefix (tiền tố EAN)
```

### **ISBN-10** (Chuẩn cũ):
```
0-13-235088-2
│ │  │   │   │
│ │  │   │   └─ Check digit (số kiểm tra)
│ │  │   └───── Book identifier (mã sách)
│ │  └───────── Publisher identifier (mã nhà xuất bản)
│ └──────────── Group identifier (mã nhóm ngôn ngữ/vùng)
└────────────── Group identifier (mã nhóm ngôn ngữ/vùng)
```

## 🏗️ Các thành phần ISBN

### **1. EAN Prefix (ISBN-13)**:
- **978**: Mã EAN cho sách
- **979**: Mã EAN thay thế (khi 978 hết)

### **2. Group Identifier**:
- **0**: Tiếng Anh (Mỹ, Anh, Canada, Australia)
- **1**: Tiếng Anh (Nam Phi, New Zealand)
- **2**: Tiếng Pháp
- **3**: Tiếng Đức
- **4**: Nhật Bản
- **5**: Nga
- **7**: Trung Quốc
- **8**: Ấn Độ
- **9**: Các nước khác

### **3. Publisher Identifier**:
- Mã nhà xuất bản (2-7 chữ số)
- Nhà xuất bản lớn: mã ngắn
- Nhà xuất bản nhỏ: mã dài

### **4. Book Identifier**:
- Mã sách cụ thể (1-6 chữ số)
- Được nhà xuất bản gán

### **5. Check Digit**:
- Số kiểm tra để đảm bảo tính chính xác
- Được tính toán từ các chữ số trước đó

## 🧮 Cách tính Check Digit

### **ISBN-13**:
```java
// Ví dụ: 978-0-13-235088-4
// Bước 1: Lấy 12 chữ số đầu: 978013235088
// Bước 2: Tính tổng có trọng số
sum = 9×1 + 7×3 + 8×1 + 0×3 + 1×1 + 3×3 + 2×1 + 3×3 + 5×1 + 0×3 + 8×1 + 8×3
sum = 9 + 21 + 8 + 0 + 1 + 9 + 2 + 9 + 5 + 0 + 8 + 24 = 96
// Bước 3: Tính check digit
checkDigit = (10 - (sum % 10)) % 10 = (10 - (96 % 10)) % 10 = (10 - 6) % 10 = 4
```

### **ISBN-10**:
```java
// Ví dụ: 0-13-235088-2
// Bước 1: Lấy 9 chữ số đầu: 013235088
// Bước 2: Tính tổng có trọng số
sum = 0×10 + 1×9 + 3×8 + 2×7 + 3×6 + 5×5 + 0×4 + 8×3 + 8×2
sum = 0 + 9 + 24 + 14 + 18 + 25 + 0 + 24 + 16 = 130
// Bước 3: Tính check digit
checkDigit = sum % 11 = 130 % 11 = 9
// Nếu checkDigit = 10, dùng 'X'
```

## 💻 Implementation trong Project SOLID

### **Validation Logic**:
```java
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
```

### **ISBN-13 Validation**:
```java
private boolean validateISBN13(String isbn) {
    int sum = 0;
    for (int i = 0; i < 12; i++) {
        int digit = Character.getNumericValue(isbn.charAt(i));
        sum += (i % 2 == 0) ? digit : digit * 3;
    }
    int checkDigit = (10 - (sum % 10)) % 10;
    return checkDigit == Character.getNumericValue(isbn.charAt(12));
}
```

### **ISBN-10 Validation**:
```java
private boolean validateISBN10(String isbn) {
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
```

## 🎯 Tại sao ISBN quan trọng trong Project SOLID?

### **1. Business Rule Validation**:
- Đảm bảo dữ liệu sách chính xác
- Tuân thủ chuẩn quốc tế
- Tránh lỗi nhập liệu

### **2. Data Integrity**:
- Mỗi cuốn sách có mã định danh duy nhất
- Dễ dàng tra cứu và quản lý
- Tránh nhầm lẫn giữa các phiên bản

### **3. International Standard**:
- Tuân thủ chuẩn quốc tế ISO 2108
- Tương thích với hệ thống toàn cầu
- Dễ dàng tích hợp với API bên ngoài

## 📝 Ví dụ thực tế

### **Sách "Clean Code"**:
- **ISBN-13**: 978-0-13-235088-4
- **ISBN-10**: 0-13-235088-2
- **Nhà xuất bản**: Prentice Hall (013)
- **Ngôn ngữ**: Tiếng Anh (0)

### **Sách "Design Patterns"**:
- **ISBN-13**: 978-0-20-163361-0
- **ISBN-10**: 0-20-163361-2
- **Nhà xuất bản**: Addison-Wesley (020)
- **Ngôn ngữ**: Tiếng Anh (0)

## 🚀 Kết luận

ISBN là một chuẩn quốc tế quan trọng giúp:

- ✅ **Nhận diện sách** chính xác
- ✅ **Quản lý dữ liệu** hiệu quả
- ✅ **Tích hợp hệ thống** dễ dàng
- ✅ **Tuân thủ chuẩn** quốc tế
- ✅ **Tránh lỗi** nhập liệu

Trong project SOLID, ISBN validation thể hiện:
- **Single Responsibility**: Service chỉ validate ISBN
- **Business Rules**: Đảm bảo dữ liệu chính xác
- **Error Handling**: Xử lý lỗi validation rõ ràng

---

**Lưu ý**: Validation ISBN trong project này được đơn giản hóa để demo. Trong thực tế, cần validation phức tạp hơn và có thể sử dụng thư viện chuyên dụng.

