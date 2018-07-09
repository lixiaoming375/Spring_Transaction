package com.tengxvincent.transaction.SpringTransaction.controller;

import com.tengxvincent.transaction.SpringTransaction.domain.City;
import com.tengxvincent.transaction.SpringTransaction.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bysocket on 07/02/2017.
 */
@RestController
public class CityRestController {

    @Autowired
    private CityService cityService;


    @PutMapping(value = "/api/transaction/test")
    public void testTransaction(){

        cityService.parent();
    }

}
