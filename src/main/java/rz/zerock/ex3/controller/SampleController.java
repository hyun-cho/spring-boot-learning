package rz.zerock.ex3.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/*
* @Log4j2 의 경우에 Lombok의 기능으로 스프링 부트가 로그 라이브러리 중 스프링부트에서 적용가능
* */
@Controller
@RequestMapping("/sample")
@Log4j2
public class SampleController {
    @GetMapping("/ex1")
    public void ex1() {
        log.info("ex1....");
    }
}
