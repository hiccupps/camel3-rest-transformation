package com.example.camelexample;


import com.example.camelexample.pojo.Country;
import org.springframework.stereotype.Component;

@Component
public class SearchBean {

    public Country getCountry(String city){

        Country country = new Country();

         country.setCountryName("UNKNOWN");
        if(city.equals("DELHI")){
            country.setCountryName("INDIA");
        }

        country.setContinent("ASIA");
        country.setGovernanceType("DEMOCRACY");

        return country;
    }

    public String getZipcode(String city){

        String zipCode = "UNKNOWN";
        if(city.equals("Ghaziabad")){
            zipCode="201010";
        }
        return zipCode;
    }





}
