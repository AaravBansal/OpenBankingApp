package com.scots.openbanking.openbankingapp.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


import java.io.InputStream;



@Service
public class JsonLoaderService {


    private final ObjectMapper mapper =
            new ObjectMapper();



    public <T> T load(
            String file,
            Class<T> clazz
    ) throws Exception {


        InputStream input =
                new ClassPathResource(
                        "mockdata/" + file
                ).getInputStream();



        return mapper.readValue(
                input,
                clazz
        );

    }

}