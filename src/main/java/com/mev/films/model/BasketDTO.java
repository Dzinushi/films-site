package com.mev.films.model;

public class BasketDTO {
    private Long id;
    private Long userId;
    private Long filmId;
    private Long discountId;

    public BasketDTO(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("BasketDTO [id = ");
        sb.append(id);
        sb.append(", userId = ");
        sb.append(userId);
        sb.append(", filmId = ");
        sb.append(filmId);
        sb.append(", discountId = ");
        sb.append(discountId);
        sb.append("]");
        sb.append(super.toString());

        return sb.toString();
    }
}
