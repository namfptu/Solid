package repository;

import model.Member;
import java.util.List;
import java.util.Optional;

/**
 * Interface cho việc quản lý dữ liệu thành viên
 * Tuân thủ Dependency Inversion Principle - phụ thuộc vào abstraction
 */
public interface MemberRepository {
    void save(Member member);
    Optional<Member> findById(String id);
    List<Member> findAll();
    List<Member> findByName(String name);
    void update(Member member);
    void delete(String id);
}

