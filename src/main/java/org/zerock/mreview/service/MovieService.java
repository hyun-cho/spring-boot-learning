package org.zerock.mreview.service;


import org.springframework.data.domain.PageRequest;
import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.MovieImageDTO;
import org.zerock.mreview.dto.PageRequestDTO;
import org.zerock.mreview.dto.PageResultDTO;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public interface MovieService {
    Long register(MovieDTO movieDTO);

    //목록 처리
    PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO);

    //영화 하나 정보
    MovieDTO getMovie(Long mno);

    /*
    * Movie Entity
    * List<MovieImage> Entity - 리스트로 받은 이유는 조회 화면에서 여러 개의 MovieImage를 처리하기 위해서
    * Double 타입의 평점 평균
    * Long 타입의 리뷰 개수
    * */
    default MovieDTO entitiesToDTO(Movie movie, List<MovieImage> movieImages, Double avg, Long reviewCnt) {
        MovieDTO movieDTO = MovieDTO.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .regDate(movie.getRegDate())
                .modDate(movie.getModDate())
                .build();

        List<MovieImageDTO> movieImageDTOList = movieImages.stream().map(movieImage -> {
            if(movieImage == null) return new MovieImageDTO();
            return MovieImageDTO.builder()
                    .imgName(movieImage.getImgName())
                    .path(movieImage.getPath())
                    .uuid(movieImage.getUuid())
                    .build();
        }).collect(Collectors.toList());

        movieDTO.setImageDTOList(movieImageDTOList);
        movieDTO.setAvg(avg);
        movieDTO.setReviewCnt(reviewCnt.intValue());

        return movieDTO;
    }


    default Map<String, Object> dtoToEntity(MovieDTO movieDTO) {
        Map<String, Object> entityMap = new HashMap<>();

        // 질문 할 부분
        Movie movie;
        if (movieDTO.getMno() == null) {
            movie = Movie.builder()
                    .title(movieDTO.getTitle())
                    .build();
        } else {
            movie = Movie.builder()
                    .mno(movieDTO.getMno())
                    .title(movieDTO.getTitle())
                    .build();
        }

        entityMap.put("movie", movie);

        List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();

        //MovieImageDTO 처리
        if(imageDTOList != null && imageDTOList.size() > 0){
            List<MovieImage> movieImageList = imageDTOList.stream().map(movieImageDTO -> {
               MovieImage movieImage = MovieImage.builder().path(movieImageDTO.getPath())
                                                           .imgName(movieImageDTO.getImgName())
                                                           .uuid(movieImageDTO.getUuid())
                                                           .movie(movie)
                                                           .build();
               return movieImage;
            }).collect(Collectors.toList());

            entityMap.put("imgList", movieImageList);
        }

        return entityMap;
    }
}
