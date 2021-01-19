package rz.zerock.ex3.controller;

import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rz.zerock.ex3.dto.SampleDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


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

    // SampleDTO 타입의 객체를 20개 추가하고 이를 Model에 담아서 전송
    @GetMapping({"/ex2", "/exLink"})
    public void exModel(Model model) {
        List<SampleDTO> list = IntStream.rangeClosed(1, 20).asLongStream().
                mapToObj( i -> {
                    SampleDTO dto = SampleDTO.builder()
                            .sno(i)
                            .first("First.."+i)
                            .last("Last.."+i)
                            .regTime(LocalDateTime.now())
                            .build();
                    return dto;
                }).collect(Collectors.toList());
        model.addAttribute("list", list);
    }

    @GetMapping({"/exInline"})
    public String exInline(RedirectAttributes redirectAttributes) {
        log.info("exInline.....");

        SampleDTO dto = SampleDTO.builder()
                .sno(100L)
                .first("Frist....100")
                .last("Last....100")
                .regTime(LocalDateTime.now())
                .build();
        redirectAttributes.addFlashAttribute("result", "success");
        redirectAttributes.addFlashAttribute("dto", dto);

        return "redirect:/sample/ex3";
    }

    @GetMapping("/ex3")
    public void ex3() {
        log.info("ex3");
    }

    @GetMapping({"/exLayout1", "/exLayout2", "/exTemplate", "/exSidebar"})
    public void exLayout1() {
        log.info("exLayout....");
    }
}
