package com.example.camelexample.airlines;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AirlineSearchBean {

    @Autowired
    com.mongodb.client.MongoClient mongoClient;

    public List getInfo(String srcCity ,String destCity){

        Document filter = new Document("src_airport", srcCity);
        filter.append("dst_airport" , destCity);
        return getData(filter);

    }

    public List getAllFlightsFromCity(String srcCity){

        Document filter = new Document("src_airport", srcCity);
        return getData(filter);

    }

    public List getAllFlightsToCity(String destCity){

        Document filter = new Document("src_airport", destCity);
        return getData(filter);

    }

    private List getData(Document filter){

        MongoDatabase database = mongoClient.getDatabase("sample_training");
        MongoCollection<Document> collection = database.getCollection("routes");
        List results = new ArrayList<>();
        return   collection.find(filter).into(results);

    }

}

