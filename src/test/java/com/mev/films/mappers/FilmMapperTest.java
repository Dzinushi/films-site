package com.mev.films.mappers;


import com.mev.films.mappers.interfaces.FilmMapper;
import com.mev.films.model.FilmDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:test-dispatcher.xml")
public class FilmMapperTest {

    @Autowired private FilmMapper filmMapper;

    private FilmDTO filmDTO1 = new FilmDTO("film1", "genre1", (short) 100, 1, "imageUrl1");
    private FilmDTO filmDTO2 = new FilmDTO("film2", "genre1", (short) 200, 2, "imageUrl2");

    @Before
    public void setup(){
        List<FilmDTO> filmDTOS = filmMapper.selectFilms();
        for (FilmDTO filmDTO: filmDTOS){
            filmMapper.deleteFilmByImage(filmDTO.getImage());
        }
    }

    @Test
    public void getAllFilmsTest(){
        filmMapper.insertFilm(filmDTO1);
        filmMapper.insertFilm(filmDTO2);

        List<FilmDTO> filmDTOS = filmMapper.selectFilms();

        assertTrue("FilmDTO1 = " + filmDTO1,
                filmDTOS.get(0).equals(filmDTO1));
        assertTrue("FilmDTO2 = " + filmDTO2,
                filmDTOS.get(1).equals(filmDTO2));
    }

    @Test
    public void getFilmByNameTest(){
        filmMapper.insertFilm(filmDTO1);

        List<FilmDTO> filmDTOS = filmMapper.selectFilmsByName(filmDTO1.getName());
        assertTrue("filmDTO1 = " + filmDTO1,
                filmDTOS.get(0).equals(filmDTO1));
    }

    @Test
    public void getFilmByImage(){
        filmMapper.insertFilm(filmDTO1);

        FilmDTO filmDTO = filmMapper.selectFilmByImage(filmDTO1.getImage());
        assertTrue("FilmDTO1 = " + filmDTO1,
                filmDTO.equals(filmDTO1));
    }

    @Test
    public void addFilmTest(){
        filmMapper.insertFilm(filmDTO2);

        FilmDTO filmDTO = filmMapper.selectFilmByImage(filmDTO2.getImage());
        assertTrue("FilmDTO2 = " + filmDTO2,
                filmDTO.equals(filmDTO2));
    }

    @Test
    public void updateFilmTest(){
        filmMapper.insertFilm(filmDTO1);

        FilmDTO getFilmDTO1 = filmMapper.selectFilmByImage(filmDTO1.getImage());
        getFilmDTO1.setName("film11");
        getFilmDTO1.setGenre("genre11");
        getFilmDTO1.setDuration((short) 1000);
        getFilmDTO1.setPrice(11);
        getFilmDTO1.setImage("imageUrl11");

        filmMapper.updateFilm(getFilmDTO1);
        FilmDTO filmDTO = filmMapper.selectFilmByImage("imageUrl11");

        assertTrue("filmDTO1 = " + getFilmDTO1,
                filmDTO.equals(getFilmDTO1));
    }

    @Test
    public void deleteFilmByImageTest(){
        filmMapper.insertFilm(filmDTO1);

        List<FilmDTO> filmDTOS = filmMapper.selectFilms();
        assertTrue(filmDTOS.size() == 1);

        filmMapper.deleteFilmByImage(filmDTO1.getImage());
        filmDTOS = filmMapper.selectFilms();
        assertTrue(filmDTOS.size() == 0);
    }
}