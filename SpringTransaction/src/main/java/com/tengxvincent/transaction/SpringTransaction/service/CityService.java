package com.tengxvincent.transaction.SpringTransaction.service;


import com.tengxvincent.transaction.SpringTransaction.domain.City;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 城市业务逻辑接口类
 *
 * Created by bysocket on 07/02/2017.
 */
public interface CityService {

    @Transactional
    void parent();

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void child();
}
