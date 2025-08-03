package org.bee.metro.core.member.api;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.member.application.MemberService;
import org.bee.metro.core.member.domain.Member;
import org.bee.metro.core.member.dto.MemberPayload;
import org.bee.metro.global.auth.annotation.Login;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberApi {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<MemberPayload> getMember(@Login UUID memberId) {
        Member member = memberService.findMemberById(memberId);
        return ResponseEntity.ok(MemberPayload.createByMember(member));
    }
}
