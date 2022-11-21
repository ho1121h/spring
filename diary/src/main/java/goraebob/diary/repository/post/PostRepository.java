package goraebob.diary.repository.post;

import goraebob.diary.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

// 게시글 데이터 접근 계층                             리포지터리<엔티티 ,일반키 타입>
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p join fetch p.member where p.id = :id")
    // select * from posts where members_member_idx = 1;
    Optional<Post> findByIdWithMember(Long id);


}

/** 쿼리문으로 구현 해보자 => 그래서 어떻게함?
 * # 1.내글만 보기
 * select * from posts where members_member_idx = 1;
 *
 * # 2. 내가 초대 된 글만 보기
 * select * from invite;
 *
 * select invite.use_flag , posts.* from
 * (select * from invite where members_member_idx = 1) as invite
 * left join
 * (select * from posts) as posts
 * on invite.posts_posts_idx = posts.posts_idx
 * where use_flag = "false";
 *
 *
 * 쿼리를 바꿀 필욘 없고, 권한 테이블을 구현한뒤 api로 제한하거나 권한의 별도 로직으로 요청 자체를 제한하면 될 것같다.
 */

