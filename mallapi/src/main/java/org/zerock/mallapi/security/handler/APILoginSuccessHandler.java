package org.zerock.mallapi.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.zerock.mallapi.dto.MemberDTO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {   // security config 쪽에서 추가해서 쓸 것이기 때문에 얘는 스프링빈으로 등록하지 않는다.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("-----------------");
        log.info(authentication);
        log.info("-----------------");

        // 인증 객체에서 정보를 꺼낸다. 여기까지 왔으면 이미 로그인은 성공한것.
        MemberDTO memberDTO = (MemberDTO) authentication.getPrincipal();

        Map<String, Object> claims = memberDTO.getClaims();

        claims.put("accessToken", "");
        claims.put("refreshToken", "");

        // Gson을 사용하여 claims 를 json 문자열로 바꾼다.
        Gson gson = new Gson();
        String jsonStr = gson.toJson(claims);

        // 전송
        response.setContentType("application/json; charset=UTF-8");

        // 화면에 출력
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);
        printWriter.close();

    }
}
