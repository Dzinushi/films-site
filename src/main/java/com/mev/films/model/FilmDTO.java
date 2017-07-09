package com.mev.films.model;

import java.sql.Time;

public class FilmDTO {
    private Long id;
    private String name;
    private String genre;
    private short duration;
    private int price;
    private String image;

    public FilmDTO(){
    }

    public FilmDTO(String name, String genre, short duration, int price, String image){
        setName(name);
        setGenre(genre);
        setDuration(duration);
        setPrice(price);
        setImage(image);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public short getDuration() {
        return duration;
    }

    public void setDuration(short duration) {
        this.duration = duration;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "FilmDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilmDTO)) return false;

        FilmDTO filmDTO = (FilmDTO) o;

        if (getDuration() != filmDTO.getDuration()) return false;
        if (getPrice() != filmDTO.getPrice()) return false;
        if (getName() != null ? !getName().equals(filmDTO.getName()) : filmDTO.getName() != null) return false;
        if (getGenre() != null ? !getGenre().equals(filmDTO.getGenre()) : filmDTO.getGenre() != null) return false;
        return getImage() != null ? getImage().equals(filmDTO.getImage()) : filmDTO.getImage() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getGenre() != null ? getGenre().hashCode() : 0);
        result = 31 * result + (int) getDuration();
        result = 31 * result + getPrice();
        result = 31 * result + (getImage() != null ? getImage().hashCode() : 0);
        return result;
    }
}
