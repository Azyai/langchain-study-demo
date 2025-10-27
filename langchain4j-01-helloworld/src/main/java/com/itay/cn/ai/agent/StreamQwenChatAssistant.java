package com.itay.cn.ai.agent;

import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

// 因为在配置文件中同时配置了多个大语言模型，所以要在这里明确EXPLICTT指定模型的beanName
@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,
        streamingChatModel = "streamingQwen")
public interface StreamQwenChatAssistant {

    Flux<String> chatFlux(String  prompt);

    String chat(String prompt);

}
