package org.zerock.mreview.service;

import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

import java.util.List;

public interface ReviewService {
    //영화의 모든 영화 리뷰를 가져온다.
    List<ReviewDTO> getListOfMovie(Long mno);

    //영화 리뷰를 추가
    Long register(ReviewDTO movieReviewDTO);

    //특정한 영화리뷰 수정
    void modify(ReviewDTO movieReviewDTO);

    //영화 리뷰 삭제
    void remove(Long reviewnum);

    default Review dtoToEntity(ReviewDTO moviewReviewDTO) {
        Review movieReview = null;
        System.out.println(moviewReviewDTO);
        if(moviewReviewDTO.getReviewnum() == null) {
            movieReview = Review.builder()
                    .movie(Movie.builder().mno(moviewReviewDTO.getMno()).build())
                    .member(Member.builder().mid(moviewReviewDTO.getMid()).build())
                    .grade(moviewReviewDTO.getGrade())
                    .text(moviewReviewDTO.getText())
                    .build();
        } else {
            movieReview = Review.builder()
                    .reviewnum(moviewReviewDTO.getReviewnum())
                    .movie(Movie.builder().mno(moviewReviewDTO.getMno()).build())
                    .member(Member.builder().mid(moviewReviewDTO.getMid()).build())
                    .grade(moviewReviewDTO.getGrade())
                    .text(moviewReviewDTO.getText())
                    .build();
        }
        return movieReview;
    }

    default ReviewDTO entityToDto(Review movieReview) {
        ReviewDTO movieReviewDTO = ReviewDTO.builder()
                .reviewnum(movieReview.getReviewnum())
                .mno(movieReview.getMovie().getMno())
                .mid(movieReview.getMember().getMid())
                .nickname(movieReview.getMember().getNickname())
                .email(movieReview.getMember().getEmail())
                .grade(movieReview.getGrade())
                .text(movieReview.getText())
                .regDate(movieReview.getRegDate())
                .modDate(movieReview.getModDate())
                .build();
        return movieReviewDTO;
    }
}
