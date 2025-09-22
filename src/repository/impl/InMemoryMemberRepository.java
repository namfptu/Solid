package repository.impl;

import model.Member;
import repository.MemberRepository;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation của MemberRepository sử dụng bộ nhớ
 * Tuân thủ Single Responsibility Principle - chỉ quản lý lưu trữ thành viên
 */
public class InMemoryMemberRepository implements MemberRepository {
    private final Map<String, Member> members = new HashMap<>();
    
    @Override
    public void save(Member member) {
        members.put(member.getId(), member);
    }
    
    @Override
    public Optional<Member> findById(String id) {
        return Optional.ofNullable(members.get(id));
    }
    
    @Override
    public List<Member> findAll() {
        return new ArrayList<>(members.values());
    }
    
    @Override
    public List<Member> findByName(String name) {
        return members.values().stream()
                .filter(member -> member.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    @Override
    public void update(Member member) {
        if (members.containsKey(member.getId())) {
            members.put(member.getId(), member);
        }
    }
    
    @Override
    public void delete(String id) {
        members.remove(id);
    }
}

