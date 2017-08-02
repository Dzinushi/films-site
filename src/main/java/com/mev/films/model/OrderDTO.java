package com.mev.films.model;

import java.sql.Timestamp;

public class OrderDTO {

    private Long id;
    private BasketDTO basketDTO;
    private FilmDTO filmDTO;
    private DiscountDTO discountDTO;
    private Integer priceByDiscount;
    private Boolean mark;
    private Timestamp time;

    public OrderDTO(){
    }

    public OrderDTO(BasketDTO basketDTO, FilmDTO filmDTO, DiscountDTO discountDTO, Boolean mark){
        this.basketDTO = basketDTO;
        this.filmDTO = filmDTO;
        this.discountDTO = discountDTO;
        this.mark = mark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBasketDTO(BasketDTO basketDTO) {
        this.basketDTO = basketDTO;
    }

    public BasketDTO getBasketDTO() {
        return basketDTO;
    }

    public FilmDTO getFilmDTO() {
        return filmDTO;
    }

    public void setFilmDTO(FilmDTO filmDTO) {
        this.filmDTO = filmDTO;
    }

    public DiscountDTO getDiscountDTO() {
        return discountDTO;
    }

    public void setDiscountDTO(DiscountDTO discountDTO) {
        this.discountDTO = discountDTO;
    }

    public void setPriceByDiscount(Integer priceByDiscount) {
        this.priceByDiscount = priceByDiscount;
    }

    public Integer getPriceByDiscount() {
        return priceByDiscount;
    }

    public Boolean isMark() {
        return mark;
    }

    public void setMark(Boolean mark) {
        this.mark = mark;
    }

    public Timestamp getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDTO)) return false;

        OrderDTO orderDTO = (OrderDTO) o;

        if (getBasketDTO() != null ? !getBasketDTO().equals(orderDTO.getBasketDTO()) : orderDTO.getBasketDTO() != null)
            return false;
        if (getFilmDTO() != null ? !getFilmDTO().equals(orderDTO.getFilmDTO()) : orderDTO.getFilmDTO() != null)
            return false;
        if (getDiscountDTO() != null ? !getDiscountDTO().equals(orderDTO.getDiscountDTO()) : orderDTO.getDiscountDTO() != null)
            return false;
        if (getPriceByDiscount() != null ? !getPriceByDiscount().equals(orderDTO.getPriceByDiscount()) : orderDTO.getPriceByDiscount() != null)
            return false;
        return mark != null ? mark.equals(orderDTO.mark) : orderDTO.mark == null;
    }

    @Override
    public int hashCode() {
        int result = getBasketDTO() != null ? getBasketDTO().hashCode() : 0;
        result = 31 * result + (getFilmDTO() != null ? getFilmDTO().hashCode() : 0);
        result = 31 * result + (getDiscountDTO() != null ? getDiscountDTO().hashCode() : 0);
        result = 31 * result + (getPriceByDiscount() != null ? getPriceByDiscount().hashCode() : 0);
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", basketDTO=" + basketDTO +
                ", filmDTO=" + filmDTO +
                ", discountDTO=" + discountDTO +
                ", priceByDiscount=" + priceByDiscount +
                ", mark=" + mark +
                ", time=" + time +
                '}';
    }
}
