package goraebob.diary.config.security.guard;

import goraebob.diary.entity.member.RoleType;
import goraebob.diary.entity.post.Post;
import goraebob.diary.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 보안 : 삭제권한 주는것
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostGuard extends Guard {
    private final PostRepository postRepository;
    private List<RoleType> roleTypes = List.of(RoleType.ROLE_ADMIN);

    @Override
    protected List<RoleType> getRoleTypes() {
        return roleTypes;
    }

    @Override
    protected boolean isResourceOwner(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> { throw new AccessDeniedException(""); });
        Long memberId = AuthHelper.extractMemberId();
        return post.getMember().getId().equals(memberId);
    }
}
