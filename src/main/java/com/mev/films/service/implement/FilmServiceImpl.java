package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.FilmMapper;
import com.mev.films.model.FilmDTO;
import com.mev.films.service.interfaces.FilmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@Transactional
public class FilmServiceImpl implements FilmService{

    private static final Logger LOG = LogManager.getLogger();

    @Autowired private FilmMapper filmMapper;

    public FilmServiceImpl(){
    }

    public FilmServiceImpl(FilmMapper filmMapper) {
       this.filmMapper = filmMapper;
    }

    @Override
    public List<FilmDTO> getAllFilms() {
        LOG.debug("getAllFilms");

        return filmMapper.selectFilms();
    }

    @Override
    public List<FilmDTO> getFilmByName(String name) {
        LOG.debug("getFilmByName: name = {}",
                name);

        return filmMapper.selectFilmsByName(name);
    }

    @Override
    public FilmDTO getFilmByImage(String image) {
        LOG.debug("getFilmByImage: image = {}",
                image);

        return filmMapper.selectFilmByImage(image);
    }

    @Override
    public void addFilm(FilmDTO filmDTO) {
        LOG.debug("addFilm: filmDTO = {}",
                filmDTO);
        filmMapper.insertFilm(filmDTO);
    }

    @Override
    public void updateFilm(FilmDTO filmDTO) {
        LOG.debug("updateFilm: filmDTO = {}",
                filmDTO);

        filmMapper.updateFilm(filmDTO);
    }

    @Override
    public void deleteFilmByImage(String image) {
        LOG.debug("deleteFilmByImage: image = {}",
                image);

        filmMapper.deleteFilmByImage(image);
    }
}
