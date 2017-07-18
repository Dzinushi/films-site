package com.mev.films.mappers.interfaces;

import com.mev.films.model.FilmDTO;

import java.util.List;

public interface FilmMapper {
    List<FilmDTO> selectFilms();
    FilmDTO selectFilm(Long id);
    List<FilmDTO> selectFilmsSortByName();
    List<FilmDTO> selectFilmsByName(String name);
    FilmDTO selectFilmByImage(String image);
    void insertFilm(FilmDTO filmDTO);
    void updateFilm(FilmDTO filmDTO);
    void deleteFilmByImage(String image);
}
