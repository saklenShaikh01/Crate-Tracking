package com.notification.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
@Service
public class SmsService {

  private final String AUTH_KEY = "501791AYieJgnW69bcd6a7P1";
  private final String TEMPLATE_ID = "69bcd8b5925e82c770020c73";

  public void sendSms(String mobile, Long name, String balance, String action) {

    String url = "https://api.msg91.com/api/v5/flow/";

    RestTemplate restTemplate = new RestTemplate();

    String body = "{"
      + "\"template_id\":\"" + TEMPLATE_ID + "\","
      + "\"short_url\":\"0\","
      + "\"recipients\":[{"
      + "\"mobiles\":\"91" + mobile + "\","
      + "\"var1\":\"" + name + "\","
      + "\"var2\":\"" + balance + "\","
      + "\"var3\":\"" + action + "\""
      + "}]"
      + "}";

    HttpHeaders headers = new HttpHeaders();
    headers.set("authkey", AUTH_KEY);
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> request = new HttpEntity<>(body, headers);

    restTemplate.postForObject(url, request, String.class);
  }
}
