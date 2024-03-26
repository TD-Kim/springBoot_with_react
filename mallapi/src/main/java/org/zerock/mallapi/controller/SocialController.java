package org.zerock.mallapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.mallapi.service.MemberService;

@RestController
@Log4j2
@RequiredArgsConstructor
public class SocialController {

    private final MemberService memberService;

    @GetMapping("/api/member/kakao")
    public String[] getMemberFromKakao(String accessToken) {    // 리액트에서 넘어오는 accessToken 을 받는다

        log.info("accessToken: " + accessToken);

        memberService.getKakaoMember(accessToken);

        return new String[]{"AAA", "BBB", "CCC"};

    }

}
