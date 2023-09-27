package com.salkcoding.GInfo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Controller
public class InputController {

    @GetMapping("")
    public String inputForm() {
        return "input";
    }

    @GetMapping("/login")
    public String resultForm(@RequestParam String id, @RequestParam String pw, Model model) {
        System.out.println(id);
        System.out.println(pw);
        String stringURL = String.format(
                "https://att.gachon.ac.kr/ajax/PU_MNMN01_SVC/PU_MNMN01_LOGIN.do?" +
                        "USER_ID=%s&" +
                        "USER_PW=%s&isMobile=true&language=ko"
        ,id,pw);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String pretty=gson.toJson(
                URLDecoder.decode(getOpenStreamUrl(stringURL),StandardCharsets.UTF_8)
        );
        model.addAttribute("data", pretty);
        return "result";
    }

    private String getOpenStreamUrl(String urlToRead) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urlToRead);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            }
        } catch (IOException ignore) {}
        return result.toString();
    }

}
