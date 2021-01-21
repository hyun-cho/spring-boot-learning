package org.zerock.mreview.entity;

import lombok.*;

import javax.persistence.*;

// 단방향 참조로 처리, @Query로 left join 등을 사용하게 된다.
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "movie")
public class MovieImage {           //연관 관계 주의
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long inum;

    private String uuid;

    private String imgName;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

}
