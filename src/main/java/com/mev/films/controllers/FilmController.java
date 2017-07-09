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

    @RequestMapping(value = {"/api/films"}, method = RequestMethod.GET)
    public List<FilmDTO> getAllFilms(){
        LOG.debug("getAllFilms");
        return filmService.getAllFilms();
    }

    @RequestMapping(value = {"/api/films/name"}, method = RequestMethod.GET)
    public List<FilmDTO> getFilmByName(@RequestParam String name){
        LOG.debug("getFilmByName: name = {}",
                name);
        return filmService.getFilmByName(name);
    }

    @RequestMapping(value = {"/api/films/image"}, method = RequestMethod.GET)
    public FilmDTO getFilmByImage(@RequestParam String image){
        LOG.debug("getFilmByImage: image = {}",
                image);
        return filmService.getFilmByImage(image);
    }

    @RequestMapping(value = {"/api/films"}, method = RequestMethod.POST)
    public void addFilm(@RequestBody FilmDTO filmDTO){
        LOG.debug("addFilm = {}",
                filmDTO);
        filmService.addFilm(filmDTO);
    }

    @RequestMapping(value = {"/api/films"}, method = RequestMethod.PUT)
    public void updateFilm(@RequestParam FilmDTO filmDTO){
        LOG.debug("filmDTO = {}",
                filmDTO);
        filmService.updateFilm(filmDTO);
    }

    @RequestMapping(value = {"/api/films"}, method = RequestMethod.DELETE)
    public void deleteFilmByImage(@RequestParam String image){
        LOG.debug("deleteFilmByImage = {}",
                image);
        filmService.deleteFilmByImage(image);
    }
}
