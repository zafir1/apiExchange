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
    private int count=0;
    private String displayData = "";
    private LocalDate localDate;
    private Map<String, Double> map;
    private String endDate = LocalDate.now().toString();
    private String today = LocalDate.now().toString();
    private RestTemplate restTemplate = new RestTemplate();
    private String url = "https://api.exchangeratesapi.io/";
    private String startDate = LocalDate.now().minusDays(15).toString();

    @GetMapping("/")
    public String Home() {
        return getContentOf(startDate);
    }

    @GetMapping("/{startDate}/{endDate}")
    public String CalculateAverageBetween(@PathVariable(value = "startDate")String startDate,@PathVariable(value = "endDate")String endDate){
        return CalculateAverage(startDate,endDate);
    }

    private String CalculateAverage(String startDate,String endDate){
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate).plusDays(1);

        String display = "";
        for(LocalDate currdate=start;!currdate.isEqual(end);currdate=currdate.plusDays(1)){
            display += getContentOf(currdate.toString());
        }

        return display;
    }

    private String DisplayDataBetween(String startDate,String endDate,String currencies){
//        String currencyList[] = currencies.split("-");

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate).plusDays(1);

        String display = "";
        for(LocalDate currdate=start;!currdate.isEqual(end);currdate=currdate.plusDays(1)){
            display += getContentOf(currdate.toString());
        }
        return display;
    }

    /*
    * Add a map m to this class instance map.
    * */
    private void addMapto(Map<String,Double>m){
        for(Map.Entry<String,Double> entry : m.entrySet()){
            if(this.map.containsKey(entry.getKey())){
                // update value
                this.map.replace(entry.getKey(),entry.getValue(),entry.getValue()+this.map.get(entry.getKey()));
            }else{
                this.map.put(entry.getKey(),entry.getValue());
            }
        }
    }

//    public void divide(int d){
//        for(Map.Entry<String,Double>entry : this.map.entrySet()){
//            Double val = entry.getValue();
//            val /= (Double)(d*1.00);
//            map.replace(entry.getKey(),val);
//        }
//    }

    private CurrentDateData getConversionRatesOn(String date) {
        CurrentDateData currentDateData = restTemplate.getForObject(url+date, CurrentDateData.class);
        return currentDateData;
    }

    private String getContentOf(String date){
        CurrentDateData currentDateData = getConversionRatesOn(date);
        String displayData = "";
        displayData += "Base : "+currentDateData.getBase() + " <br>";
        displayData += "Base date : "+currentDateData.getBaseDate() + " <br>";
        displayData += "Date: " + date + "<br><br>";
        this.map = currentDateData.getRates().getMap();
        displayData+=getMapContent();
        displayData+= "<br><br><hr><br><br>";
        return displayData;
    }

    private String getMapContent(){
        String content = "";
        for(Map.Entry<String,Double>entry : this.map.entrySet()){
            content += entry.getKey() + " -> " + entry.getValue() + " <br>";
        }
        return content;
    }

    private String getMapContent(Map<String,Double>myMap){
        String content = "";
        for(Map.Entry<String,Double>entry : myMap.entrySet()){
            content += entry.getKey() + " -> " + entry.getValue() + " <br>";
        }
        return content;
    }

}
