package goraebob.diary.controller.member;

import goraebob.diary.dto.response.Response;
import goraebob.diary.service.member.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@Api(value = "Member Controller", tags = "Member")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController { //사용자의 id로 조회와 삭제 작업을 수행

    private final MemberService memberService;

    @ApiOperation(value = "사용자 정보 조회", notes = "사용자 정보를 조회한다.")
    @GetMapping("/api/members/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response read(@PathVariable Long id) {
        return Response.success(memberService.read(id));
    }

    @ApiOperation(value = "사용자 정보 삭제", notes = "사용자 정보를 삭제한다.")
    @DeleteMapping("/api/members/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response delete(@PathVariable Long id) {
        memberService.delete(id);
        return Response.success();
    }
}
/**
 * 사용자가 게시글을 삭제 요청시
 * DELETE /api/posts/번호
 * 로 요청을 전송하게 됨
 * 이와 같은 명령을 수행하기위해 검증이 필요함
 * 1. 로그인된 사용자
 * 2. 요청을 보낸 사용자가 게시글의 소유주여야함 혹은 관리자여야함
 */
