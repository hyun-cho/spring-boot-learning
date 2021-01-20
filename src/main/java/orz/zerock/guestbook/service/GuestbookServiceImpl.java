package orz.zerock.guestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import orz.zerock.guestbook.dto.GuestbookDTO;
import orz.zerock.guestbook.entity.Guestbook;
import orz.zerock.guestbook.repository.GuestbookRepository;

@Service
@Log4j2
@RequiredArgsConstructor // 의존성 자동 주입을 위한 어노테이션
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository repository; // 무조건 final로 선언

    @Override
    public Long register(GuestbookDTO dto) {
        log.info("DTO ------");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);

        return entity.getGno();
    }
}
