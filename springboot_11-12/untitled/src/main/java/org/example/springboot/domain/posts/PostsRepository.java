package org.example.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
// DB 접근자 //레이어 <엔티티 클래스, 기본키타입> 으로 생성함

public interface PostsRepository extends JpaRepository<Posts, Long> {
}
