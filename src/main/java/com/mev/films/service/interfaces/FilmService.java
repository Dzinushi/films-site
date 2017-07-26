package com.mev.films.service.interfaces;


import com.mev.films.model.FilmDTO;
import com.mev.films.model.UserDTO;

import java.util.List;

public interface FilmService {
    List<FilmDTO> getFilms();
    FilmDTO getFilm(Long id);
    List<FilmDTO> getFilmsSortByName();
    List<FilmDTO> getFilmByName(String name);
    FilmDTO getFilmByImage(String image);
    void addFilm(FilmDTO filmDTO);
    void updateFilm(FilmDTO filmDTO);
    void deleteFilm(Long id);
    void deleteFilmByImage(String image);
}