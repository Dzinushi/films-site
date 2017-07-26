package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.UserDiscountMapper;
import com.mev.films.model.DiscountDTO;
import com.mev.films.model.UserDTO;
import com.mev.films.model.UserDiscountDTO;
import com.mev.films.service.interfaces.DiscountService;
import com.mev.films.service.interfaces.UserDiscountService;
import com.mev.films.service.interfaces.UserService;
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

    @Autowired private UserService userService;
    @Autowired private DiscountService discountService;

    public UserDiscountServiceImpl(){
    }

    public UserDiscountServiceImpl(UserDiscountMapper userDiscountMapper){
        this.userDiscountMapper = userDiscountMapper;
    }

    private boolean checkUserDiscount(UserDiscountDTO userDiscountDTO, boolean idCheck, String functionName){
        boolean userOk = false;
        boolean discountOk = false;
        boolean idOk = true;

        UserDTO userDTO = userDiscountDTO.getUserDTO();
        if (userDTO != null){
            Long userId = userDTO.getId();
            if (userId != null && userId  >= 0){
                if (userService.getUser(userId) != null){
                    userOk = true;
                }
                else {
                    LOG.debug("Error in " + "'" + functionName + "'! User not found");
                }
            }
            else {
                LOG.debug("Error in " + "'" + functionName + "'! 'user_id is not validate: user_id = {}",
                        userId);
            }
        }
        else {
            LOG.debug("Error in " + "'" + functionName + "'! 'userDTO' is null");
        }

        DiscountDTO discountDTO = userDiscountDTO.getDiscountDTO();
        if (discountDTO != null){
            Long discountId = discountDTO.getId();
            if (discountId != null && discountId >= 0){
                if (discountService.getDiscount(discountId) != null){
                    discountOk = true;
                }
                else {
                    LOG.debug("Error in " + "'" + functionName + "'! Discount not found");
                }
            }
            else {
                LOG.debug("Error in " + "'" + functionName + "'! 'discount_id' is not validate: discount_id = {}",
                        discountId);
            }
        }
        else {
            LOG.debug("Error in " + "'" + functionName + "'! 'discountDTO' is null");
        }

        if (idCheck){
            idOk = userDiscountDTO.getId() != null && userDiscountDTO.getId() >= 0;
        }

        return userOk && discountOk && idOk;
    }

    @Override
    public List<UserDiscountDTO> getUserDiscounts() {
        LOG.debug("getUserDiscounts");

        return userDiscountMapper.selectUserDiscounts();
    }

    @Override
    public UserDiscountDTO getUserDiscount(Long id) {
        LOG.debug("getUserDiscount: id = {}",
                id);

        UserDiscountDTO userDiscountDTO = null;
        if (id != null && id >= 0){
            userDiscountDTO = userDiscountMapper.selectUserDiscount(id);
        }
        else {
            LOG.debug("Error in 'getUserDiscount'! 'id' is not validate: id = {}",
                    id);
        }

        return userDiscountDTO;
    }

    @Override
    public List<UserDiscountDTO> getUserDiscountsByUser(Long userId) {
        LOG.debug("getUserDiscountsByUser: user_id = {}",
                userId);

        List<UserDiscountDTO> userDiscountDTOS = null;
        if (userId != null && userId >= 0){
            userDiscountDTOS = userDiscountMapper.selectUserDiscountsByUser(userId);
        }
        else {
            LOG.debug("Error in 'getUserDiscountsByUser'! 'user_id is not validate: user_id = {}",
                    userId);
        }

        return userDiscountDTOS;
    }

    @Override
    public UserDiscountDTO getUserDiscountByDiscount(Long discountId) {
        LOG.debug("getUserDiscountByDiscount: discount_id = {}",
                discountId);

        UserDiscountDTO userDiscountDTO = null;
        if (discountId != null && discountId >= 0){
            userDiscountDTO = userDiscountMapper.selectUserDiscountByDiscount(discountId);
        }
        else {
            LOG.debug("Error in 'getUserDiscountByDiscount'! 'discount_id is not validate: discount_id = {}",
                    discountId);
        }

        return userDiscountDTO;
    }

    @Override
    public void addUserDiscount(UserDiscountDTO userDiscountDTO) {
        LOG.debug("addUserDiscount: userDiscountDTO = {}",
                userDiscountDTO);

        if (checkUserDiscount(userDiscountDTO, false, "addUserDiscount")) {
            userDiscountMapper.insertUserDiscount(userDiscountDTO);
        }
    }

    @Override
    public void updateUserDiscount(UserDiscountDTO userDiscountDTO) {
        LOG.debug("updateUserDiscount: userDiscountDTO = {}",
                userDiscountDTO);

        if (checkUserDiscount(userDiscountDTO, true, "updateUserDiscount")) {
            userDiscountMapper.updateUserDiscount(userDiscountDTO);
        }
    }

    @Override
    public void deleteUserDiscount(Long id) {
        LOG.debug("deleteUserDiscount: id = {}",
                id);

        if (id != null && id >= 0){
            userDiscountMapper.deleteUserDiscount(id);
        }
        else {
            LOG.debug("Error in 'deleteUserDiscount'! 'id' is not validate: id = {}",
                    id);
        }
    }

    @Override
    public void deleteUserDiscountByDiscount(Long discountId) {
        LOG.debug("deleteUserDiscountByDiscount: discount_id = {}",
                discountId);

        if (discountId != null && discountId >= 0){
            DiscountDTO discountDTO = discountService.getDiscount(discountId);
            if (discountDTO != null) {
                userDiscountMapper.deleteUserDiscountByDiscount(discountId);
            }
            else {
                LOG.debug("Error in 'deleteUserDiscountByDiscount'! Discount is not found");
            }
        }
        else {
            LOG.debug("Error in 'deleteUserDiscountByDiscount'! 'id' is not validate: id = {}",
                    discountId);
        }
    }

    @Override
    public void deleteUserDiscountByUser(Long userId) {
        LOG.debug("deleteUserDiscountByUser: user_id = {}",
                userId);

        if (userId != null && userId >= 0){
            UserDTO userDTO = userService.getUser(userId);
            if (userDTO != null) {
                userDiscountMapper.deleteUserDiscountByUser(userId);
            }
            else {
                LOG.debug("Error in 'deleteUserDiscountByUser'! User is not found");
            }
        }
        else {
            LOG.debug("Error in 'deleteUserDiscountByUser'! 'id' is not validate: id = {}",
                    userId);
        }
    }
}
