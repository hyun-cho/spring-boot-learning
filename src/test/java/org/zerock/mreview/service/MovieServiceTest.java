package org.zerock.mreview.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.MovieImageDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieServiceTest {

    @Autowired
    MovieService movieService;

    @Test
    public void testEntityToDto() {

        List<MovieImageDTO> movieImageDTO = new ArrayList<>();
        MovieDTO movieDto = MovieDTO.builder()
                .mno(null)
                .title("asdf")
                .imageDTOList(movieImageDTO)
                .build();

        Long mno = movieService.register(movieDto);

        System.out.println(mno);
    }

}