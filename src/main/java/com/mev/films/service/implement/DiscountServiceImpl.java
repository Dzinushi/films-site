package com.mev.films.service.implement;


import com.mev.films.mappers.interfaces.DiscountMapper;
import com.mev.films.model.DiscountDTO;
import com.mev.films.service.interfaces.DiscountService;
import com.mev.films.service.interfaces.ExceptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DiscountServiceImpl implements DiscountService{

    private static Logger LOG = LogManager.getLogger();

    @Autowired private DiscountMapper discountMapper;

    @Autowired private ExceptionService exceptionService;

    public DiscountServiceImpl(){
    }

    public DiscountServiceImpl(DiscountMapper discountMapper, ExceptionService exceptionService){
        this.discountMapper = discountMapper;
        this.exceptionService = exceptionService;
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

        exceptionService.checkDiscountId(id);

        return discountMapper.selectDiscount(id);
    }

    @Override
    public DiscountDTO getDiscountByCode(String code) {
        LOG.debug("getDiscountByCode: code = {}",
                code);

        exceptionService.checkDiscountCode(code);

        return discountMapper.selectDiscountByCode(code);
    }

    @Override
    public void addDiscount(DiscountDTO discountDTO) {
        LOG.debug("addDiscount: discountDTO = {}",
                discountDTO);

        exceptionService.checkDiscountWithoutId(discountDTO);

        discountMapper.insertDiscount(discountDTO);
    }

    @Override
    public void updateDiscount(DiscountDTO discountDTO) {
        LOG.debug("updateDiscount: discountDTO = {}",
                discountDTO);

        exceptionService.checkDiscount(discountDTO);

        discountMapper.updateDiscount(discountDTO);
    }

    @Override
    public void deleteDiscount(Long id) {
        LOG.debug("deleteDiscount: id = {}",
                id);

        exceptionService.checkDiscountId(id);

        discountMapper.deleteDiscount(id);
    }

    @Override
    public void deleteDiscountByCode(String code) {
        LOG.debug("deleteDiscountByCode: code = {}",
                code);

        exceptionService.checkDiscountCode(code);

        discountMapper.deleteDiscountByCode(code);
    }
}