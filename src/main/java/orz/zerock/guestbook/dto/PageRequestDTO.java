package orz.zerock.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


/*
* 화면에서 전달되는 page라는 파라미터와 size라는 파라미터를 수집하는 역할
* 페이지 번호등은 기본값을 가지는게 좋기 때문에, 1, 10등 사용
* JPA쪽에서 사용하는 Pageable 타입의 객체를 생성하는것이 목표
* */
@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
    private int page;
    private int size;

    public PageRequestDTO () {
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
    }
}
