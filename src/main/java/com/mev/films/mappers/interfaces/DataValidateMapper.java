package com.mev.films.mappers.interfaces;

import com.mev.films.model.DataValidateDTO;

import java.util.List;

public interface DataValidateMapper {
    List<DataValidateDTO> selectDataValidates(Long limit, Long offset);
    DataValidateDTO selectDataValidate(Long id);
    Long selectDataValidateCount();
    void insertDataValidate(DataValidateDTO dataValidateDTO);
    void updateDataValidate(DataValidateDTO dataValidateDTO);
    void deleteDataValidate(Long id);
}
