package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{ // alt + 엔터
    private static Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member; //id 저장
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
         return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();//끝까지 찾고 없으면 null
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}
