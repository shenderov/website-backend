package me.shenderov.website.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class RecapchaService {

    @Value("${application.security.recaptcha.secretkey}")
    private String recaptchaSecret;

    private static final String GOOGLE_RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean verifyRecaptcha(String recaptchaResponse){
        try {
        String url = GOOGLE_RECAPTCHA_VERIFY_URL + "?secret=" + recaptchaSecret + "&response=" + recaptchaResponse;
        InputStream res = new URL(url).openStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(res, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        String jsonText = sb.toString();
        res.close();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(jsonText, new TypeReference<Map<String, Object>>() {});
        return (boolean) map.get("success");
    } catch (Exception e) {
            e.printStackTrace();
        return false;
    }
    }
}