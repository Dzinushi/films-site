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

        DiscountDTO discountDTO = null;
        if (id != null && id >= 0){
            discountDTO = discountMapper.selectDiscount(id);
        }
        else {
            LOG.debug("Error in 'getDiscount'! 'id' is not validate: id = {}",
                    id);
        }

        return discountDTO;
    }

    @Override
    public DiscountDTO getDiscountByCode(String code) {
        LOG.debug("getDiscountByCode: code = {}",
                code);

        DiscountDTO discountDTO = null;
        if (code != null){
            discountDTO = discountMapper.selectDiscountByCode(code);
        }
        else {
            LOG.debug("Error on 'getDiscountByCode'! 'code' is null");
        }

        return discountDTO;
    }

    @Override
    public void addDiscount(DiscountDTO discountDTO) {
        LOG.debug("addDiscount: discountDTO = {}",
                discountDTO);

        if (discountDTO != null){
            if (discountDTO.getCode() != null){
                if (discountDTO.getValue() > 0 && discountDTO.getValue() < 0.75F){

                    discountMapper.insertDiscount(discountDTO);

                }
                else {
                    LOG.debug("Error in 'addDiscount!' 'value' is not validate: value = {}",
                            discountDTO.getValue());
                }
            }
            else {
                LOG.debug("Error in 'addDiscount!' 'code' is null");
            }
        }
        else {
            LOG.debug("Error in 'addDiscount!' 'discountDTO' is null");
        }
    }

    @Override
    public void updateDiscount(DiscountDTO discountDTO) {
        LOG.debug("updateDiscount: discountDTO = {}",
                discountDTO);

        if (discountDTO != null) {
            if (discountDTO.getId() != null && discountDTO.getId() >= 0) {
                if (discountDTO.getCode() != null) {
                    if (discountDTO.getValue() > 0 && discountDTO.getValue() < 0.75F) {

                        discountMapper.updateDiscount(discountDTO);

                    } else {
                        LOG.debug("Error in 'updateDiscount!' 'value' is not validate: value = {}",
                                discountDTO.getValue());
                    }
                } else {
                    LOG.debug("Error in 'updateDiscount!' 'code' is null");
                }
            }
            else {
                LOG.debug("Error in 'updateDiscount'! 'id' is not validate: id = {}",
                        discountDTO.getId());
            }
        }
        else {
            LOG.debug("Error in 'updateDiscount!' 'discountDTO' is null");
        }

        discountMapper.updateDiscount(discountDTO);
    }

    @Override
    public void deleteDiscount(Long id) {
        LOG.debug("deleteDiscount: id = {}",
                id);

        if (id != null && id >= 0){
            discountMapper.deleteDiscount(id);
        }
        else {
            LOG.debug("Error in 'deleteDiscount'! 'id' is not validate: id = {}",
                    id);
        }
    }

    @Override
    public void deleteDiscountByCode(String code) {
        LOG.debug("deleteDiscountByCode: code = {}",
                code);

        if (code != null){
            discountMapper.deleteDiscountByCode(code);
        }
        else {
            LOG.debug("Error in 'deleteDiscountByCode'! 'code' is null");
        }
    }
}