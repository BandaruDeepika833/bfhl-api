package com.example.bfhl.controller;

import com.example.bfhl.model.BfhlRequest;
import com.example.bfhl.model.BfhlResponse;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class BfhlController {

    @PostMapping("/bfhl")
    public BfhlResponse processData(@RequestBody BfhlRequest request) {
        BfhlResponse response = new BfhlResponse();

        try {
            List<String> oddNumbers = new ArrayList<>();
            List<String> evenNumbers = new ArrayList<>();
            List<String> alphabets = new ArrayList<>();
            List<String> specialChars = new ArrayList<>();
            int sum = 0;

            for (String item : request.getData()) {
                if (item.matches("^-?\\d+$")) { // number
                    int num = Integer.parseInt(item);
                    sum += num;
                    if (num % 2 == 0) {
                        evenNumbers.add(item);
                    } else {
                        oddNumbers.add(item);
                    }
                } else if (item.matches("^[a-zA-Z]+$")) { // alphabets
                    alphabets.add(item.toUpperCase());
                } else { // special chars
                    specialChars.add(item);
                }
            }

            // concat string (reverse + alternating caps)
            String allAlphabets = alphabets.stream().collect(Collectors.joining(""));
            String reversed = new StringBuilder(allAlphabets).reverse().toString();
            StringBuilder altCaps = new StringBuilder();
            boolean upper = true;
            for (char c : reversed.toCharArray()) {
                altCaps.append(upper ? Character.toUpperCase(c) : Character.toLowerCase(c));
                upper = !upper;
            }

            // set response
            response.setIs_success(true);
            response.setUser_id("john_doe_17091999"); // <-- customize
            response.setEmail("john@xyz.com");
            response.setRoll_number("ABCD123");
            response.setOdd_numbers(oddNumbers);
            response.setEven_numbers(evenNumbers);
            response.setAlphabets(alphabets);
            response.setSpecial_characters(specialChars);
            response.setSum(String.valueOf(sum));
            response.setConcat_string(altCaps.toString());

        } catch (Exception e) {
            response.setIs_success(false);
        }
        return response;
    }
}
