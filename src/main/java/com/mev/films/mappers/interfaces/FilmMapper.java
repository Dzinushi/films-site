package com.mev.films.mappers.interfaces;

import com.mev.films.model.FilmDTO;

import java.util.List;

public interface FilmMapper {
    List<FilmDTO> selectsAll();
    FilmDTO select(Long id);
    List<FilmDTO> selectsSortByName();
    List<FilmDTO> selectsByName(String name);
    FilmDTO selectByImage(String image);
    void insert(FilmDTO filmDTO);
    void update(FilmDTO filmDTO);
    void delete(Long id);
    void deleteByImage(String image);
}
