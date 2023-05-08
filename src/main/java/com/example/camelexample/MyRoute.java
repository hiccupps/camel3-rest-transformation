package com.example.camelexample;

import com.example.camelexample.pojo.Employee;
import com.example.camelexample.pojo.Employees;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.TypeConverter;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.apache.camel.model.dataformat.JaxbDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spi.TypeConverterRegistry;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class MyRoute extends RouteBuilder {

    private static List<Employee> employees = new ArrayList<>();

    Processor myProcessor = new Processor() {

        @Override
        public void process(Exchange exchange) throws Exception {
            Employee emp = exchange.getIn().getBody(Employee.class);
            emp.setAge("20");
            exchange.getIn().setBody(emp , Employee.class);
        }
    };

    @Override
    public void configure() throws Exception {


        restConfiguration()
                .enableCORS(true)
                .component("jetty")
                .host("0.0.0.0")
                .port(8084);

        // XML Data Format
        JaxbDataFormat xmlFormat = new JaxbDataFormat();
        xmlFormat.setPartClass(Employee.class.getName());

        JaxbDataFormat listxmlFormat = new JaxbDataFormat();
        listxmlFormat.setPartClass(Employees.class.getName());

        // JSON Data Format
        JacksonDataFormat jdf =  new JacksonDataFormat(Employee.class);
        JacksonDataFormat ljdf1 =  new JacksonDataFormat(Employees.class);



        rest("/api")
                .post("/getData")
                .to("direct:getData")
                .post("/getListData")
                .to("direct:getListData");

        from("direct:getData")
                .log(LoggingLevel.INFO, log, "New message received.")
                .log(LoggingLevel.INFO, log, "JSON TO POJO conversion started")
                .unmarshal(jdf)
                .log(LoggingLevel.INFO, log, "JSON TO POJO conversion done")
                .process(myProcessor)
                .log(LoggingLevel.INFO, log, "POJO TO XML conversion started")
                .marshal(xmlFormat)
                .log(LoggingLevel.INFO, log, "POJO TO XML conversion done");


        from("direct:getListData")
                .log(LoggingLevel.INFO, log, "New message list received.")
                .log(LoggingLevel.INFO, log, "JSON TO POJO conversion started")
                .unmarshal(ljdf1)
                .log(LoggingLevel.INFO, log, "JSON TO POJO conversion done")
                //.process(myProcessor)
                .log(LoggingLevel.INFO, log, "POJO TO XML conversion started")
                .marshal(listxmlFormat)
                .log(LoggingLevel.INFO, log, "POJO TO XML conversion done");

    }
}
