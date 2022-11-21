package goraebob.diary.factory.entity;

import goraebob.diary.entity.member.Member;
import goraebob.diary.entity.post.Post;

import java.util.List;

import static goraebob.diary.factory.entity.MemberFactory.createMember;

public class PostFactory {
    public static Post createPost() {
        return createPost(createMember());
    }

    public static Post createPost(Member member) {
        return new Post("title", "content",  member );
    }





    }