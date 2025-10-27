package com.itay.cn.ai.agent;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;


@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "qwen",
        tools = {"cityCodeTool", "weatherTool"}
)
public interface WeatherAssistant {

    @SystemMessage("你是一位专业的天气预报专家。用户可能输入城市中文名、拼音或城市编码。若不是编码，请先调用cityCodeTool.getCityCode解析出国内天气城市的服务代码，再调用weatherTool.getWeather获取对应城市的实时天气，并用简洁中文回答。")
    @UserMessage("用户请求：{{it}}。请判断是否需要先解析城市编码，然后给出该城市当前天气情况。")
    String chatWeatherTool(@P("城市中文名、拼音或国内城市编码") String cityOrQuery);
}
