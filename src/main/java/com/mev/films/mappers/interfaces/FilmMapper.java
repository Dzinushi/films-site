package com.mev.films.mappers.interfaces;

import com.mev.films.model.FilmDTO;

import java.util.List;

public interface FilmMapper {
    List<FilmDTO> selectFilms();
    List<FilmDTO> selectFilmsSortByName();
    List<FilmDTO> selectFilmsByName(String name);
    FilmDTO selectFilmByImage(String image);
    Long insertFilm(FilmDTO filmDTO);
    Long updateFilm(FilmDTO filmDTO);
    Long deleteFilmByImage(String image);
}
