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

        List<FilmDTO> filmDTOS = filmMapper.selectsAll();
        for (FilmDTO filmDTO: filmDTOS){
            filmMapper.delete(filmDTO.getId());
        }
    }

    @Test
    public void selectFilmsTest(){

        filmMapper.insert(filmDTO1);
        filmMapper.insert(filmDTO2);

        List<FilmDTO> filmDTOS = filmMapper.selects(1L, 0L);
        assertTrue("count = 1",
                filmDTOS.size() == 1);
        assertTrue("FilmDTO1 = " + filmDTO1,
                filmDTOS.get(0).equals(filmDTO1));

        filmDTOS = filmMapper.selects(1L, 1L);
        assertTrue("count = 1",
                filmDTOS.size() == 1);
        assertTrue("FilmDTO2 = " + filmDTO2,
                filmDTOS.get(0).equals(filmDTO2));
    }

    @Test
    public void selectFilmsCountTest(){

        Long count = filmMapper.selectsCount();
        assertTrue("count = 0",
                count == 0);

        filmMapper.insert(filmDTO1);
        count = filmMapper.selectsCount();
        assertTrue("count = 1",
                count == 1);
    }

    @Test
    public void selectFilmTest(){

        filmMapper.insert(filmDTO2);
        filmMapper.insert(filmDTO1);

        List<FilmDTO> filmDTOS = filmMapper.selectsAll();
        FilmDTO filmDTO = filmMapper.select(filmDTOS.get(1).getId());
        assertTrue("filmDTO1 = " + filmDTO1.toString(),
                filmDTO.equals(filmDTO1));
    }

    @Test
    public void selectFilmsSortByName(){

        filmMapper.insert(filmDTO2);
        filmMapper.insert(filmDTO1);

        List<FilmDTO> filmDTOS = filmMapper.selectsSortByName();
        assertTrue("count = 2",
                filmDTOS.size() == 2);
        assertTrue("filmDTO1 = " + filmDTO1.toString(),
                filmDTOS.get(0).equals(filmDTO1));
        assertTrue("filmDTO2 = " + filmDTO2.toString(),
                filmDTOS.get(1).equals(filmDTO2));
    }


    @Test
    public void selectFilmByNameTest(){

        filmMapper.insert(filmDTO1);
        filmMapper.insert(filmDTO2);
        filmMapper.insert(filmDTO3);

        List<FilmDTO> filmDTOS = filmMapper.selectsByName(filmDTO2.getName());
        assertTrue("count = 2",
                filmDTOS.size() == 2);
        assertTrue("filmDTO2 = " + filmDTO2,
                filmDTOS.get(0).equals(filmDTO2));
        assertTrue("filmDTO3 = " + filmDTO3,
                filmDTOS.get(1).equals(filmDTO3));
    }

    @Test
    public void selectFilmByImageTest(){

        filmMapper.insert(filmDTO1);

        FilmDTO filmDTO = filmMapper.selectByImage(filmDTO1.getImage());
        assertTrue("filmDTO1 = " + filmDTO1,
                filmDTO.equals(filmDTO1));
    }

    @Test
    public void insertFilmTest(){

        filmMapper.insert(filmDTO2);

        List<FilmDTO> filmDTOS = filmMapper.selectsAll();
        assertTrue("count = 1",
                filmDTOS.size() == 1);
        assertTrue("FilmDTO2 = " + filmDTO2,
                filmDTOS.get(0).equals(filmDTO2));


        filmMapper.insert(filmDTO1);

        filmDTOS = filmMapper.selectsAll();
        assertTrue("count = 2",
                filmDTOS.size() == 2);
        assertTrue("filmDTO2 = " + filmDTO2,
                filmDTOS.get(0).equals(filmDTO2));
        assertTrue("filmDTO1 = " + filmDTO1,
                filmDTOS.get(1).equals(filmDTO1));
    }

    @Test
    public void updateFilmTest(){

        filmMapper.insert(filmDTO1);

        FilmDTO getFilmDTO1 = filmMapper.selectByImage(filmDTO1.getImage());
        getFilmDTO1.setName("film11");
        getFilmDTO1.setGenre("genre11");
        getFilmDTO1.setDuration((short) 1000);
        getFilmDTO1.setPrice(11);
        getFilmDTO1.setImage("imageUrl11");

        filmMapper.update(getFilmDTO1);

        FilmDTO filmDTO = filmMapper.selectByImage("imageUrl11");
        assertTrue("filmDTO1 = " + getFilmDTO1,
                filmDTO.equals(getFilmDTO1));
    }

    @Test
    public void deleteFilmTest(){

        // insert 1 delete 1
        filmMapper.insert(filmDTO1);

        List<FilmDTO> filmDTOS = filmMapper.selectsAll();
        filmMapper.delete(filmDTOS.get(0).getId());
        filmDTOS = filmMapper.selectsAll();
        assertTrue("count = 0",
                filmDTOS.size() == 0);

        // insert 2 delete 2
        filmMapper.insert(filmDTO1);
        filmMapper.insert(filmDTO2);

        filmDTOS = filmMapper.selectsAll();
        filmMapper.delete(filmDTOS.get(1).getId());
        filmMapper.delete(filmDTOS.get(0).getId());
        filmDTOS = filmMapper.selectsAll();
        assertTrue("count = 0",
                filmDTOS.size() == 0);

        // insert 2 delete 1
        filmMapper.insert(filmDTO1);
        filmMapper.insert(filmDTO2);

        filmDTOS = filmMapper.selectsAll();
        filmMapper.delete(filmDTOS.get(1).getId());
        filmDTOS = filmMapper.selectsAll();
        assertTrue("count = 1",
                filmDTOS.size() == 1);
        assertTrue("filmDTO1 = " + filmDTO1.toString(),
                filmDTOS.get(0).equals(filmDTO1));
    }

    @Test
    public void deleteFilmByImageTest(){

        // insert 1 delete 1
        filmMapper.insert(filmDTO1);

        filmMapper.deleteByImage(filmDTO1.getImage());
        List<FilmDTO> filmDTOS = filmMapper.selectsAll();
        assertTrue("count = 0",
                filmDTOS.size() == 0);

        // insert 2 delete 2
        filmMapper.insert(filmDTO1);
        filmMapper.insert(filmDTO2);

        filmMapper.deleteByImage(filmDTO1.getImage());
        filmMapper.deleteByImage(filmDTO2.getImage());
        filmDTOS = filmMapper.selectsAll();
        assertTrue("count = 0",
                filmDTOS.size() == 0);

        // insert 2 delete 1
        filmMapper.insert(filmDTO1);
        filmMapper.insert(filmDTO2);

        filmMapper.deleteByImage(filmDTO2.getImage());
        filmDTOS = filmMapper.selectsAll();
        assertTrue("count = 1",
                filmDTOS.size() == 1);
        assertTrue("filmDTO1 = " + filmDTO1.toString(),
                filmDTOS.get(0).equals(filmDTO1));
    }
}
