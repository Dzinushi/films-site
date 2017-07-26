package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.FilmMapper;
import com.mev.films.model.FilmDTO;
import com.mev.films.model.UserDTO;
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
    public List<FilmDTO> getFilms() {
        LOG.debug("getFilms");

        return filmMapper.selectFilms();
    }

    @Override
    public FilmDTO getFilm(Long id) {
        LOG.debug("getFilm: id = {}",
                id);

        FilmDTO filmDTO = null;
        if (id != null && id >= 0){
            filmDTO = filmMapper.selectFilm(id);
        }
        else {
            LOG.debug("Error in 'getFilm'! 'id' is not validate: id = {}",
                    id);
        }

        return filmDTO;
    }

    @Override
    public List<FilmDTO> getFilmsSortByName() {
        LOG.debug("getFilmsSortByName");

        return filmMapper.selectFilmsSortByName();
    }

    @Override
    public List<FilmDTO> getFilmByName(String name) {
        LOG.debug("getFilmByName: name = {}",
                name);

        List<FilmDTO> filmDTOS = null;
        if (name != null){
            filmDTOS = filmMapper.selectFilmsSortByName();
        }
        else {
            LOG.debug("Error in 'getFilmByName'! 'name' is null");
        }

        return filmDTOS;
    }

    @Override
    public FilmDTO getFilmByImage(String image) {
        LOG.debug("getFilmByImage: image = {}",
                image);

        FilmDTO filmDTO = null;
        if (image != null && (image.endsWith(".jpg") ||
                              image.endsWith(".jpeg") ||
                              image.endsWith(".png") ||
                              image.endsWith(".bmp"))){
            filmDTO = filmMapper.selectFilmByImage(image);
        }
        else {
            LOG.debug("Error in 'getFilmByImage'! image is not validate: image = {}",
                    image);
        }

        return filmDTO;
    }

    @Override
    public void addFilm(FilmDTO filmDTO) {
        LOG.debug("addFilm: {}",
                filmDTO);

        if (filmDTO != null){
            if (filmDTO.getName() != null){
                if (filmDTO.getGenre() != null){
                    if (filmDTO.getPrice() > 0 && filmDTO.getPrice() < 10000){
                        if (filmDTO.getDuration() > 0 && filmDTO.getDuration() < 300) {
                            String image = filmDTO.getImage();
                            if (image != null && (image.endsWith(".jpg") ||
                                    image.endsWith(".jpeg") ||
                                    image.endsWith(".png") ||
                                    image.endsWith(".bmp"))){

                                filmMapper.insertFilm(filmDTO);

                            }
                            else {
                                LOG.debug("Error in 'addFilm'! 'image' is not validate: image = {}",
                                        filmDTO.getImage());
                            }
                        }
                        else {
                            LOG.debug("Error in 'addFilm'! 'duration' is not validate: duration = {}",
                                    filmDTO.getDuration());
                        }
                    }
                    else {
                        LOG.debug("Error in 'addFilm'! 'price' is not validate: price = {}",
                                filmDTO.getPrice());
                    }
                }
                else {
                    LOG.debug("Error in 'addFilm'! 'genre' is null");
                }
            }
            else {
                LOG.debug("Error in 'addFilm'! 'name' is null");
            }
        }
        else {
            LOG.debug("Error in 'addFilm'! 'filmDTO' is null");
        }
    }

    @Override
    public void updateFilm(FilmDTO filmDTO) {
        LOG.debug("updateFilm: filmDTO = {}",
                filmDTO);

        if (filmDTO != null){
            if (filmDTO.getId() != null && filmDTO.getId() >= 0) {
                if (filmDTO.getName() != null) {
                    if (filmDTO.getGenre() != null) {
                        if (filmDTO.getPrice() > 0 && filmDTO.getPrice() < 10000) {
                            if (filmDTO.getDuration() > 0 && filmDTO.getDuration() < 300) {
                                String image = filmDTO.getImage();
                                if (image != null && (image.endsWith(".jpg") ||
                                        image.endsWith(".jpeg") ||
                                        image.endsWith(".png") ||
                                        image.endsWith(".bmp"))) {

                                    filmMapper.updateFilm(filmDTO);

                                } else {
                                    LOG.debug("Error in 'addFilm'! 'image' is not validate: image = {}",
                                            image);
                                }
                            } else {
                                LOG.debug("Error in 'addFilm'! 'duration' is not validate: duration = {}",
                                        filmDTO.getDuration());
                            }
                        } else {
                            LOG.debug("Error in 'addFilm'! 'price' is not validate: price = {}",
                                    filmDTO.getPrice());
                        }
                    } else {
                        LOG.debug("Error in 'addFilm'! 'genre' is null");
                    }
                } else {
                    LOG.debug("Error in 'addFilm'! 'name' is null");
                }
            }
            else {
                LOG.debug("Error in 'addFilm'! 'id' is not validate: id = {}",
                        filmDTO.getId());
            }
        }
        else {
            LOG.debug("Error in 'addFilm'! 'filmDTO' is null");
        }
    }

    @Override
    public void deleteFilm(Long id) {

        LOG.debug("deleteFilm: id = {}",
                id);

        if (id != null && id >= 0){
            filmMapper.deleteFilm(id);
        }
        else {
            LOG.debug("Error in 'deleteFilm'! 'id' is not validate: id = {}",
                    id);
        }
    }

    @Override
    public void deleteFilmByImage(String image) {
        LOG.debug("deleteFilmByImage: image = {}",
                image);

        if (image != null && (image.endsWith(".jpg") ||
                image.endsWith(".jpeg") ||
                image.endsWith(".png") ||
                image.endsWith(".bmp"))) {

            filmMapper.deleteFilmByImage(image);
        } else {
            LOG.debug("Error in 'addFilm'! 'image' is not validate: image = {}",
                    image);
        }
    }
}