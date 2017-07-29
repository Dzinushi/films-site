package com.mev.films.service;

import com.mev.films.mappers.interfaces.FilmMapper;
import com.mev.films.model.FilmDTO;
import com.mev.films.service.implement.ExceptionServiceImpl;
import com.mev.films.service.implement.FilmServiceImpl;
import com.mev.films.service.interfaces.ExceptionService;
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

import static junit.framework.TestCase.fail;
import static org.easymock.EasyMock.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:test-dispatcher.xml")
public class FilmServiceTest {

    @Autowired private FilmService filmService;
    @Autowired private FilmMapper filmMapperMock;

    @Autowired private ExceptionService exceptionService;

    private FilmDTO filmDTO1 = new FilmDTO("film1", "genre1", (short) 10, 100, "imageUrl1.png");
    private FilmDTO filmDTO2 = new FilmDTO("film2", "genre1", (short) 20, 200, "imageUrl2.bmp");
    private FilmDTO filmDTO3 = new FilmDTO("film1", "genre3", (short) 15, 180, "imageUrl3.jpg");

    @Before
    public void setup(){
        filmMapperMock = createNiceMock(FilmMapper.class);

        exceptionService = new ExceptionServiceImpl(filmMapperMock);

        filmService = new FilmServiceImpl(filmMapperMock, exceptionService);

        filmDTO1.setId(1L);
        filmDTO2.setId(2L);
        filmDTO3.setId(3L);
    }

    @Test
    public void getFilmsTest(){
    }

    @Test
    public void getFilmTest(){

        expect(filmMapperMock.selectFilm(filmDTO2.getId())).andAnswer(new IAnswer<FilmDTO>() {
            @Override
            public FilmDTO answer() throws Throwable {
                return filmDTO2;
            }
        });

        replay(filmMapperMock);

        FilmDTO filmDTO = filmService.getFilm(filmDTO2.getId());
        assertTrue("filmDTO2 = " + filmDTO2,
                filmDTO.equals(filmDTO2));

        // check id null
        try {
            filmService.getFilm(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        // check id < 0
        try {
            filmService.getFilm(-7L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("id = -7",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        verify(filmMapperMock);
    }

    @Test
    public void getFilmsSortByNameTest(){
    }

    @Test
    public void getFilmByNameTest(){

        expect(filmMapperMock.selectFilmsByName(filmDTO1.getName())).andAnswer(new IAnswer<List<FilmDTO>>() {
            @Override
            public List<FilmDTO> answer() throws Throwable {
                List<FilmDTO> filmDTOS = new ArrayList<>();
                filmDTOS.add(filmDTO1);
                filmDTOS.add(filmDTO3);
                return filmDTOS;
            }
        });

        replay(filmMapperMock);

        List<FilmDTO> filmDTOS = filmService.getFilmByName(filmDTO1.getName());
        assertTrue("filmDTO1 = " + filmDTO1,
                filmDTOS.get(0).equals(filmDTO1));

        // check name null
        try {
            filmService.getFilmByName(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_NAME_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("name = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_NAME_PROVIDED).getMessage()));
        }

        verify(filmMapperMock);
    }

    @Test
    public void getFilmByImage(){

        expect(filmMapperMock.selectFilmByImage(filmDTO2.getImage())).andAnswer(new IAnswer<FilmDTO>() {
            @Override
            public FilmDTO answer() throws Throwable {
                return filmDTO2;
            }
        });

        replay(filmMapperMock);

        FilmDTO filmDTO = filmService.getFilmByImage(filmDTO2.getImage());
        assertTrue("filmDTO2 = " + filmDTO2,
                filmDTO.equals(filmDTO2));

        // check image null
        try {
            filmService.getFilmByImage(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_IMAGE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("image = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_IMAGE_PROVIDED).getMessage()));
        }

        // check image not valid format
        try {
            filmService.getFilmByImage("imageUrl1");
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_IMAGE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("image = imageUrl1",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_IMAGE_PROVIDED).getMessage()));
        }

        verify(filmMapperMock);
    }

    @Test
    public void addFilmTest(){

        replay(filmMapperMock);

        filmService.addFilm(filmDTO3);

        // check null
        try {
            filmService.addFilm(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_NULL_POINTER_EXCEPTION).getMessage()));
        }

        // check name null
        try {
            filmService.addFilm(new FilmDTO(null, filmDTO3.getGenre(), filmDTO3.getDuration(), filmDTO3.getPrice(), filmDTO3.getImage()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_NAME_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO.name = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_NAME_PROVIDED).getMessage()));
        }

        // check genre null
        try {
            filmService.addFilm(new FilmDTO(filmDTO3.getName(), null, filmDTO3.getDuration(), filmDTO3.getPrice(), filmDTO3.getImage()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_GENRE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO.genre = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_GENRE_PROVIDED).getMessage()));
        }

        // check duration null
        try {
            filmService.addFilm(new FilmDTO(filmDTO3.getName(), filmDTO3.getGenre(), null, filmDTO3.getPrice(), filmDTO3.getImage()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_DURATION_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO.duration = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_DURATION_PROVIDED).getMessage()));
        }

        // check duration less validate
        try {
            filmService.addFilm(new FilmDTO(filmDTO3.getName(), filmDTO3.getGenre(), (short) -5, filmDTO3.getPrice(), filmDTO3.getImage()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_DURATION_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO.duration = -5",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_DURATION_PROVIDED).getMessage()));
        }

        // check duration more validate
        try {
            filmService.addFilm(new FilmDTO(filmDTO3.getName(), filmDTO3.getGenre(), (short) 301, filmDTO3.getPrice(), filmDTO3.getImage()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_DURATION_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO.duration = 301",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_DURATION_PROVIDED).getMessage()));
        }

        // check price null
        try {
            filmService.addFilm(new FilmDTO(filmDTO3.getName(), filmDTO3.getGenre(), filmDTO3.getDuration(), null, filmDTO3.getImage()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_PRICE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO.price = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_PRICE_PROVIDED).getMessage()));
        }

        // check price less validate
        try {
            filmService.addFilm(new FilmDTO(filmDTO3.getName(), filmDTO3.getGenre(), filmDTO3.getDuration(), -1, filmDTO3.getImage()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_PRICE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO.price = -1",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_PRICE_PROVIDED).getMessage()));
        }

        // check price more validate
        try {
            filmService.addFilm(new FilmDTO(filmDTO3.getName(), filmDTO3.getGenre(), filmDTO3.getDuration(), 10001, filmDTO3.getImage()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_PRICE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO.price = 10 001",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_PRICE_PROVIDED).getMessage()));
        }

        // check image null
        try {
            filmService.addFilm(new FilmDTO(filmDTO3.getName(), filmDTO3.getGenre(), filmDTO3.getDuration(), filmDTO3.getPrice(), null));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_IMAGE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO.image = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_IMAGE_PROVIDED).getMessage()));
        }

        // check image not validate
        try {
            filmService.addFilm(new FilmDTO(filmDTO3.getName(), filmDTO3.getGenre(), filmDTO3.getDuration(), filmDTO3.getPrice(), "imageUrl.crf"));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_IMAGE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO.image = imageUrl.crf",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_IMAGE_PROVIDED).getMessage()));
        }

        verify(filmMapperMock);
    }

    @Test
    public void updateFilmTest(){

        replay(filmMapperMock);

        filmService.updateFilm(filmDTO3);

        // check null
        try {
            filmService.updateFilm(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_NULL_POINTER_EXCEPTION).getMessage()));
        }

        // check id null
        try {
            FilmDTO filmDTO = new FilmDTO(filmDTO3.getName(), filmDTO3.getGenre(), filmDTO3.getDuration(), filmDTO3.getPrice(), filmDTO3.getImage());
            filmService.updateFilm(filmDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        // check id null
        try {
            FilmDTO filmDTO = new FilmDTO(filmDTO3.getName(), filmDTO3.getGenre(), filmDTO3.getDuration(), filmDTO3.getPrice(), filmDTO3.getImage());
            filmDTO.setId(-7L);
            filmService.updateFilm(filmDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO = -7",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        // check name null
        try {
            FilmDTO filmDTO = new FilmDTO(null, filmDTO3.getGenre(), filmDTO3.getDuration(), filmDTO3.getPrice(), filmDTO3.getImage());
            filmDTO.setId(7L);
            filmService.updateFilm(filmDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_NAME_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO.name = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_NAME_PROVIDED).getMessage()));
        }

        // check genre null
        try {
            FilmDTO filmDTO = new FilmDTO(filmDTO3.getName(), null, filmDTO3.getDuration(), filmDTO3.getPrice(), filmDTO3.getImage());
            filmDTO.setId(7L);
            filmService.updateFilm(filmDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_GENRE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO.genre = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_GENRE_PROVIDED).getMessage()));
        }

        // check duration null
        try {
            FilmDTO filmDTO = new FilmDTO(filmDTO3.getName(), filmDTO3.getGenre(), null, filmDTO3.getPrice(), filmDTO3.getImage());
            filmDTO.setId(7L);
            filmService.updateFilm(filmDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_DURATION_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO.duration = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_DURATION_PROVIDED).getMessage()));
        }

        // check duration less validate
        try {
            FilmDTO filmDTO = new FilmDTO(filmDTO3.getName(), filmDTO3.getGenre(), (short) -1, filmDTO3.getPrice(), filmDTO3.getImage());
            filmDTO.setId(7L);
            filmService.updateFilm(filmDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_DURATION_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO.duration = -5",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_DURATION_PROVIDED).getMessage()));
        }

        // check duration more validate
        try {
            FilmDTO filmDTO = new FilmDTO(filmDTO3.getName(), filmDTO3.getGenre(), (short) 301, filmDTO3.getPrice(), filmDTO3.getImage());
            filmDTO.setId(7L);
            filmService.updateFilm(filmDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_DURATION_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO.duration = 301",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_DURATION_PROVIDED).getMessage()));
        }

        // check price null
        try {
            FilmDTO filmDTO = new FilmDTO(filmDTO3.getName(), filmDTO3.getGenre(), filmDTO3.getDuration(), null, filmDTO3.getImage());
            filmDTO.setId(7L);
            filmService.updateFilm(filmDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_PRICE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO.price = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_PRICE_PROVIDED).getMessage()));
        }

        // check price less validate
        try {
            FilmDTO filmDTO = new FilmDTO(filmDTO3.getName(), filmDTO3.getGenre(), filmDTO3.getDuration(), -1, filmDTO3.getImage());
            filmDTO.setId(7L);
            filmService.updateFilm(filmDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_PRICE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO.price = -1",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_PRICE_PROVIDED).getMessage()));
        }

        // check price more validate
        try {
            FilmDTO filmDTO = new FilmDTO(filmDTO3.getName(), filmDTO3.getGenre(), filmDTO3.getDuration(), 10001, filmDTO3.getImage());
            filmDTO.setId(7L);
            filmService.updateFilm(filmDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_PRICE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO.price = 10 001",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_PRICE_PROVIDED).getMessage()));
        }

        // check image null
        try {
            FilmDTO filmDTO = new FilmDTO(filmDTO3.getName(), filmDTO3.getGenre(), filmDTO3.getDuration(), filmDTO3.getPrice(), null);
            filmDTO.setId(7L);
            filmService.updateFilm(filmDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_IMAGE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO.image = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_IMAGE_PROVIDED).getMessage()));
        }

        // check image not validate
        try {
            FilmDTO filmDTO = new FilmDTO(filmDTO3.getName(), filmDTO3.getGenre(), filmDTO3.getDuration(), filmDTO3.getPrice(), "ImageUrl.crf");
            filmDTO.setId(7L);
            filmService.updateFilm(filmDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_IMAGE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("filmDTO.image = imageUrl.crf",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_IMAGE_PROVIDED).getMessage()));
        }

        verify(filmMapperMock);
    }

    @Test
    public void deleteFilmTest(){

        replay(filmMapperMock);

        filmService.deleteFilm(filmDTO1.getId());

        // check id null
        try {
            filmService.deleteFilm(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        // check id < 0
        try {
            filmService.deleteFilm(-7L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("id = -7",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        verify(filmMapperMock);
    }

    @Test
    public void deleteFilmByImageTest(){

        replay(filmMapperMock);

        filmService.deleteFilm(filmDTO1.getId());

        // check id null
        try {
            filmService.deleteFilmByImage(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_IMAGE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.FILM_ERROR_WRONG_IMAGE_PROVIDED).getMessage()));
        }

        verify(filmMapperMock);
    }
}
