package com.mev.films.service.interfaces;


import com.mev.films.model.FilmDTO;

import java.util.List;

public interface FilmService {
    List<FilmDTO> getFilms();
    List<FilmDTO> getFilmsSortByName();
    List<FilmDTO> getFilmByName(String name);
    FilmDTO getFilmByImage(String image);
    void addFilm(FilmDTO filmDTO);
    void updateFilm(FilmDTO filmDTO);
    void deleteFilmByImage(String image);
}