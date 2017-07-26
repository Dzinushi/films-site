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

    private FilmDTO filmDTO1 = new FilmDTO("film1", "genre1", (short) 10, 100, "imageUrl1");
    private FilmDTO filmDTO2 = new FilmDTO("film2", "genre2", (short) 20, 200, "imageUrl2");
    private FilmDTO filmDTO3 = new FilmDTO("film2", "genre3", (short) 30, 150, "imageUrl3");

    @Before
    public void setup(){

        List<FilmDTO> filmDTOS = filmMapper.selectFilms();
        for (FilmDTO filmDTO: filmDTOS){
            filmMapper.deleteFilm(filmDTO.getId());
        }
    }

    @Test
    public void selectFilmsTest(){

        filmMapper.insertFilm(filmDTO1);
        filmMapper.insertFilm(filmDTO2);

        List<FilmDTO> filmDTOS = filmMapper.selectFilms();
        assertTrue("count = 2",
                filmDTOS.size() == 2);
        assertTrue("FilmDTO1 = " + filmDTO1,
                filmDTOS.get(0).equals(filmDTO1));
        assertTrue("FilmDTO2 = " + filmDTO2,
                filmDTOS.get(1).equals(filmDTO2));
    }

    @Test
    public void selectFilmTest(){

        filmMapper.insertFilm(filmDTO2);
        filmMapper.insertFilm(filmDTO1);

        List<FilmDTO> filmDTOS = filmMapper.selectFilms();
        FilmDTO filmDTO = filmMapper.selectFilm(filmDTOS.get(1).getId());
        assertTrue("filmDTO1 = " + filmDTO1.toString(),
                filmDTO.equals(filmDTO1));
    }

    @Test
    public void selectFilmsSortByName(){

        filmMapper.insertFilm(filmDTO2);
        filmMapper.insertFilm(filmDTO1);

        List<FilmDTO> filmDTOS = filmMapper.selectFilmsSortByName();
        assertTrue("count = 2",
                filmDTOS.size() == 2);
        assertTrue("filmDTO1 = " + filmDTO1.toString(),
                filmDTOS.get(0).equals(filmDTO1));
        assertTrue("filmDTO2 = " + filmDTO2.toString(),
                filmDTOS.get(1).equals(filmDTO2));
    }


    @Test
    public void selectFilmByNameTest(){

        filmMapper.insertFilm(filmDTO1);
        filmMapper.insertFilm(filmDTO2);
        filmMapper.insertFilm(filmDTO3);

        List<FilmDTO> filmDTOS = filmMapper.selectFilmsByName(filmDTO2.getName());
        assertTrue("count = 2",
                filmDTOS.size() == 2);
        assertTrue("filmDTO2 = " + filmDTO2,
                filmDTOS.get(0).equals(filmDTO2));
        assertTrue("filmDTO3 = " + filmDTO3,
                filmDTOS.get(1).equals(filmDTO3));
    }

    @Test
    public void selectFilmByImageTest(){

        filmMapper.insertFilm(filmDTO1);

        FilmDTO filmDTO = filmMapper.selectFilmByImage(filmDTO1.getImage());
        assertTrue("filmDTO1 = " + filmDTO1,
                filmDTO.equals(filmDTO1));
    }

    @Test
    public void insertFilmTest(){

        filmMapper.insertFilm(filmDTO2);

        List<FilmDTO> filmDTOS = filmMapper.selectFilms();
        assertTrue("count = 1",
                filmDTOS.size() == 1);
        assertTrue("FilmDTO2 = " + filmDTO2,
                filmDTOS.get(0).equals(filmDTO2));


        filmMapper.insertFilm(filmDTO1);

        filmDTOS = filmMapper.selectFilms();
        assertTrue("count = 2",
                filmDTOS.size() == 2);
        assertTrue("filmDTO2 = " + filmDTO2,
                filmDTOS.get(0).equals(filmDTO2));
        assertTrue("filmDTO1 = " + filmDTO1,
                filmDTOS.get(1).equals(filmDTO1));
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
    public void deleteFilmTest(){

        // insert 1 delete 1
        filmMapper.insertFilm(filmDTO1);

        List<FilmDTO> filmDTOS = filmMapper.selectFilms();
        filmMapper.deleteFilm(filmDTOS.get(0).getId());
        filmDTOS = filmMapper.selectFilms();
        assertTrue("count = 0",
                filmDTOS.size() == 0);

        // insert 2 delete 2
        filmMapper.insertFilm(filmDTO1);
        filmMapper.insertFilm(filmDTO2);

        filmDTOS = filmMapper.selectFilms();
        filmMapper.deleteFilm(filmDTOS.get(1).getId());
        filmMapper.deleteFilm(filmDTOS.get(0).getId());
        filmDTOS = filmMapper.selectFilms();
        assertTrue("count = 0",
                filmDTOS.size() == 0);

        // insert 2 delete 1
        filmMapper.insertFilm(filmDTO1);
        filmMapper.insertFilm(filmDTO2);

        filmDTOS = filmMapper.selectFilms();
        filmMapper.deleteFilm(filmDTOS.get(1).getId());
        filmDTOS = filmMapper.selectFilms();
        assertTrue("count = 1",
                filmDTOS.size() == 1);
        assertTrue("filmDTO1 = " + filmDTO1.toString(),
                filmDTOS.get(0).equals(filmDTO1));
    }

    @Test
    public void deleteFilmByImageTest(){

        // insert 1 delete 1
        filmMapper.insertFilm(filmDTO1);

        filmMapper.deleteFilmByImage(filmDTO1.getImage());
        List<FilmDTO> filmDTOS = filmMapper.selectFilms();
        assertTrue("count = 0",
                filmDTOS.size() == 0);

        // insert 2 delete 2
        filmMapper.insertFilm(filmDTO1);
        filmMapper.insertFilm(filmDTO2);

        filmMapper.deleteFilmByImage(filmDTO1.getImage());
        filmMapper.deleteFilmByImage(filmDTO2.getImage());
        filmDTOS = filmMapper.selectFilms();
        assertTrue("count = 0",
                filmDTOS.size() == 0);

        // insert 2 delete 1
        filmMapper.insertFilm(filmDTO1);
        filmMapper.insertFilm(filmDTO2);

        filmMapper.deleteFilmByImage(filmDTO2.getImage());
        filmDTOS = filmMapper.selectFilms();
        assertTrue("count = 1",
                filmDTOS.size() == 1);
        assertTrue("filmDTO1 = " + filmDTO1.toString(),
                filmDTOS.get(0).equals(filmDTO1));
    }
}
