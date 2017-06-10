package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.FilmMapper;
import com.mev.films.model.FilmDTO;
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
    public Long addFilm(FilmDTO filmDTO) {
        LOG.debug("addFilm: id = {}, name = {}, genre = {}, duration = {}, price = {}, image = {}",
                filmDTO.getId(), filmDTO.getName(), filmDTO.getGenre(), filmDTO.getDuration(), filmDTO.getPrice(), filmDTO.getImage());
        return filmMapper.insertFilm(filmDTO);
    }

    @Override
    public Long updateFilm(FilmDTO filmDTO) {
        LOG.debug("updateFilm: id = {}, name = {}, genre = {}, duration = {}, price = {}, image = {}",
                filmDTO.getId(), filmDTO.getName(), filmDTO.getGenre(), filmDTO.getDuration(), filmDTO.getPrice(), filmDTO.getImage());

        return filmMapper.updateFilm(filmDTO);
    }

    @Override
    public Long deleteFilmByImage(String image) {
        LOG.debug("deleteFilmByImage: image = {}",
                image);

        return filmMapper.deleteFilmByImage(image);
    }
}
