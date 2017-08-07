package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.UserDiscountMapper;
import com.mev.films.model.UserDiscountDTO;
import com.mev.films.service.interfaces.ExceptionService;
import com.mev.films.service.interfaces.UserDiscountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserDiscountServiceImpl implements UserDiscountService{

    private Logger LOG = LogManager.getLogger();

    @Autowired private UserDiscountMapper userDiscountMapper;

    @Autowired private ExceptionService exceptionService;

    public UserDiscountServiceImpl(){
    }

    public UserDiscountServiceImpl(UserDiscountMapper userDiscountMapper, ExceptionService exceptionService){
        this.userDiscountMapper = userDiscountMapper;
        this.exceptionService = exceptionService;
    }

    @Override
    public List<UserDiscountDTO> getUserDiscounts() {
        LOG.debug("getUserDiscounts");

        return userDiscountMapper.selectsAll();
    }

    @Override
    public UserDiscountDTO getUserDiscount(Long id) {
        LOG.debug("getUserDiscount: id = {}",
                id);

        exceptionService.checkUserDiscountId(id);

        return userDiscountMapper.select(id);
    }

    @Override
    public List<UserDiscountDTO> getUserDiscountsByUser(Long userId) {
        LOG.debug("getUserDiscountsByUser: user_id = {}",
                userId);

        exceptionService.checkUserDiscountUserId(userId);

        return userDiscountMapper.selectsByUser(userId);
    }

    @Override
    public UserDiscountDTO getUserDiscountByDiscount(Long discountId) {
        LOG.debug("getUserDiscountByDiscount: discount_id = {}",
                discountId);

        exceptionService.checkUserDiscountDiscountId(discountId);

        return userDiscountMapper.selectByDiscount(discountId);
    }

    @Override
    public void addUserDiscount(UserDiscountDTO userDiscountDTO) {
        LOG.debug("addUserDiscount: userDiscountDTO = {}",
                userDiscountDTO);

        exceptionService.checkUserDiscountWithoutId(userDiscountDTO);

        userDiscountMapper.insert(userDiscountDTO);
    }

    @Override
    public void updateUserDiscount(UserDiscountDTO userDiscountDTO) {
        LOG.debug("update: userDiscountDTO = {}",
                userDiscountDTO);

        exceptionService.checkUserDiscount(userDiscountDTO);

        userDiscountMapper.update(userDiscountDTO);
    }

    @Override
    public void deleteUserDiscount(Long id) {
        LOG.debug("delete: id = {}",
                id);

        exceptionService.checkUserDiscountId(id);

        userDiscountMapper.delete(id);
    }

    @Override
    public void deleteUserDiscountByDiscount(Long discountId) {
        LOG.debug("deleteByDiscount: discount_id = {}",
                discountId);

        exceptionService.checkUserDiscountDiscountId(discountId);

        userDiscountMapper.deleteByDiscount(discountId);
    }

    @Override
    public void deleteUserDiscountByUser(Long userId) {
        LOG.debug("deleteByUser: user_id = {}",
                userId);

        exceptionService.checkUserDiscountUserId(userId);

        userDiscountMapper.deleteByUser(userId);
    }
}
