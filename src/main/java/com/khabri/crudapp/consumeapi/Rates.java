package com.khabri.crudapp.consumeapi;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rates {
    private HashMap<String,Double> map = new HashMap<>();

    @JsonAnySetter
    public void addRates(String currency,Double value){
        this.map.put(currency,value);
    }

    public HashMap<String, Double> getMap() {
        return map;
    }
}
