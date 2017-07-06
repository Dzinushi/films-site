package com.mev.films.service;

import com.mev.films.mappers.interfaces.FilmMapper;
import com.mev.films.model.FilmDTO;
import com.mev.films.service.interfaces.FilmService;
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
public class FilmServiceTest {

    @Autowired private FilmService filmService;

    private FilmDTO filmDTO1 = new FilmDTO("film1", "genre1", (short) 100, 1, "imageUrl1");
    private FilmDTO filmDTO2 = new FilmDTO("film2", "genre1", (short) 200, 2, "imageUrl2");

    @Before
    public void setup(){
        List<FilmDTO> filmDTOS = filmService.getAllFilms();
        for (FilmDTO filmDTO: filmDTOS){
            filmService.deleteFilmByImage(filmDTO.getImage());
        }
    }

    @Test
    public void getAllFilmsTest(){
        filmService.addFilm(filmDTO1);
        filmService.addFilm(filmDTO2);

        List<FilmDTO> filmDTOS = filmService.getAllFilms();

        // Check 1-t object
        assertTrue("name1 = " + filmDTO1.getName(),
                filmDTOS.get(0).getName().equals(filmDTO1.getName()));
        assertTrue("genre1 = " + filmDTO1.getGenre(),
                filmDTOS.get(0).getGenre().equals(filmDTO1.getGenre()));
        assertTrue("duration1 = " + filmDTO1.getDuration(),
                filmDTOS.get(0).getDuration() == filmDTO1.getDuration());
        assertTrue("price1 = " + filmDTO1.getPrice(),
                filmDTOS.get(0).getPrice() == filmDTO1.getPrice());
        assertTrue("image1 = " + filmDTO1.getImage(),
                filmDTOS.get(0).getImage().equals(filmDTO1.getImage()));

        // Check 2-d object
        assertTrue("name2 = " + filmDTO2.getName(),
                filmDTOS.get(1).getName().equals(filmDTO2.getName()));
        assertTrue("genre2 = " + filmDTO2.getGenre(),
                filmDTOS.get(1).getGenre().equals(filmDTO2.getGenre()));
        assertTrue("duration2 = " + filmDTO2.getDuration(),
                filmDTOS.get(1).getDuration() == filmDTO2.getDuration());
        assertTrue("price2 = " + filmDTO2.getPrice(),
                filmDTOS.get(1).getPrice() == filmDTO2.getPrice());
        assertTrue("image2 = " + filmDTO2.getImage(),
                filmDTOS.get(1).getImage().equals(filmDTO2.getImage()));
    }

    @Test
    public void getFilmByNameTest(){
        filmService.addFilm(filmDTO1);

        List<FilmDTO> filmDTOS = filmService.getFilmByName(filmDTO1.getName());
        assertTrue("name1 = " + filmDTO1.getName(),
                filmDTOS.get(0).getName().equals(filmDTO1.getName()));
        assertTrue("genre1 = " + filmDTO1.getGenre(),
                filmDTOS.get(0).getGenre().equals(filmDTO1.getGenre()));
        assertTrue("duration1 = " + filmDTO1.getDuration(),
                filmDTOS.get(0).getDuration() == filmDTO1.getDuration());
        assertTrue("price1 = " + filmDTO1.getPrice(),
                filmDTOS.get(0).getPrice() == filmDTO1.getPrice());
        assertTrue("image1 = " + filmDTO1.getImage(),
                filmDTOS.get(0).getImage().equals(filmDTO1.getImage()));
    }

    @Test
    public void getFilmByImage(){
        filmService.addFilm(filmDTO1);

        FilmDTO filmDTO = filmService.getFilmByImage(filmDTO1.getImage());
        assertTrue("name1 = " + filmDTO1.getName(),
                filmDTO.getName().equals(filmDTO1.getName()));
        assertTrue("genre1 = " + filmDTO1.getGenre(),
                filmDTO.getGenre().equals(filmDTO1.getGenre()));
        assertTrue("duration1 = " + filmDTO1.getDuration(),
                filmDTO.getDuration() == filmDTO1.getDuration());
        assertTrue("price1 = " + filmDTO1.getPrice(),
                filmDTO.getPrice() == filmDTO1.getPrice());
        assertTrue("image1 = " + filmDTO1.getImage(),
                filmDTO.getImage().equals(filmDTO1.getImage()));
    }

    @Test
    public void addFilmTest(){
        filmService.addFilm(filmDTO2);

        FilmDTO filmDTO = filmService.getFilmByImage(filmDTO2.getImage());
        assertTrue("name2 = " + filmDTO2.getName(),
                filmDTO.getName().equals(filmDTO2.getName()));
        assertTrue("genre2 = " + filmDTO2.getGenre(),
                filmDTO.getGenre().equals(filmDTO2.getGenre()));
        assertTrue("duration2 = " + filmDTO2.getDuration(),
                filmDTO.getDuration() == filmDTO2.getDuration());
        assertTrue("price2 = " + filmDTO2.getPrice(),
                filmDTO.getPrice() == filmDTO2.getPrice());
        assertTrue("image2 = " + filmDTO2.getImage(),
                filmDTO.getImage().equals(filmDTO2.getImage()));
    }

    @Test
    public void updateFilmTest(){
        filmService.addFilm(filmDTO1);

        FilmDTO filmDTO = filmService.getFilmByImage(filmDTO1.getImage());
        filmDTO.setName("film11");
        filmDTO.setGenre("genre11");
        filmDTO.setDuration((short) 1000);
        filmDTO.setPrice(11);
        filmDTO.setImage("imageUrl11");

        filmService.updateFilm(filmDTO);
        filmDTO = filmService.getFilmByImage("imageUrl11");

        assertTrue("name1 = " + filmDTO.getName(),
                filmDTO.getName().equals("film11"));
        assertTrue("genre1 = " + filmDTO.getGenre(),
                filmDTO.getGenre().equals("genre11"));
        assertTrue("duration1 = " + filmDTO.getDuration(),
                filmDTO.getDuration() == (short) 1000);
        assertTrue("price1 = " + filmDTO.getPrice(),
                filmDTO.getPrice() == 11);
        assertTrue("image1 = " + filmDTO.getImage(),
                filmDTO.getImage().equals("imageUrl11"));
    }

    @Test
    public void deleteFilmByImageTest(){
        filmService.addFilm(filmDTO1);

        List<FilmDTO> filmDTOS = filmService.getAllFilms();
        assertTrue(filmDTOS.size() == 1);

        filmService.deleteFilmByImage(filmDTO1.getImage());
        filmDTOS = filmService.getAllFilms();
        assertTrue(filmDTOS.size() == 0);
    }
}
