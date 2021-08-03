package com.example.camelexample;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import java.util.HashMap;

//@Component
public class MyRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {


        restConfiguration()
                .enableCORS(true)
                .component("jetty")
                .host("0.0.0.0")
                .port(8084)
                .bindingMode(RestBindingMode.json)    ;


        Processor p1 = new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {

                exchange.getIn().getHeaders();
                HashMap<String, String> map = exchange.getIn().getBody(HashMap.class);
                String password = map.get("pwd");
                String user = map.get("usrname");

                String result = "false";

                if(user.equals("abhishek")){
                    if(password.equals("hello")){
                        result="true";
                    }
                }


                exchange.getIn().setBody(result);
            }
        };

        rest("/api/getData")
                .get("/getCountry?cityName={cityName}")
                .to("direct:getCountry")
                .get("/getZipCode?cityName={cityName}")
                .to("direct:getZipCode");
        //.route()
        // .log("A request has been recieved.");



        from("direct:getCountry")
                .process(p1)
                .to("bean:searchBean?method=getCountry(${header.cityName})")
                .log("A request has been recieved.");

        from("direct:getZipCode")
                .to("bean:searchBean?method=getZipcode(${header.cityName})")
                .log("A request has been recieved.");


        rest("/api")
                .post("/login").to("direct:loginApi");

        from("direct:loginApi")
                .process(p1)
                .log("a login request has been recieved.");


    }
}
