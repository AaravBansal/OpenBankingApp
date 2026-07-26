package com.scots.openbanking.openbankingapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class FinancialDataService {


    private final ObjectMapper mapper = new ObjectMapper();


    public String getFinancialData() throws Exception {


        StringBuilder data = new StringBuilder();


        data.append("\nACCOUNTS DATA:\n");

        data.append(
                readJson("mockdata/accounts.json")
        );


        data.append("\n\nTRANSACTIONS DATA:\n");

        data.append(
                readJson("mockdata/transactions.json")
        );


        data.append("\n\nLOANS DATA:\n");

        data.append(
                readJson("mockdata/loans.json")
        );


        data.append("\n\nINVESTMENTS DATA:\n");

        data.append(
                readJson("mockdata/investments.json")
        );


        return data.toString();

    }



    private String readJson(String path) throws Exception {


        InputStream inputStream =
                new ClassPathResource(path)
                        .getInputStream();


        JsonNode json =
                mapper.readTree(inputStream);


        return mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(json);

    }

}