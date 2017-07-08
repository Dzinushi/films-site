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

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("BasketDTO [id = ");
        sb.append(id);
        sb.append(", userId = ");
        sb.append(userDTO.getId());
        sb.append(", filmId = ");
        sb.append(filmDTO.getId());
        sb.append(", discountId = ");
        sb.append(discountDTO.getId());
        sb.append("]");
        sb.append(super.toString());

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasketDTO)) return false;

        BasketDTO basketDTO = (BasketDTO) o;

        if (getId() != null ? !getId().equals(basketDTO.getId()) : basketDTO.getId() != null) return false;
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
}
