package com.mev.films.model;

public class UserDiscountDTO {
    private Long id;
    UserDTO userDTO;
    DiscountDTO discountDTO;
    boolean used;

    public UserDiscountDTO(){

    }

    public UserDiscountDTO(UserDTO userDTO, DiscountDTO discountDTO, boolean used){
        setUserDTO(userDTO);
        setDiscountDTO(discountDTO);
        setUsed(used);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public DiscountDTO getDiscountDTO() {
        return discountDTO;
    }

    public void setDiscountDTO(DiscountDTO discountDTO) {
        this.discountDTO = discountDTO;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDiscountDTO)) return false;

        UserDiscountDTO that = (UserDiscountDTO) o;

        if (isUsed() != that.isUsed()) return false;
        if (getUserDTO() != null ? !getUserDTO().equals(that.getUserDTO()) : that.getUserDTO() != null) return false;
        return getDiscountDTO() != null ? getDiscountDTO().equals(that.getDiscountDTO()) : that.getDiscountDTO() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getUserDTO() != null ? getUserDTO().hashCode() : 0);
        result = 31 * result + (getDiscountDTO() != null ? getDiscountDTO().hashCode() : 0);
        result = 31 * result + (isUsed() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDiscountDTO{" +
                "id=" + id +
                ", userDTO=" + userDTO +
                ", discountDTO=" + discountDTO +
                ", used=" + used +
                '}';
    }
}
