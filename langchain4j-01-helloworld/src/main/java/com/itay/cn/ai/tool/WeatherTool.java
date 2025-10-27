package com.itay.cn.ai.tool;

import com.fasterxml.jackson.databind.JsonNode;
import com.itay.cn.service.WeatherService;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeatherTool {

    @Autowired
    private WeatherService weatherService;

    @Tool
    public JsonNode getWeather(String city) throws Exception {
        return weatherService.getWeatherV2(city);
    }

}
