package com.scots.openbanking.openbankingapp.service;

import org.springframework.stereotype.Service;


@Service
public class NumberParserService {


    public double parseMoney(String value) {


        if (value == null || value.isBlank()) {
            return 0;
        }


        return Double.parseDouble(
                value
                        .replace(",", "")
                        .replace("$", "")
                        .trim()
        );

    }

}