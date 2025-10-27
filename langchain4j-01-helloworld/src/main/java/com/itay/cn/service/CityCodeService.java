package com.itay.cn.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CityCodeService {

    // 与 WeatherService 保持一致的和风天气域名与密钥
    private static final String API_KEY = "ddf2ec87dd99469eb62f9c0364bb012e";
    private static final String BASE_URL = "https://mm44u9udjc.re.qweatherapi.com/geo/v2/city/lookup?location=%s&key=%s";

    public String getCityCode(String cityName) throws Exception {
        String url = String.format(BASE_URL, cityName, API_KEY);

        var httpClient = HttpClients.createDefault();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        String response = new RestTemplate(factory).getForObject(url, String.class);

        JsonNode jsonNode = new ObjectMapper().readTree(response);
        // 文档: https://dev.qweather.com/docs/api/geoapi/geoapi/#返回结果
        if (jsonNode.has("location") && jsonNode.get("location").isArray() && jsonNode.get("location").size() > 0) {
            JsonNode first = jsonNode.get("location").get(0);
            if (first.has("id")) {
                return first.get("id").asText();
            }
        }
        throw new IllegalArgumentException("未找到城市编码: " + cityName);
    }
}