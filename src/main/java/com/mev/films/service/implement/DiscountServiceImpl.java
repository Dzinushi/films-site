package com.mev.films.service.implement;


import com.mev.films.mappers.interfaces.DiscountMapper;
import com.mev.films.model.DiscountDTO;
import com.mev.films.service.interfaces.DiscountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DiscountServiceImpl implements DiscountService{

    private static Logger LOG = LogManager.getLogger();

    @Autowired private DiscountMapper discountMapper;

    @Override
    public List<DiscountDTO> getAllDiscounts() {
        LOG.debug("getAllDiscounts");
        return discountMapper.selectDiscounts();
    }

    @Override
    public DiscountDTO getDiscountByCode(String code) {
        LOG.debug("getDiscountByCode: code = {}",
                code);
        return discountMapper.selectDiscountByCode();
    }

    @Override
    public Long addDiscount(DiscountDTO discountDTO) {
        LOG.debug("addDiscount: id = {}, code = {}",
                discountDTO.getId(), discountDTO.getCode());
        return discountMapper.insertDiscount(discountDTO);
    }

    @Override
    public Long updateDiscount(DiscountDTO discountDTO) {
        LOG.debug("updateDiscount: id = {}, code = {}",
                discountDTO.getId(), discountDTO.getCode());
        return discountMapper.updateDiscount(discountDTO);
    }

    @Override
    public Long deleteDiscountByCode(String code) {
        LOG.debug("deleteDiscountByCode: code = {}",
                code);
        return discountMapper.deleteDiscountByCode(code);
    }
}