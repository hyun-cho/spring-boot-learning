package orz.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import orz.zerock.guestbook.dto.PageRequestDTO;
import orz.zerock.guestbook.service.GuestbookService;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor // 자동 주입을 위한 Annotation
public class GuestbookController {

    private final GuestbookService service; // final로 선언!

    /*
    * guestbook의 기본 루트와 list
    * */
    @GetMapping("/")
    public String index() {
        return "redirect:/guestbook/list";
    }

    @GetMapping({"/list"})
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        log.info("list ........");

        model.addAttribute("result", service.getList(pageRequestDTO));

    }
}
