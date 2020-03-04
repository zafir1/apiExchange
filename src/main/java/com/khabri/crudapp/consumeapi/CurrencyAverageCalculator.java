package com.khabri.crudapp.consumeapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CurrencyAverageCalculator {

    @GetMapping("/")
    public String Home(){
        String home = "Index";
        return home;
    }
}
