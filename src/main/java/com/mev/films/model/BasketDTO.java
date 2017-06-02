package com.mev.films.model;


import java.math.BigInteger;

public class BasketDTO {
    private BigInteger basketId;
    private BigInteger userId;
    private BigInteger filmId;
    private BigInteger discountId;

    public BasketDTO(){
    }

    public BigInteger getBasketId() {
        return basketId;
    }

    public void setBasketId(BigInteger basketId) {
        this.basketId = basketId;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getFilmId() {
        return filmId;
    }

    public void setFilmId(BigInteger filmId) {
        this.filmId = filmId;
    }

    public BigInteger getDiscountId() {
        return discountId;
    }

    public void setDiscountId(BigInteger discountId) {
        this.discountId = discountId;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("BasketDTO [basketId = ");
        sb.append(basketId);
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
