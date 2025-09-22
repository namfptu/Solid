package model;

/**
 * Lớp Member đại diện cho thành viên thư viện
 * Tuân thủ Single Responsibility Principle - chỉ quản lý thông tin thành viên
 */
public class Member {
    private String id;
    private String name;
    private String email;
    private String phone;
    
    public Member(String id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    
    // Getters và Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    @Override
    public String toString() {
        return String.format("Member{id='%s', name='%s', email='%s', phone='%s'}", 
                           id, name, email, phone);
    }
}

