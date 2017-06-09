package com.mev.films.service.interfaces;


import com.mev.films.model.FilmDTO;

import java.util.List;

public interface FilmService {
    List<FilmDTO> getAllFilms();
    List<FilmDTO> getFilmByName(String name);
    FilmDTO getFilmByImage(String image);
    Long addFilm(FilmDTO filmDTO);
    Long updateFilm(FilmDTO filmDTO);
    Long deleteFilmByImage(String image);
}