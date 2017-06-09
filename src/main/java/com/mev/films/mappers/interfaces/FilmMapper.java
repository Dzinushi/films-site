package com.mev.films.mappers.interfaces;

import com.mev.films.model.FilmDTO;

import java.util.List;

public interface FilmMapper {
    List<FilmMapper> selectFilms();
    Long insertFilm(FilmDTO filmDTO);
    Long updateFilm(FilmDTO filmDTO);
    Long deleteFilmById(Long id);
}
