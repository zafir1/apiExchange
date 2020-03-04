package com.khabri.crudapp.consumeapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/")
public class CurrencyAverageCalculator {

    private String url = "https://api.exchangeratesapi.io/";
    private LocalDate localDate;
    private String startDate = LocalDate.now().minusDays(15).toString();
    private String endDate = LocalDate.now().toString();
    private String urlToVisit =  url + startDate;
    private Map<String,Double>map;

    @GetMapping("/")
    public String Home(){
        String home = "Index";
        return home;
    }

    @GetMapping("/")
    public String CalculateAverageTill(@PathVariable(value = "endDate")String endDate){
        this.endDate = endDate;
        this.startDate = getLocalDate(endDate).minusDays(15).toString();
        return "";
    }

    public String ConnectToInternet(){
        return "";
    }

    private LocalDate getLocalDate(String date){
        this.localDate = LocalDate.parse(date);
        return localDate;
    }

}
