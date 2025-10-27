package com.itay.cn.ai.tool;

import com.itay.cn.service.CityCodeService;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityCodeTool {

    @Autowired
    private CityCodeService cityCodeService;

    @Tool
    public String getCityCode(String cityName) throws Exception {
        return cityCodeService.getCityCode(cityName);
    }
}