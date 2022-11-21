package goraebob.diary.service.post;

import goraebob.diary.dto.post.PostCreateRequest;
import goraebob.diary.dto.post.PostDto;
import goraebob.diary.entity.post.Post;
import goraebob.diary.exception.MemberNotFoundException;
import goraebob.diary.exception.PostNotFoundException;
import goraebob.diary.repository.member.MemberRepository;
import goraebob.diary.repository.post.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static goraebob.diary.factory.dto.PostCreateRequestFactory.createPostCreateRequest;
import static goraebob.diary.factory.entity.MemberFactory.createMember;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static goraebob.diary.factory.entity.PostFactory.createPost;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @InjectMocks
    PostService postService;
    @Mock
    PostRepository postRepository;
    @Mock
    MemberRepository memberRepository;

    @Test
    void createTest() {
        // given
        PostCreateRequest req = createPostCreateRequest();
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(createMember()));
        // when
        postService.create(req);
        // then
        verify(postRepository).save(any());
    }
    @Test
    void createExceptionByMemberNotFoundTest() {
        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));

        // when, then
        assertThatThrownBy(() -> postService.create(createPostCreateRequest())).isInstanceOf(MemberNotFoundException.class);
    }
//    @Test
//    void readTest() {
//        // given
//        Post post = createPostWithImages(List.of(createImage(), createImage()));
//        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
//
//        // when
//        PostDto postDto = postService.read(1L);
//
//        // then
//        assertThat(postDto.getTitle()).isEqualTo(post.getTitle());
////        assertThat(postDto.getImages().size()).isEqualTo(post.getImages().size());
//    }

    @Test
    void readExceptionByPostNotFoundTest() {
        // given
        given(postRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));

        // when, then
        assertThatThrownBy(() -> postService.read(1L)).isInstanceOf(PostNotFoundException.class);
    }
    @Test
    void deleteTest() {
        // given
        Post post = createPost();
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));

        // when
        postService.delete(1L);

        // then
//        verify(fileService, times(post.getImages().size())).delete(anyString());
        verify(postRepository).delete(any());
    }

    @Test
    void deleteExceptionByNotFoundPostTest() {
        // given
        given(postRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));

        // when, then
        assertThatThrownBy(() -> postService.delete(1L)).isInstanceOf(PostNotFoundException.class);
    }

}