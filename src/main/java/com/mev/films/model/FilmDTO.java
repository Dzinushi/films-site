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

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("FilmDTO [id = ");
        sb.append(id);
        sb.append(", name = ");
        sb.append(name);
        sb.append(", genre = ");
        sb.append(genre);
        sb.append(", duration = ");
        sb.append(duration);
        sb.append(", price = ");
        sb.append(Integer.toString(price));
        sb.append("]");
        sb.append(super.toString());

        return sb.toString();
    }
}
