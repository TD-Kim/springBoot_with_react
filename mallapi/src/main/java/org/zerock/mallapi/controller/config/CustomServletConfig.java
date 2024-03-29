package org.zerock.mallapi.controller.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zerock.mallapi.controller.formatter.LocalDateFormatter;

@Configuration
@Log4j2
public class CustomServletConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {

        log.info("-------------------------------------");
        log.info("addFormatters");

        registry.addFormatter(new LocalDateFormatter());
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")   // 어떤 경로에 CORS를 적용할 지
//                .maxAge(500)
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
//                .allowedOrigins("*");        // 어디에서부터 들어오는 연결을 허락할 지
//    }
}
