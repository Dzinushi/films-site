package com.mev.films.service.implement;


import com.mev.films.mappers.interfaces.BasketMapper;
import com.mev.films.mappers.interfaces.UserDiscountMapper;
import com.mev.films.model.BasketDTO;
import com.mev.films.model.FilmDTO;
import com.mev.films.model.UserDiscountDTO;
import com.mev.films.service.interfaces.BasketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BasketServiceImpl implements BasketService{

    private static Logger LOG = LogManager.getLogger();

    @Autowired private BasketMapper basketMapper;
    @Autowired private UserDiscountMapper userDiscountMapper;

    public BasketServiceImpl(){
    }

    public BasketServiceImpl(BasketMapper basketMapper, UserDiscountMapper userDiscountMapper){
        this.basketMapper = basketMapper;
        this.userDiscountMapper = userDiscountMapper;
    }

    public static void priceByDiscount(BasketDTO basketDTO){
        if (basketDTO.getDiscountDTO() != null){
            FilmDTO filmDTO = basketDTO.getFilmDTO();
            float price_discount = filmDTO.getPrice() - ( filmDTO.getPrice() * basketDTO.getDiscountDTO().getValue() );
            filmDTO.setPrice(Math.round(price_discount));
        }
    }

    @Override
    public List<BasketDTO> getBaskets() {
        LOG.debug("getBaskets");

        List<BasketDTO> basketDTOS = basketMapper.selectBaskets();
        if (basketDTOS != null) {
            for (BasketDTO basketDTO : basketDTOS) {
                priceByDiscount(basketDTO);
            }
        }

        return basketDTOS;
    }

    @Override
    public BasketDTO getBasket(Long id) {
        LOG.debug("getBasket: id = {}",
                id);

        BasketDTO basketDTO = basketMapper.selectBasket(id);
        if (basketDTO != null) {
            priceByDiscount(basketDTO);
        }

        return basketDTO;
    }

    @Override
    public List<BasketDTO> getBasketByUser(Long userId) {
        LOG.debug("getBasketByUser: user_id = {}",
                userId);

        List<BasketDTO> basketDTOS = basketMapper.selectBasketByUser(userId);
        if (basketDTOS != null) {
            for (BasketDTO basketDTO : basketDTOS) {
                priceByDiscount(basketDTO);
            }
        }

        return basketDTOS;
    }

    @Override
    public void addBasket(BasketDTO basketDTO) {
        LOG.debug("addBasket: basketDTO = {}",
                basketDTO);

        if (basketDTO.getDiscountDTO() != null){
            UserDiscountDTO userDiscountDTO = userDiscountMapper.selectUserDiscountByDiscount(basketDTO.getDiscountDTO().getId());

            if (userDiscountDTO == null) {
                userDiscountMapper.insertUserDiscount(new UserDiscountDTO(basketDTO.getUserDTO(), basketDTO.getDiscountDTO(), false));
            }
        }

        basketMapper.insertBasket(basketDTO);
    }

    @Override
    public void updateBasket(BasketDTO basketDTO) {
        LOG.debug("updateBasket: basketDTO = {}",
                basketDTO);

        if (basketDTO.getDiscountDTO() != null){
            BasketDTO basketDTOOld = basketMapper.selectBasket(basketDTO.getId());

            if (basketDTOOld != null) {
                UserDiscountDTO userDiscountDTO = userDiscountMapper.selectUserDiscountByDiscount(basketDTOOld.getDiscountDTO().getId());

                if (userDiscountDTO != null) {
                    userDiscountMapper.updateUserDiscount(new UserDiscountDTO(basketDTO.getUserDTO(), basketDTO.getDiscountDTO(), false));
                }
            }
        }

        basketMapper.updateBasket(basketDTO);
    }

    @Override
    public void deleteBasket(Long id){
        LOG.debug("deleteBasket: id = {}",
                id);

        basketMapper.deleteBasket(id);
    }

    @Override
    public void deleteBasketByUser(Long userId) {
        LOG.debug("deleteBasket: userId = {}",
                userId);

        basketMapper.deleteBasketByUser(userId);
    }

    @Override
    public void deleteBasketByUserFilm(BasketDTO basketDTO) {
        LOG.debug("deleteBasketByUserFilm: basketDTO = {}",
                basketDTO);

        basketMapper.deleteBasketByUserFilm(basketDTO);
    }
}
