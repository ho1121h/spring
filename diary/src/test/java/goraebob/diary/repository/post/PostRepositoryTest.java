package goraebob.diary.repository.post;

import goraebob.diary.entity.member.Member;
import goraebob.diary.entity.post.Post;
import goraebob.diary.exception.PostNotFoundException;
import goraebob.diary.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static goraebob.diary.factory.entity.MemberFactory.createMember;
import static goraebob.diary.factory.entity.PostFactory.createPost;
import static org.junit.jupiter.api.Assertions.*;
//import static org.slf4j.MDC.clear;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
@DataJpaTest
class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberRepository memberRepository;
    @PersistenceContext
    EntityManager em;

    Member member;

    @BeforeEach
    void beforeEach() {
        member = memberRepository.save(createMember());
    }

    @Test
    void createAndReadTest() { // 생성 및 조회 검증
        // given
        Post post = postRepository.save(createPost(member));
        clear();

        // when
        Post foundPost = postRepository.findById(post.getId()).orElseThrow(PostNotFoundException::new);

        // then
        assertThat(foundPost.getId()).isEqualTo(post.getId());
        assertThat(foundPost.getTitle()).isEqualTo(post.getTitle());

    }

    @Test
    void deleteTest() { // 삭제 검증
        // given
        Post post = postRepository.save(createPost(member));
        clear();

        // when
        postRepository.deleteById(post.getId());
        clear();

        // then
        assertThatThrownBy(() -> postRepository.findById(post.getId()).orElseThrow(PostNotFoundException::new))
                .isInstanceOf(PostNotFoundException.class);


    }

    @Test
    void deleteCascadeByMemberTest() { // Member가 삭제되었을 때 연쇄적으로 Post도 삭제되는지 검증
        // given
        postRepository.save(createPost(member));
        clear();

        // when
        memberRepository.deleteById(member.getId());
        clear();

        // then
        List<Post> result = postRepository.findAll();
        Assertions.assertThat(result.size()).isZero();
    }
//    @Test
//    void findByIdWithMemberTest() {
//        // given
//        Post post = postRepository.save(createPost(member));
//
//        // when
//        Post foundPost = postRepository.findByIdWithMember(post.getId()).orElseThrow(PostNotFoundException::new);
//
//        // then
//        Member foundMember = foundPost.getMember();
//        assertThat(foundMember.getEmail()).isEqualTo(member.getEmail());
//    }
    void clear() {
        em.flush();
        em.clear();
}
}