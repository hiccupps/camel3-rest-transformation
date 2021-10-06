package com.example.camelexample.airlines;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class AirlineRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        restConfiguration()
                .enableCORS(true)
                .component("jetty")
                .host("0.0.0.0")
                .port(8080)
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

        rest("/")
                .get("/").to("direct:baseURI");

        rest("/api")
                .get("/getAirlineRouteInfo?srcCity={srcCity}&destCity={destCity}")
                    .to("direct:getAirlineRouteInfo")
                .get("/getAirlinesFromCity?srcCity={srcCity}")
                    .to("direct:getAirlinesFromCity")
                .get("/getAirlineToCity?destCity={destCity}")
                    .to("direct:getAirlineToCity");

        from("direct:baseURI").setBody(simple("Hello Arushi!!"));

        from("direct:getAirlinesFromCity")
                .to("bean:airlineSearchBean?method=getAllFlightsFromCity(${header.srcCity})")
                .log("a route request has been received.");

        from("direct:getAirlineToCity")
                .to("bean:airlineSearchBean?method=getAllFlightsToCity(${header.destCity})")
                .log("a route request has been received.");

        from("direct:getAirlineRouteInfo")
                .to("bean:airlineSearchBean?method=getInfo(${header.srcCity} , ${header.destCity} )")
                .log("a route request has been received.");
    }
}
