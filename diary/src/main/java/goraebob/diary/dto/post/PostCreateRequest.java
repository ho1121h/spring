package goraebob.diary.dto.post;

import goraebob.diary.entity.post.Post;
import goraebob.diary.exception.MemberNotFoundException;
import goraebob.diary.repository.member.MemberRepository;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
//import java.awt.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@ApiModel(value = "게시글 생성 요청")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {

    @ApiModelProperty(value = "게시글 제목", notes = "게시글 제목을 입력해주세요", required = true, example = "my title")
    @NotBlank(message = "게시글 제목을 입력해주세요.")
    private String title;

    @ApiModelProperty(value = "게시글 본문", notes = "게시글 본문을 입력해주세요", required = true, example = "my content")
    @NotBlank(message = "게시글 본문을 입력해주세요.")
    private String content;

    @ApiModelProperty(hidden = true)
    @Null
    private Long memberId;

//


    public static Post toEntity(PostCreateRequest req, MemberRepository memberRepository ) {
        return new Post(
                req.title,
                req.content,
                memberRepository.findById(req.getMemberId()).orElseThrow(MemberNotFoundException::new)
//                categoryRepository.findById(req.getCategoryId()).orElseThrow(CategoryNotFoundException::new),
//                req.images.stream().map(i -> new Image(i.getOriginalFilename())).collect(toList())
        );
    }
}
