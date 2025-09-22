package service;

import model.Member;
import repository.MemberRepository;
import java.util.List;
import java.util.Optional;

/**
 * Service quản lý thành viên
 * Tuân thủ Single Responsibility Principle - chỉ xử lý logic nghiệp vụ liên quan đến thành viên
 */
public class MemberService {
    private final MemberRepository memberRepository;
    
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    
    public void registerMember(Member member) {
        // Validation logic (Business rules)
        if (member == null || member.getId() == null || member.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Member ID cannot be null or empty");
        }
        
        // Business rule: Check if member already exists
        if (memberRepository.findById(member.getId()).isPresent()) {
            throw new IllegalStateException("Member with ID " + member.getId() + " already exists");
        }
        
        // Business rule: Validate email format
        if (member.getEmail() == null || !isValidEmail(member.getEmail())) {
            throw new IllegalArgumentException("Invalid email format: " + member.getEmail());
        }
        
        // Business rule: Check if email is already registered
        if (isEmailAlreadyRegistered(member.getEmail())) {
            throw new IllegalStateException("Email " + member.getEmail() + " is already registered");
        }
        
        // Business rule: Validate phone number
        if (member.getPhone() != null && !isValidPhone(member.getPhone())) {
            throw new IllegalArgumentException("Invalid phone number format: " + member.getPhone());
        }
        
        // Delegate to repository
        memberRepository.save(member);
        System.out.println("✅ Member registered successfully: " + member.getName());
    }
    
    public Optional<Member> getMemberById(String id) {
        return memberRepository.findById(id);
    }
    
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
    
    public List<Member> searchMembersByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        return memberRepository.findByName(name);
    }
    
    public void updateMember(Member member) {
        if (member == null || member.getId() == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        if (member.getEmail() != null && !isValidEmail(member.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        memberRepository.update(member);
    }
    
    public void deleteMember(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Member ID cannot be null or empty");
        }
        memberRepository.delete(id);
    }
    
    private boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
    
    private boolean isValidPhone(String phone) {
        // Business rule: Vietnamese phone number validation
        return phone != null && phone.matches("^0[0-9]{9,10}$");
    }
    
    private boolean isEmailAlreadyRegistered(String email) {
        // Business logic: Check if email is already used
        return memberRepository.findAll().stream()
                .anyMatch(member -> email.equals(member.getEmail()));
    }
    
    // Business logic: Get member statistics
    public MemberStatistics getMemberStatistics() {
        List<Member> allMembers = memberRepository.findAll();
        long totalMembers = allMembers.size();
        long membersWithPhone = allMembers.stream()
                .filter(member -> member.getPhone() != null && !member.getPhone().trim().isEmpty())
                .count();
        
        return new MemberStatistics(totalMembers, membersWithPhone);
    }
    
    // Inner class for business data
    public static class MemberStatistics {
        private final long totalMembers;
        private final long membersWithPhone;
        
        public MemberStatistics(long totalMembers, long membersWithPhone) {
            this.totalMembers = totalMembers;
            this.membersWithPhone = membersWithPhone;
        }
        
        public long getTotalMembers() { return totalMembers; }
        public long getMembersWithPhone() { return membersWithPhone; }
        
        @Override
        public String toString() {
            return String.format("MemberStatistics{total=%d, withPhone=%d}", 
                               totalMembers, membersWithPhone);
        }
    }
}

