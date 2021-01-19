package rz.zerock.ex3.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/*
* @Data 는 Getter/Setter, toString(), equals(), hashCode()를 자동으로 생성
* */

@Data
@Builder(toBuilder = true)
public class SampleDTO {
    private Long sno;

    private String first;

    private String last;

    private LocalDateTime regTime;
}
