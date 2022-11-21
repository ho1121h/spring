package goraebob.diary.entity.post;

import goraebob.diary.dto.post.PostUpdateRequest;
import goraebob.diary.entity.common.EntityDate;
import goraebob.diary.entity.member.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity //엔티티
@Getter // 게터
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends EntityDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// pk 설정임
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // 다대 일 관계 |해결과제 |사용자는 게시글을 작성하되 혼자만 볼 수 있어야하고 상대방중 랜덤으로 보낼 수 있어야함.
    @JoinColumn(name = "member_id", nullable = false) //
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member; // 1

    public Post(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }
    public UpdatedResult update(PostUpdateRequest req) { // 1
        this.title = req.getTitle();
        this.content = req.getContent();


        return null;
    }
    @Getter
    @AllArgsConstructor
    public static class UpdatedResult { // 4
        private String title;
        private String content;
    }
// 좋아요 기능 추가해야함

}
