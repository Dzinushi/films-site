package com.mev.films.mappers.interfaces;

import com.mev.films.model.DataValidateDTO;

import java.util.List;

public interface DataValidateMapper {
    List<DataValidateDTO> selects(Long limit, Long offset);
    DataValidateDTO select(Long id);
    Long selectsCount();
    void insert(DataValidateDTO dataValidateDTO);
    void update(DataValidateDTO dataValidateDTO);
    void delete(Long id);
}
