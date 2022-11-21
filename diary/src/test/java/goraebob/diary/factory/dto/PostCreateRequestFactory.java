package goraebob.diary.factory.dto;

import goraebob.diary.dto.post.PostCreateRequest;

public class PostCreateRequestFactory {
    public static PostCreateRequest createPostCreateRequest() {
        return new PostCreateRequest("title", "content", 1000L );
    }
    public static PostCreateRequest createPostCreateRequest(String title,String content,Long memberId){
        return new PostCreateRequest(title, content, memberId);
    }
    public static PostCreateRequest createPostCreateRequestWithMemberId(Long memberId){
        return new PostCreateRequest("title", "content",1L);
    }
    public static PostCreateRequest createPostCreateRequestWithTitle(String title){
        return new PostCreateRequest(title, "content",1L);
    }
    public static PostCreateRequest createPostCreateRequestWithContent(String content){
        return new PostCreateRequest("title", content,1L);
    }

}
