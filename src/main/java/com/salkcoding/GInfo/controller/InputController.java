package com.salkcoding.GInfo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.salkcoding.GInfo.data.Login;
import com.salkcoding.GInfo.dto.LoginDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class InputController {

    @GetMapping("")
    public String inputForm() {
        return "input";
    }

    @PostMapping("/login")
    public String resultForm(LoginDTO dto, Model model){
        Login login = dto.toLogin();
        String stringURL = String.format(
                "https://att.gachon.ac.kr/ajax/PU_MNMN01_SVC/PU_MNMN01_LOGIN.do?" +
                        "USER_ID=%s&" +
                        "USER_PW=%s&language=ko"
                ,login.getId(),login.getPassword());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String stringJson = getOpenStreamUrl(stringURL);
        String pretty = gson.toJson(JsonParser.parseString(stringJson));
        model.addAttribute("data", pretty);
        return "result";
    }

    @GetMapping("/login")
    public String resultForm(@RequestParam String id, @RequestParam String pw, Model model) {
        String stringURL = String.format(
                "https://att.gachon.ac.kr/ajax/PU_MNMN01_SVC/PU_MNMN01_LOGIN.do?" +
                        "USER_ID=%s&" +
                        "USER_PW=%s&language=ko"
        ,id,pw);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String stringJson = getOpenStreamUrl(stringURL);
        String pretty = gson.toJson(JsonParser.parseString(stringJson));
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
