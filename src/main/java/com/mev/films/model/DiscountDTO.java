package com.mev.films.model;

public class DiscountDTO {
    private Long id;
    private String code;

    public DiscountDTO(){
    }

    public DiscountDTO(String code){
        setCode(code);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiscountDTO)) return false;

        DiscountDTO that = (DiscountDTO) o;

        return getCode() != null ? getCode().equals(that.getCode()) : that.getCode() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DiscountDTO{" +
                "id=" + id +
                ", code='" + code + '\'' +
                '}';
    }
}
