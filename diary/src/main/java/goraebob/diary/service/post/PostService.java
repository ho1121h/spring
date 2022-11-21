package goraebob.diary.service.post;

import goraebob.diary.dto.post.*;
import goraebob.diary.entity.member.Member;
import goraebob.diary.entity.post.Post;
import goraebob.diary.exception.MemberNotFoundException;
import goraebob.diary.exception.PostNotFoundException;
import goraebob.diary.repository.member.MemberRepository;
import goraebob.diary.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// 게시판 작성 서비스 | dto.post.PostCreateRequest 에 접근함
@Service // 비즈니스 로직을 수행하는 Class
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public PostCreateResponse create(PostCreateRequest req) {
        Member member = memberRepository.findById(req.getMemberId()).orElseThrow(MemberNotFoundException::new);
        Post post = postRepository.save(
                PostCreateRequest.toEntity(
                        req,
                        memberRepository

                )
        );
//        uploadImages(post.getImages(), req.getImages());
        return new PostCreateResponse(post.getId());
    }
    // PostService.java
    public PostDto read(Long id) {
        return PostDto.toDto(postRepository.findById(id).orElseThrow(PostNotFoundException::new));
    }
    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
//        deleteImages(post.getImages()); 만약 이미지를 추가한다면 사용하고...
        postRepository.delete(post);
    }

    @Transactional
    public PostUpdateResponse update(Long id, PostUpdateRequest req) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        Post.UpdatedResult result = post.update(req);
//        uploadImages(result.getAddedImages(), result.getAddedImageFiles());
//        deleteImages(result.getDeletedImages());
        return new PostUpdateResponse(id);
    }
//
//    private void uploadImages(List<Image> images, List<MultipartFile> fileImages) {
//        IntStream.range(0, images.size()).forEach(i -> fileService.upload(fileImages.get(i), images.get(i).getUniqueName()));
//    }
//
//    private void deleteImages(List<Image> images) {
//        images.stream().forEach(i -> fileService.delete(i.getUniqueName()));
//    }
}
