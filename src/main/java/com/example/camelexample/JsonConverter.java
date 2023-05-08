package com.example.camelexample;

import com.example.camelexample.pojo.Employees;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Converter;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Map;

@Component
@Converter(generateBulkLoader = true)
public class JsonConverter {

    @Converter
    public static Employees toJson(InputStream stream) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        //Map<String, Object> jsonMap = mapper.readValue(inputStream, Map.class);

        return null;
    }
}