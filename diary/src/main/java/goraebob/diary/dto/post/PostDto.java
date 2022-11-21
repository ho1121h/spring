package goraebob.diary.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import goraebob.diary.dto.member.MemberDto;
import goraebob.diary.entity.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private MemberDto member;
//    private List<ImageDto> images;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt; //작성일 타임 스탬프
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;   // 수정일 타임 스탬프


    public static PostDto toDto(Post post) {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                MemberDto.toDto(post.getMember()),
//                post.getImages().stream().map(i -> ImageDto.toDto(i)).collect(toList())
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }
}