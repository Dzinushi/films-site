package com.mev.films.model;

import java.sql.Blob;
import java.sql.Time;

public class FilmDTO {
    private int filmId;
    private String filmName;
    private String filmGenre;
    private Time filmLast;
    private int filmPrice;
    private Blob image;

    public FilmDTO(){
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmGenre() {
        return filmGenre;
    }

    public void setFilmGenre(String filmGenre) {
        this.filmGenre = filmGenre;
    }

    public Time getFilmLast() {
        return filmLast;
    }

    public void setFilmLast(Time filmLast) {
        this.filmLast = filmLast;
    }

    public int getFilmPrice() {
        return filmPrice;
    }

    public void setFilmPrice(int filmPrice) {
        this.filmPrice = filmPrice;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("FilmDTO [filmId = ");
        sb.append(filmId);
        sb.append(", filmName = ");
        sb.append(filmName);
        sb.append(", filmGenre = ");
        sb.append(filmGenre);
        sb.append(", filmLast = ");
        sb.append(filmLast);
        sb.append(", filmPrice = ");
        sb.append(Integer.toString(filmPrice));
        sb.append("]");
        sb.append(super.toString());

        return sb.toString();
    }
}
