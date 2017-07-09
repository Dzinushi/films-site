package com.mev.films.model;

public class BasketDTO {
    private Long id;
    private UserDTO userDTO;
    private FilmDTO filmDTO;
    private DiscountDTO discountDTO;

    public BasketDTO(){
    }

    public BasketDTO(UserDTO userDTO, FilmDTO filmDTO, DiscountDTO discountDTO){
        setUserDTO(userDTO);
        setFilmDTO(filmDTO);
        setDiscountDTO(discountDTO);
    }

    public BasketDTO(Long id, UserDTO userDTO, FilmDTO filmDTO, DiscountDTO discountDTO){
        setId(id);
        setUserDTO(userDTO);
        setFilmDTO(filmDTO);
        setDiscountDTO(discountDTO);
    }

    public void setUserDTO(UserDTO userDTO){
        this.userDTO = userDTO;
    }

    public void setFilmDTO(FilmDTO filmDTO){
        this.filmDTO = filmDTO;
    }

    public void setDiscountDTO(DiscountDTO discountDTO){
        this.discountDTO = discountDTO;
    }

    public UserDTO getUserDTO(){
        return userDTO;
    }

    public FilmDTO getFilmDTO(){
        return filmDTO;
    }

    public DiscountDTO getDiscountDTO(){
        return discountDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasketDTO)) return false;

        BasketDTO basketDTO = (BasketDTO) o;

        if (getUserDTO() != null ? !getUserDTO().equals(basketDTO.getUserDTO()) : basketDTO.getUserDTO() != null)
            return false;
        if (getFilmDTO() != null ? !getFilmDTO().equals(basketDTO.getFilmDTO()) : basketDTO.getFilmDTO() != null)
            return false;
        return getDiscountDTO() != null ? getDiscountDTO().equals(basketDTO.getDiscountDTO()) : basketDTO.getDiscountDTO() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getUserDTO() != null ? getUserDTO().hashCode() : 0);
        result = 31 * result + (getFilmDTO() != null ? getFilmDTO().hashCode() : 0);
        result = 31 * result + (getDiscountDTO() != null ? getDiscountDTO().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BasketDTO{" +
                "id=" + id +
                ", userDTO=" + userDTO +
                ", filmDTO=" + filmDTO +
                ", discountDTO=" + discountDTO +
                '}';
    }
}
