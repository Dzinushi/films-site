package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.UserDiscountMapper;
import com.mev.films.model.UserDiscountDTO;
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

    @Override
    public List<UserDiscountDTO> getUserDiscounts() {
        LOG.debug("getUserDiscounts");

        return userDiscountMapper.selectUserDiscounts();
    }

    @Override
    public UserDiscountDTO getUserDiscount(Long id) {
        LOG.debug("getUserDiscount: id = {}",
                id);

        return userDiscountMapper.selectUserDiscount(id);
    }

    @Override
    public List<UserDiscountDTO> getUserDiscountsByUser(Long userId) {
        LOG.debug("getUserDiscountsByUser: user_id = {}",
                userId);

        return userDiscountMapper.selectUserDiscountsByUser(userId);
    }

    @Override
    public UserDiscountDTO getUserDiscountByDiscount(Long discountId) {
        LOG.debug("getUserDiscountByDiscount: discount_id = {}",
                discountId);

        return userDiscountMapper.selectUserDiscountByDiscount(discountId);
    }

    @Override
    public void addUserDiscount(UserDiscountDTO userDiscountDTO) {
        LOG.debug("addUserDiscount: userDiscountDTO = {}",
                userDiscountDTO);

        userDiscountMapper.insertUserDiscount(userDiscountDTO);
    }

    @Override
    public void updateUserDiscount(UserDiscountDTO userDiscountDTO) {
        LOG.debug("updateUserDiscount: userDiscountDTO = {}",
                userDiscountDTO);

        userDiscountMapper.updateUserDiscount(userDiscountDTO);
    }

    @Override
    public void deleteUserDiscount(Long id) {
        LOG.debug("deleteUserDiscount: id = {}",
                id);

        userDiscountMapper.deleteUserDiscount(id);
    }

    @Override
    public void deleteUserDiscountByDiscount(Long discountId) {
        LOG.debug("deleteUserDiscountByDiscount: discount_id = {}",
                discountId);

        userDiscountMapper.deleteUserDiscountByDiscount(discountId);
    }

    @Override
    public void deleteUserDiscountByUser(Long userId) {
        LOG.debug("deleteUserDiscountByUser: user_id = {}",
                userId);

        userDiscountMapper.deleteUserDiscountByUser(userId);
    }
}
