package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.OrderMapper;
import com.mev.films.model.DiscountDTO;
import com.mev.films.model.FilmDTO;
import com.mev.films.model.OrderDTO;
import com.mev.films.model.UserDTO;
import com.mev.films.service.interfaces.DiscountService;
import com.mev.films.service.interfaces.FilmService;
import com.mev.films.service.interfaces.OrderService;
import com.mev.films.service.interfaces.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{

    private Logger LOG = LogManager.getLogger();

    @Autowired private OrderMapper orderMapper;

    @Autowired private UserService userService;
    @Autowired private FilmService filmService;
    @Autowired private DiscountService discountService;

    public OrderServiceImpl(){
    }

    public OrderServiceImpl(OrderMapper orderMapper, UserService userService, FilmService filmService, DiscountService discountService){
        this.orderMapper = orderMapper;
        this.userService = userService;
        this.filmService = filmService;
        this.discountService = discountService;
    }

    private void priceByDiscount(OrderDTO orderDTO){
        if (orderDTO != null) {
            if (orderDTO.getUserDTO() != null) {
                FilmDTO filmDTO = orderDTO.getFilmDTO();
                if (filmDTO != null) {
                    DiscountDTO discountDTO = orderDTO.getDiscountDTO();
                    if (discountDTO != null){
                        Integer price = filmDTO.getPrice();
                        Float value = discountDTO.getValue();
                        if (price != null && value != null){

                            Float price_discount = price - (price * value);
                            filmDTO.setPrice(Math.round(price_discount));

                        }
                    }
                }
                else {
                    LOG.debug("Error in 'priceByDiscount'! 'filmDTO' is null");
                }
            }
        }
    }

    @Override
    public List<OrderDTO> getOrders() {

        LOG.debug("getOrders:");

        List<OrderDTO> orderDTOS = orderMapper.selectOrders();
        if (orderDTOS != null){
            for (OrderDTO orderDTO : orderDTOS){
                priceByDiscount(orderDTO);
            }
        }

        return orderDTOS;
    }

    @Override
    public OrderDTO getOrder(Long id) {

        LOG.debug("getOrder: id = {}",
                id);

        OrderDTO orderDTO = null;
        if (id != null && id >= 0){
            orderDTO = orderMapper.selectOrder(id);
            priceByDiscount(orderDTO);
        }
        else {
            LOG.debug("Error in 'getOrder'! 'id' is not validate: id = {}",
                    id);
        }

        return orderDTO;
    }

    @Override
    public List<OrderDTO> getOrderByUser(Long userId) {

        LOG.debug("getOrderByUser: user_id = {}",
                userId);

        List<OrderDTO> orderDTOS = null;
        if (userId != null && userId >= 0){
            orderDTOS = orderMapper.selectOrderByUser(userId);
            for (OrderDTO orderDTO : orderDTOS){
                priceByDiscount(orderDTO);
            }
        }
        else {
            LOG.debug("Error in 'getOrderByUser'! 'user_id' is not validate: user_id = {}",
                    userId);
        }

        return orderDTOS;
    }

    @Override
    public List<OrderDTO> getOrderByUserIsMark(Long userId) {

        LOG.debug("getOrderByUserIsMark: user_id = {}",
                userId);

        List<OrderDTO> orderDTOS = null;
        if (userId != null && userId >= 0){
            orderDTOS = orderMapper.selectOrderByUserIsMark(userId);
        }
        else {
            LOG.debug("Error in 'getOrderByUserIsMark'! 'user_id' is not validate: user_id = {}",
                    userId);
        }

        return orderDTOS;
    }

    @Override
    public void addOrder(OrderDTO orderDTO) {

        LOG.debug("addOrder: {}",
                orderDTO);

        Boolean userOk = false;
        Boolean filmOk = false;
        Boolean discountOk = false;

        if (orderDTO != null){
            if (orderDTO.getUserDTO() != null) {
                Long userId = orderDTO.getUserDTO().getId();
                if (userId != null && userId >= 0) {
                    UserDTO userDTO = userService.getUser(userId);
                    if (userDTO != null) {
                        userOk = true;
                    } else {
                        LOG.debug("Error in 'addOrder'! User not found");
                    }
                } else {
                    LOG.debug("Error in 'addOrder'! 'user_id' is not validate: user_id = {}",
                            userId);
                }
            } else {
                LOG.debug("Error in 'addOrder'! 'userDTO' is null");
            }

            if (orderDTO.getFilmDTO() != null) {
                Long filmId = orderDTO.getFilmDTO().getId();
                if (filmId != null && filmId >= 0) {
                    FilmDTO filmDTO = filmService.getFilm(filmId);
                    if (filmDTO != null) {
                        filmOk = true;
                    } else {
                        LOG.debug("Error in 'addOrder'! Film not found");
                    }
                } else {
                    LOG.debug("Error in 'addOrder'! 'film_id' is not validate: film_id = {}",
                            filmId);
                }
            } else {
                LOG.debug("Error in 'addOrder'! 'filmDTO' is null");
            }

            if (orderDTO.getDiscountDTO() != null) {
                Long discountId = orderDTO.getDiscountDTO().getId();
                if (discountId != null && discountId >= 0) {
                    DiscountDTO discountDTO = discountService.getDiscount(discountId);
                    if (discountDTO != null) {
                        discountOk = true;
                    } else {
                        LOG.debug("Error in 'addOrder'! Discount not found");
                    }
                } else {
                    LOG.debug("Error in 'addOrder'! 'discount_id' is not validate: discount_id = {}",
                            discountId);
                }
            } else {
                discountOk = true;
            }
        } else {
            LOG.debug("Error in 'addOrder'! 'orderDTO' is null");
        }

        if (userOk && filmOk && discountOk){
            orderMapper.insertOrder(orderDTO);
        }
    }

    @Override
    public void deleteOrder(Long id) {

        LOG.debug("deleteOrder: id = {}",
                id);

        if (id != null && id >= 0){
            orderMapper.deleteOrder(id);
        }
        else {
            LOG.debug("Error in 'deleteOrder'! 'id' is not validate: id = {}",
                    id);
        }
    }

    @Override
    public void deleteOrderByUser(Long userId) {

        LOG.debug("deleteOrderByUser: user_id = {}",
                userId);

        if (userId != null && userId >= 0){
            orderMapper.deleteOrderByUser(userId);
        } else {
            LOG.debug("Error in 'deleteOrderByUser'! 'user_id' is not validate: user_id = {}",
                    userId);
        }
    }
}
