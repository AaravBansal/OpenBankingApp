package com.scots.openbanking.openbankingapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scots.openbanking.openbankingapp.dto.AccountDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinancialDataCacheService {


    private final ObjectMapper mapper = new ObjectMapper();

    private final Map<String, JsonNode> financialData =
            new HashMap<>();



    @PostConstruct
    public void loadData() throws Exception {


        loadFile("accounts");
        loadFile("transactions");
        loadFile("loans");
        loadFile("investments");


        System.out.println(
                "Financial JSON loaded into memory"
        );

    }



    private void loadFile(String name) throws Exception {


        InputStream input =
                new ClassPathResource(
                        "mockdata/" + name + ".json"
                )
                        .getInputStream();


        JsonNode json =
                mapper.readTree(input);


        financialData.put(
                name,
                json
        );

    }



    public JsonNode getData(String name){

        return financialData.get(name);

    }



    public Map<String, JsonNode> getAllData(){

        return financialData;

    }

    public List<AccountDTO> getAccounts() {
        return List.of();
    }
}