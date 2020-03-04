package com.khabri.crudapp.consumeapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/")
public class CurrencyAverageCalculator {

    @GetMapping("/")
    public String Home(){
        String home = "Index";
        return home;
    }

}
