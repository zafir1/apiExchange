package com.khabri.crudapp.consumeapi;

import java.util.HashMap;

public class Rates {
    private HashMap<String,Double> map = new HashMap<>();

    public void addRates(String currency,Double value){
        this.map.put(currency,value);
    }

    public HashMap<String, Double> getMap() {
        return map;
    }
}
