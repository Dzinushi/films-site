package com.mev.films.controllers;


import com.mev.films.model.FilmDTO;
import com.mev.films.service.interfaces.FilmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FilmController {
    private static final Logger LOG = LogManager.getLogger();

    @Autowired private FilmService filmService;


    @RequestMapping(value = {"/api/films", "/admin/api/films"}, method = RequestMethod.GET)
    public List<FilmDTO> getFilms(){
        LOG.debug("getFilms");

        return filmService.getFilms();
    }

    @RequestMapping(value = {"/api/film", "/admin/api/film"}, method = RequestMethod.GET)
    public FilmDTO getFilm(@RequestParam Long id){
        LOG.debug("getFilm: id = {}",
                id);

        return filmService.getFilm(id);
    }

    @RequestMapping(value = {"/api/films/sort/name", "/admin/api/films/sort/name"}, method = RequestMethod.GET)
    public List<FilmDTO> getFilmsSortByName(){
        LOG.debug("getFilmsSorByName");

        return filmService.getFilmsSortByName();
    }

    @RequestMapping(value = {"/api/films/name", "/admin/api/films/name"}, method = RequestMethod.GET)
    public List<FilmDTO> getFilmByName(@RequestParam String name){
        LOG.debug("getFilmByName: name = {}",
                name);

        return filmService.getFilmByName(name);
    }

    @RequestMapping(value = {"/api/films/image", "/admin/api/films/image"}, method = RequestMethod.GET)
    public FilmDTO getFilmByImage(@RequestParam String image){
        LOG.debug("getFilmByImage: image = {}",
                image);

        return filmService.getFilmByImage(image);
    }

    // for admin
    @RequestMapping(value = {"/admin/api/films"}, method = RequestMethod.POST)
    public void addFilm(@RequestBody FilmDTO filmDTO){
        LOG.debug("addFilm = {}",
                filmDTO);

        filmService.addFilm(filmDTO);
    }

    // for admin
    @RequestMapping(value = {"/admin/api/films"}, method = RequestMethod.PUT)
    public void updateFilm(@RequestParam FilmDTO filmDTO){
        LOG.debug("filmDTO = {}",
                filmDTO);

        filmService.updateFilm(filmDTO);
    }

    // for admin
    @RequestMapping(value = {"/admin/api/films/image"}, method = RequestMethod.DELETE)
    public void deleteFilmByImage(@RequestParam String image){
        LOG.debug("deleteFilmByImage = {}",
                image);

        filmService.deleteFilmByImage(image);
    }
}
