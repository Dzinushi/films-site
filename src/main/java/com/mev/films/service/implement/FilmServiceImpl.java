package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.FilmMapper;
import com.mev.films.model.FilmDTO;
import com.mev.films.service.interfaces.ExceptionService;
import com.mev.films.service.interfaces.FilmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FilmServiceImpl implements FilmService{

    private static final Logger LOG = LogManager.getLogger();

    @Autowired private FilmMapper filmMapper;

    @Autowired private ExceptionService exceptionService;

    public FilmServiceImpl(){
    }

    public FilmServiceImpl(FilmMapper filmMapper, ExceptionService exceptionService) {
       this.filmMapper = filmMapper;
       this.exceptionService = exceptionService;
    }

    @Override
    public List<FilmDTO> getFilms() {
        LOG.debug("getFilms");

        return filmMapper.selects();
    }

    @Override
    public FilmDTO getFilm(Long id) {
        LOG.debug("getFilm: id = {}",
                id);

        exceptionService.checkFilmId(id);

        return filmMapper.select(id);
    }

    @Override
    public List<FilmDTO> getFilmsSortByName() {
        LOG.debug("getFilmsSortByName");

        return filmMapper.selectsSortByName();
    }

    @Override
    public List<FilmDTO> getFilmByName(String name) {
        LOG.debug("getFilmByName: name = {}",
                name);

        exceptionService.checkFilmName(name);

        return filmMapper.selectsByName(name);
    }

    @Override
    public FilmDTO getFilmByImage(String image) {
        LOG.debug("getFilmByImage: image = {}",
                image);

        exceptionService.checkFilmImage(image);

        return filmMapper.selectByImage(image);
    }

    @Override
    public void addFilm(FilmDTO filmDTO) {
        LOG.debug("addFilm: {}",
                filmDTO);

        exceptionService.checkFilmWithoutId(filmDTO);

        filmMapper.insert(filmDTO);
    }

    @Override
    public void updateFilm(FilmDTO filmDTO) {
        LOG.debug("update: filmDTO = {}",
                filmDTO);

        exceptionService.checkFilm(filmDTO);

        filmMapper.update(filmDTO);
    }

    @Override
    public void deleteFilm(Long id) {

        LOG.debug("delete: id = {}",
                id);

        exceptionService.checkFilmId(id);

        filmMapper.delete(id);
    }

    @Override
    public void deleteFilmByImage(String image) {
        LOG.debug("deleteByImage: image = {}",
                image);

        exceptionService.checkFilmImage(image);

        filmMapper.deleteByImage(image);
    }
}