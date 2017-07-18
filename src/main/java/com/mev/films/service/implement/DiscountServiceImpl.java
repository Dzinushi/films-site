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

    public DiscountServiceImpl(){
    }

    public DiscountServiceImpl(DiscountMapper discountMapper){
        this.discountMapper = discountMapper;
    }

    @Override
    public List<DiscountDTO> getDiscounts() {
        LOG.debug("getDiscounts");

        return discountMapper.selectDiscounts();
    }

    @Override
    public DiscountDTO getDiscount(Long id) {
        LOG.debug("getDiscount: id = {}",
                id);

        return discountMapper.selectDiscount(id);
    }

    @Override
    public DiscountDTO getDiscountByCode(String code) {
        LOG.debug("getDiscountByCode: code = {}",
                code);

        return discountMapper.selectDiscountByCode(code);
    }

    @Override
    public void addDiscount(DiscountDTO discountDTO) {
        LOG.debug("addDiscount: discountDTO = {}",
                discountDTO);

        discountMapper.insertDiscount(discountDTO);
    }

    @Override
    public void updateDiscount(DiscountDTO discountDTO) {
        LOG.debug("updateDiscount: discountDTO = {}",
                discountDTO);

        discountMapper.updateDiscount(discountDTO);
    }

    @Override
    public void deleteDiscountByCode(String code) {
        LOG.debug("deleteDiscountByCode: code = {}",
                code);

        discountMapper.deleteDiscountByCode(code);
    }
}