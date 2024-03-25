package org.zerock.mallapi.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.zerock.mallapi.dto.MemberDTO;
import org.zerock.mallapi.util.JWTUtil;

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

        String accessToken = JWTUtil.generateToken(claims, 10);         // 당장 사용할 수 있는
        String refreshToken = JWTUtil.generateToken(claims, 60 * 24);   // 만료되었을 때 교환할 수 있도록 가지고 있는 것


        // 일반적으로 Access 토큰을 가지고 간다.(네트워크로 통신을 하는데 자주 왔다갔다 하면 보안상 좋지 않기 때문에)
        // Access 토큰은 입장권이라고 생각하면 좋다.
        // Access 토큰이 만료되면 그 때 refresh 토큰을 보내서 확인이 되면 다시 Access 토큰을 발급받는다.
        // 근데 2개 모두 보내야 하냐? 이거는 케바케 어플리케이션을 어떻게 설계하느냐에 따라 다름
        claims.put("accessToken", accessToken);
        claims.put("refreshToken", refreshToken);

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
