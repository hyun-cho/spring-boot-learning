package orz.zerock.guestbook.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import orz.zerock.guestbook.dto.GuestbookDTO;
import orz.zerock.guestbook.dto.PageRequestDTO;
import orz.zerock.guestbook.dto.PageResultDTO;
import orz.zerock.guestbook.entity.Guestbook;
import orz.zerock.guestbook.entity.QGuestbook;
import orz.zerock.guestbook.repository.GuestbookRepository;

import java.util.Optional;
import java.util.function.Function;

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

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO); // 검색 조건 처리

        Page<Guestbook> result = repository.findAll(booleanBuilder, pageable); // Querydsl 사용

        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    //gno를 받아 findById로 결과를 조회하고, 존재하면 entityToDto를 사용해 반환
    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> result = repository.findById(gno);

        return result.isPresent()? entityToDto(result.get()): null;
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO dto) {
        // 업데이트 내용은 제목, 내용
        Optional<Guestbook> result = repository.findById(dto.getGno());

        if(result.isPresent()) {
            Guestbook entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }
    }

    /*
    * Querydsl을 통해서 BooleanBuilder를 작성하고, Repository는 Query로 작성된 것을 findAll()처리하는데 사용
    * 별도의 클래스를 작성하거나, 이 곳에 메서드를 만들어 관리
    *
    * PageRequestDTO를 파라미터로 받아서 검색 조건이 있는 경우 conditionBuilder 변수를 생성해 검색조건을 or로 연결해 처ㅣ
    * 검색 조건이 없다면 gno>0 으로만 생성
    * */
    private BooleanBuilder getSearch(PageRequestDTO requestDTO) { //Querydsl 처리
        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qGuestbook.gno.gt(0L); //gno > 0

        booleanBuilder.and(expression);

        if(type == null || type.trim().length()==0 ) { // 검색 조건이 없는 경우
            return booleanBuilder;
        }

        //검색 조건 작성
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t"))
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        if (type.contains("c"))
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        if (type.contains("w"))
            conditionBuilder.or(qGuestbook.writer.contains(keyword));

        // 모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }

}
