package com.khabri.crudapp.consumeapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/")
public class CurrencyAverageCalculator {
    private RestTemplate restTemplate = new RestTemplate();
    private String url = "https://api.exchangeratesapi.io/";

    @GetMapping("/")
    public String Home() {
        return getContentOf(LocalDate.now().toString());
    }

    @GetMapping("/{currencies}")
    public String Home(@PathVariable(value = "currencies")String currencies){
        return getContentOf(LocalDate.now().toString(),currencies);
    }

    @GetMapping("/{startDate}/{endDate}")
    public String DisplayDataBetween(@PathVariable(value = "startDate")String startDate, @PathVariable(value = "endDate")String endDate){
        return calculateTotal(startDate,endDate,"");
    }

    @GetMapping("/{startDate}/{endDate}/{currencies}")
    public String DisplayDataBetween(@PathVariable(value = "startDate")String startDate, @PathVariable(value = "endDate")String endDate, @PathVariable(value = "currencies")String currencies){
        return calculateTotal(startDate,endDate,currencies);
    }

    @GetMapping("avg/{startDate}/{endDate}")
    public String DisplayAverageDataBetween(@PathVariable(value = "startDate")String startDate,@PathVariable(value = "endDate")String endDate){
        return calculateAverageBetween(startDate,endDate,"");
    }

    @GetMapping("avg/{startDate}/{endDate}/{currencies}")
    public String DisplayAverageDataBetween(@PathVariable(value = "startDate")String startDate, @PathVariable(value = "endDate")String endDate, @PathVariable(value = "currencies")String currencies){
        return calculateAverageBetween(startDate,endDate,currencies);
    }

    private String calculateAverageBetween(String startDate,String endDate,String currencies){
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate).plusDays(1);
        Map<String,Double>map = getConversionRatesOn(start).getRates().getMap();
        start = start.plusDays(1);
        int count = 1;
        for(LocalDate currdate=start;!currdate.isEqual(end);currdate=currdate.plusDays(1)){
            ++count;
            AddSecondMapIntoFirstMap(map,getConversionRatesOn(currdate).getRates().getMap());
        }
        DivideEachEntry(map,count);
        return getContentOfMap(map,currencies);
    }
    
    private void AddSecondMapIntoFirstMap(Map<String,Double>first,Map<String,Double>second){
        for(Map.Entry<String,Double>entry : second.entrySet()){
            if(first.containsKey(entry.getKey())){
                first.replace(entry.getKey(),first.get(entry.getKey())+entry.getValue());
            }else{
                first.put(entry.getKey(),entry.getValue());
            }
        }
    }

    private void DivideEachEntry(Map<String,Double>map,int divider){
        for(Map.Entry<String,Double>entry : map.entrySet()){
            map.replace(entry.getKey(),(entry.getValue()/(divider*1.00)));
        }
    }

    private String calculateTotal(String startDate, String endDate, String currencies){
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate).plusDays(1);
        String display = "";
        for(LocalDate currdate=start;!currdate.isEqual(end);currdate=currdate.plusDays(1)){
            display += getContentOf(currdate.toString(),currencies);
        }
        return display;
    }

    private String getContentOf(String date){
        return this.getContentOf(date,"");
    }

    private String getContentOf(String date,String currencies){
        CurrentDateData currentDateData = getConversionRatesOn(date);
        String displayData = "";
        displayData += "Base : "+ currentDateData.getBase() + " <br>";
        displayData += "Base date : "+currentDateData.getBaseDate() + " <br>";
        displayData += "Date: " + date + "<br><br>";
        displayData += getContentOfMap(currentDateData.getRates().getMap(),currencies);
        displayData += "<br><br><hr><br><br>";
        return displayData;
    }

    private String getContentOfMap(Map<String,Double>map,String currencies){
        String content = "";
        if(currencies.length()>0){
            String currencyList[] = currencies.split("-");
            for(String currency : currencyList){
                if(map.containsKey(currency)){
                    content += currency + " -> " + map.get(currency) + "<br>";
                }
            }
        }else{
            for(Map.Entry<String,Double>entry : map.entrySet()){
                content += entry.getKey() + " -> " + entry.getValue() + " <br>";
            }
        }
        return content;
    }

    /*
    * Returns Currency rate of a given date.
    * */
    private CurrentDateData getConversionRatesOn(String date) {
        CurrentDateData currentDateData = restTemplate.getForObject(url+date, CurrentDateData.class);
        return currentDateData;
    }

    private CurrentDateData getConversionRatesOn(LocalDate date){
        return getConversionRatesOn(date.toString());
    }

    private Map<String,Double>getMapfromCurrentDateData(CurrentDateData currentDateData){
        return currentDateData.getRates().getMap();
    }
}
