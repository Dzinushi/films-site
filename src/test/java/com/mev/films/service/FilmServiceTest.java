package com.mev.films.service;

import com.mev.films.mappers.interfaces.FilmMapper;
import com.mev.films.model.FilmDTO;
import com.mev.films.service.implement.FilmServiceImpl;
import com.mev.films.service.interfaces.FilmService;
import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

import static org.easymock.EasyMock.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:test-dispatcher.xml")
public class FilmServiceTest {

    @Autowired private FilmService filmService;
    @Autowired private FilmMapper filmMapperMock;

    private FilmDTO filmDTO1 = new FilmDTO("film1", "genre1", (short) 100, 1, "imageUrl1");
    private FilmDTO filmDTO2 = new FilmDTO("film2", "genre1", (short) 200, 2, "imageUrl2");

    @Before
    public void setup(){
        filmMapperMock = createNiceMock(FilmMapper.class);
        filmService = new FilmServiceImpl(filmMapperMock);

        filmDTO1.setId(1L);
        filmDTO1.setId(2L);
    }

    @Test
    public void getFilmsTest(){

        expect(filmService.getFilms()).andStubAnswer(new IAnswer<List<FilmDTO>>() {
            @Override
            public List<FilmDTO> answer() throws Throwable {
                List<FilmDTO> filmDTOS = new ArrayList<>();
                filmDTOS.add(filmDTO1);
                filmDTOS.add(filmDTO2);
                return filmDTOS;
            }
        });

        replay(filmMapperMock);

        filmService.addFilm(filmDTO1);
        filmService.addFilm(filmDTO2);

        List<FilmDTO> filmDTOS = filmService.getFilms();

        assertTrue("filmDTO1 = " + filmDTO1,
                filmDTOS.get(0).equals(filmDTO1));
        assertTrue("filmDTO2 = " + filmDTO2,
                filmDTOS.get(1).equals(filmDTO2));

        verify(filmMapperMock);
    }

    @Test
    public void getFilmTest(){

        expect(filmService.getFilm(filmDTO2.getId())).andStubAnswer(new IAnswer<FilmDTO>() {
            @Override
            public FilmDTO answer() throws Throwable {
                return filmDTO2;
            }
        });

        replay(filmMapperMock);

        filmService.addFilm(filmDTO1);
        filmService.addFilm(filmDTO2);

        FilmDTO filmDTO = filmService.getFilm(filmDTO2.getId());

        assertTrue("filmDTO2 = " + filmDTO2,
                filmDTO.equals(filmDTO2));

        verify(filmMapperMock);
    }

    @Test
    public void getFilmsSortByNameTest(){
        expect(filmService.getFilmsSortByName()).andStubAnswer(new IAnswer<List<FilmDTO>>() {
            @Override
            public List<FilmDTO> answer() throws Throwable {
                List<FilmDTO> filmDTOS = new ArrayList<>();
                filmDTOS.add(filmDTO2);
                filmDTOS.add(filmDTO1);
                return filmDTOS;
            }
        });

        replay(filmMapperMock);

        filmService.addFilm(filmDTO1);
        filmService.addFilm(filmDTO2);

        List<FilmDTO> filmDTOS = filmService.getFilmsSortByName();

        assertTrue("filmDTO2 = " + filmDTO2.toString(),
                filmDTOS.get(0).equals(filmDTO2));
        assertTrue("filmDTO1 = " + filmDTO1.toString(),
                filmDTOS.get(1).equals(filmDTO1));
    }

    @Test
    public void getFilmByNameTest(){

        expect(filmService.getFilmByName(filmDTO1.getName())).andStubAnswer(new IAnswer<List<FilmDTO>>() {
            @Override
            public List<FilmDTO> answer() throws Throwable {
                List<FilmDTO> filmDTOS = new ArrayList<>();
                filmDTOS.add(filmDTO1);
                return filmDTOS;
            }
        });

        replay(filmMapperMock);

        filmService.addFilm(filmDTO1);

        List<FilmDTO> filmDTOS = filmService.getFilmByName(filmDTO1.getName());
        assertTrue("filmDTO1 = " + filmDTO1,
                filmDTOS.get(0).equals(filmDTO1));

        verify(filmMapperMock);
    }

    @Test
    public void getFilmByImage(){

        expect(filmService.getFilmByImage(filmDTO2.getImage())).andStubAnswer(new IAnswer<FilmDTO>() {
            @Override
            public FilmDTO answer() throws Throwable {
                return filmDTO2;
            }
        });

        replay(filmMapperMock);

        filmService.addFilm(filmDTO2);

        FilmDTO filmDTO = filmService.getFilmByImage(filmDTO2.getImage());
        assertTrue("filmDTO2 = " + filmDTO2,
                filmDTO.equals(filmDTO2));

        verify(filmMapperMock);
    }

    @Test
    public void addFilmTest(){

        expect(filmService.getFilmByImage(filmDTO1.getImage())).andStubAnswer(new IAnswer<FilmDTO>() {
            @Override
            public FilmDTO answer() throws Throwable {
                return filmDTO1;
            }
        });

        replay(filmMapperMock);

        filmService.addFilm(filmDTO1);

        FilmDTO filmDTO = filmService.getFilmByImage(filmDTO1.getImage());
        assertTrue("filmDTO1 = " + filmDTO1,
                filmDTO.equals(filmDTO1));

        verify(filmMapperMock);
    }

    @Test
    public void updateFilmTest(){

        expect(filmService.getFilmByImage(filmDTO1.getImage())).andStubAnswer(new IAnswer<FilmDTO>() {
            @Override
            public FilmDTO answer() throws Throwable {
                return filmDTO1;
            }
        });

        expect(filmService.getFilmByImage("imageUrl11")).andStubAnswer(new IAnswer<FilmDTO>() {
            @Override
            public FilmDTO answer() throws Throwable {
                FilmDTO filmDTO = new FilmDTO();
                filmDTO.setName("film11");
                filmDTO.setGenre("genre11");
                filmDTO.setDuration((short) 1000);
                filmDTO.setPrice(11);
                filmDTO.setImage("imageUrl11");
                return filmDTO;
            }
        });

        replay(filmMapperMock);

        filmService.addFilm(filmDTO1);

        FilmDTO getFilmDTO1 = filmService.getFilmByImage(filmDTO1.getImage());
        getFilmDTO1.setName("film11");
        getFilmDTO1.setGenre("genre11");
        getFilmDTO1.setDuration((short) 1000);
        getFilmDTO1.setPrice(11);
        getFilmDTO1.setImage("imageUrl11");

        filmService.updateFilm(getFilmDTO1);
        FilmDTO filmDTO = filmService.getFilmByImage("imageUrl11");

        assertTrue("filmDTO1 = " + getFilmDTO1,
                filmDTO.equals(getFilmDTO1));

        verify(filmMapperMock);
    }

    @Test
    public void deleteFilmByImageTest(){

        expect(filmService.getFilms()).andStubAnswer(new IAnswer<List<FilmDTO>>() {
            @Override
            public List<FilmDTO> answer() throws Throwable {
                return new ArrayList<>();
            }
        });

        replay(filmMapperMock);

        filmService.addFilm(filmDTO1);

        filmService.deleteFilmByImage(filmDTO1.getImage());
        List<FilmDTO> filmDTOS = filmService.getFilms();
        assertTrue(filmDTOS.size() == 0);

        verify(filmMapperMock);
    }
}
