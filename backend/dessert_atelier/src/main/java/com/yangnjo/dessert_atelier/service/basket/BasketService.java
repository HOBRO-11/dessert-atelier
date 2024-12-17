package com.yangnjo.dessert_atelier.service.basket;

import com.yangnjo.dessert_atelier.service.basket.dto.BasketAddForm;
import com.yangnjo.dessert_atelier.service.basket.dto.BasketRemoveForm;
import com.yangnjo.dessert_atelier.service.basket.dto.BasketResponseForm;

public interface BasketService {

    BasketResponseForm getBasket(Long memberId);

    /**
     * @param memberId
     * @param form
     * @throw OptionValidateException
     */
    void addBasket(Long memberId, BasketAddForm form);

    void removeBasket(Long memberId, BasketRemoveForm form);

}